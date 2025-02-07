package org.example.filemanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.nio.file.*;

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
                    File file = new File(originFile.getFileName().toString(), Files.size(originPath), Files.getLastModifiedTime(originPath).toString() , originPath.toString(), (Files.isDirectory(originFile)));
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
            System.out.println("Ordner existiert bereits: " + newFilePath.toAbsolutePath());
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
}
