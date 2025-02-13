package org.example.filemanager.filesearch;

import org.example.filemanager.filehandling.DateTimeFormatter;
import org.example.filemanager.filehandling.File;
import org.example.filemanager.filehandling.FileController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FolderFiles {
    FileController fileController;
    List<File> currentFiles = new ArrayList<>();

    public FolderFiles(FileController fileController) {
        this.fileController = fileController;
    }

    public List<File> getFilesFrom(Path originPath) {
        List<File> files = new ArrayList<>();
        try {
            Files.list(originPath).forEach(originFile -> {
                try {
                    String formattedTime = DateTimeFormatter.convertToLocalDateTime(Files.getLastModifiedTime(originFile).toString());
                    File file = new File(originFile.getFileName().toString(), Files.size(originPath), formattedTime, originPath, (Files.isDirectory(originFile)));
                    files.add(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        List<File> sortedFiles = getSortedDirectorys(files);

        currentFiles.clear();
        currentFiles.addAll(sortedFiles);
        return sortedFiles;
    }

    public List<File> getSortedDirectorys(List<File> files) {
        List<File> sortedFiles = new ArrayList<>();
        List<File> tempFiles = new ArrayList<>();

        for (File file : files) {
            if (file.isDirectory()) {
                sortedFiles.add(file);
            } else {
                tempFiles.add(file);
            }
        }
        sortedFiles.addAll(tempFiles);
        return sortedFiles;
    }
}
