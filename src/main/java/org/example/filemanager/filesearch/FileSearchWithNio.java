package org.example.filemanager.filesearch;

import org.example.filemanager.filehandling.File;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.filemanager.filehandling.DateTimeFormatter.convertToLocalDateTime;


public class FileSearchWithNio implements FileSearch {
    @Override
    public List<File> search(Path path, String searchQuery, int depth) {
        List<File> treeFiles = new ArrayList<>();
        Pattern pattern = Pattern.compile(searchQuery, Pattern.CASE_INSENSITIVE);

        try {
            Files.walkFileTree(path, Set.of(), depth, new SimpleFileVisitor<Path>() {
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (file.getFileName().toString().contains(searchQuery)) {
                        String formattedTime = convertToLocalDateTime(Files.getLastModifiedTime(file).toString());
                        treeFiles.add(new File(file.getFileName().toString(), file.toFile().length(), formattedTime, file.toAbsolutePath(), false));
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
                        treeFiles.add(new File(dir.getFileName().toString(), dir.toFile().length(), formattedTime, dir.toAbsolutePath(), true));
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
        List<File> result = sortFilesSearch(treeFiles, searchQuery);
        return result;
    }

    private List<File> sortFilesSearch(List<File> originFiles, String name) {
        LinkedList<File> sortedFiles = new LinkedList<>();
        for (File file : originFiles) {
            String nameRemains = file.getName().replace(name, "");

            if (nameRemains.isEmpty()) {
                sortedFiles.addFirst(file);
            }
            if (nameRemains.startsWith(".")) {
                sortedFiles.addLast(file);
            }
        }
        originFiles.removeAll(sortedFiles);
        sortedFiles.addAll(originFiles);
        return sortedFiles;
    }
}
