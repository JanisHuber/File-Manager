package org.example.filemanager.filesearch;

import org.example.filemanager.filehandling.File;
import org.example.filemanager.filehandling.FileController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileSearchRecursive implements FileSearch {
    private FileController fileController;
    private FolderFiles folderFiles;

    public FileSearchRecursive(FileController fileController) {
        this.fileController = fileController;
        this.folderFiles = new FolderFiles(fileController);
    }

    public List<File> search(Path path, String searchQuery, int depth) {
        List<File> result = new ArrayList<>();
        try {
            if (depth < 0) {
                return result;
            }
            for (File file : folderFiles.getFilesFrom(path)) {
                if (file.getName().contains(searchQuery)) {
                    result.add(file);
                }

                if (file.isDirectory() && Files.isReadable(Paths.get(file.getPath() + "\\" + file.getName()))) {
                    Path nextPath = Paths.get(file.getPath() + "\\" + file.getName());
                    result.addAll(search(nextPath, searchQuery, depth - 1));
                }
            }
        } catch (Exception e) {
            System.err.println("Keine Berechtigung: " + e.getMessage());
            return result;
        }
        return result;
    }
}
