package org.example.filemanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.nio.file.*;

public class FileController {
    private String pointer;

    FileController() {
        this.pointer = "C:\\";
    }

    public static void main(String[] args) {
        FileController fileController = new FileController();

        List<File> files = fileController.getSortedDirectorys(fileController.getFilesFrom("C:\\"));
        for(File file : files) {
            System.out.print("[]" + file.getName() + " ");
            System.out.print(file.getSize() + " ");
            System.out.println(file.getDate() + " ");
            System.out.println();
        }
    }

    public List<File> getFilesFrom(String path) {
        List<File> files = new ArrayList<>();
        try {
            Path root = Paths.get("C:\\");
            Files.list(root).forEach(originFile -> {
                try {
                    System.out.println(Files.isDirectory(originFile));
                    File file = new File(originFile.getName(0).toString(), Files.size(root), Files.getLastModifiedTime(root).toString() , root.toString(), (Files.isDirectory(originFile)));
                    files.add(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        return files;
    }

    public List<File> getSortedDirectorys(List<File> files) {
        List<File> sortedFiles = new ArrayList<>();
        List<File> tempFiles = new ArrayList<>();

        for(File file : files) {
            System.out.println(file.getName());
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
}
