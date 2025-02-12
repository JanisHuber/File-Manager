package org.example.filemanager;

import java.io.IOException;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import java.nio.file.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileController {
    private String pointer;
    public PointerHistory pointerHistory;
    public File chosenFile = null;
    List<File> currentFiles = new ArrayList<>();
    public File copyFile = null;
    public String copyFileSourcePath = null;

    public FileController() {
        this.pointer = "C:\\";
        this.pointerHistory = new PointerHistory(pointer, this);
    }

    public List<File> getFilesFrom(String path) {
        List<File> files = new ArrayList<>();
        try {
            Path originPath = Paths.get(path);
            Files.list(originPath).forEach(originFile -> {
                try {
                    String formattedTime = convertToLocalDateTime(Files.getLastModifiedTime(originFile).toString());
                    File file = new File(originFile.getFileName().toString(), Files.size(originPath), formattedTime , originPath.toString(), (Files.isDirectory(originFile)));
                    files.add(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
        List<File> sortedFiles = getSortedDirectorys(files);
        pointerHistory.addPointerToHistory(path);

        List<String> history = pointerHistory.getPointerHistory();
        currentFiles.clear();
        currentFiles.addAll(sortedFiles);
        return sortedFiles;
    }


    public List<File> getSortedDirectorys(List<File> files) {
        List<File> sortedFiles = new ArrayList<>();
        List<File> tempFiles = new ArrayList<>();

        for(File file : files) {
            if(file.isDirectory()) {
                sortedFiles.add(file);
            } else {
                tempFiles.add(file);
            }
        }
        sortedFiles.addAll(tempFiles);

        return sortedFiles;
    }

    public String getPointer() {
        return pointer;
    }

    public void setPointer(String pointer) {
        this.pointer = pointer;
    }

    public void makeDirectory(String path, String directoryName) {
        Path newDirectory = Paths.get(path + directoryName);

        try {
            Files.createDirectory(newDirectory);
        } catch (FileAlreadyExistsException e) {
            System.out.println("Ordner existiert bereits: " + newDirectory.toAbsolutePath());
        } catch (Exception e) {
            System.err.println("Fehler: " + e.getMessage());
        }
    }

    public void makeFile(String path, String fileName) {
        Path newFile = Paths.get(path + fileName);

        try {
            Files.createFile(newFile);
        } catch (FileAlreadyExistsException e) {
            System.out.println("Ordner existiert bereits: " + newFile.toAbsolutePath());
        } catch (Exception e) {
            System.err.println("Fehler: " + e.getMessage());
        }
    }

    public void renameFile(String path, String name) {
        Path file = Paths.get(path + "\\" + chosenFile.getName());
        Path newFilePath = Paths.get(path + "\\" + name);

        try {
            Files.move(file, newFilePath);
        } catch (FileAlreadyExistsException e) {
            System.out.println("Name ist bereits vergeben: " + newFilePath.toAbsolutePath());
        } catch (Exception e) {
            System.err.println("Fehler: " + e.getMessage());
        }
    }

    public void deleteFile(String path) {
        Path file = Paths.get(path + "\\" + chosenFile.getName());
        try {
            Files.delete(file);
        } catch (Exception e) {
            System.err.println("Fehler: " + e.getMessage());
        }
    }

    public void copyFile(String path) {
        copyFileSourcePath = path;
        copyFile = chosenFile;
    }

    public void pasteFile() {
        try {
            Path source = Paths.get(copyFileSourcePath + "\\" + copyFile.getName());
            Path target = Paths.get(pointer + "\\" + copyFile.getName());
            Files.copy(source, target);
        } catch (Exception e) {
            System.err.println("Fehler: " + e.getMessage());
        }
    }

    public File searchForFile(String name) {
        for (File file : currentFiles) {
            if (file.getName().equals(name)) {
                return file;
            }
        }
        return null;
    }

    public void updateFilesList() {
        List<File> files = getFilesFrom(pointer);
    }

    public List<File> deepSearchForFiles(String name, String originPath) {
        List<File> treeFiles = new ArrayList<>();
        Path startPath;
        Pattern pattern = Pattern.compile(name, Pattern.CASE_INSENSITIVE);

        if (originPath == null) {
            startPath = Paths.get(pointer);
        } else {
            startPath = Paths.get(originPath);
        }

        try {
            Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (file.getFileName().toString().contains(name)) {
                        String formattedTime = convertToLocalDateTime(Files.getLastModifiedTime(file).toString());
                        treeFiles.add(new File(file.getFileName().toString(), file.toFile().length(), formattedTime, file.toString(), false));
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    if (dir.getFileName() == null) {
                        return FileVisitResult.CONTINUE;
                    }

                    Matcher matcher = pattern.matcher(dir.getFileName().toString());
                    if (matcher.find()) {

                        Path timePath = Paths.get(dir.toString());
                        String formattedTime = convertToLocalDateTime(Files.getLastModifiedTime(timePath).toString());
                        treeFiles.add(new File(dir.getFileName().toString(), dir.toFile().length(), formattedTime, dir.toString(), true));

                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    return FileVisitResult.SKIP_SUBTREE;
                }
            });
        } catch (IOException e) {
            System.err.println("Error traversing directory: " + e.getMessage());
        }
        List<File> result = sortFilesSearch(treeFiles, name);
        return result;
    }

    public List<File> sortFilesSearch(List<File> originFiles, String name) {
        LinkedList<File> sortedFiles = new LinkedList<>();

        for (File file : originFiles) {
            String nameRemains = file.getName(). replace(name, "");

            if (nameRemains.isEmpty()) {
                sortedFiles.addFirst(file);
                System.out.println("Adding: " + file.getName());
            }
            if (nameRemains.startsWith(".")) {
                sortedFiles.addLast(file);
            }
        }
        System.out.println("Sorted Files: " + sortedFiles.size());
        originFiles.removeAll(sortedFiles);
        sortedFiles.addAll(originFiles);

        return sortedFiles;
    }

    public String convertToLocalDateTime(String time) {
        Instant instant = Instant.parse(time);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return localDateTime.format(formatter);
    }
}
