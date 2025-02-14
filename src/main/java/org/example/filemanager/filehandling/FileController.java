package org.example.filemanager.filehandling;

import org.example.filemanager.filesearch.*;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class FileController {
    public File chosenFile = null;
    public List<File> currentFiles = new ArrayList<>();
    private Path copyFileSourcePath = null;
    private PointerHistory pointerHistory;
    private File copyFile = null;
    private FolderFiles folderFiles;

    public FileController() {
        this.pointerHistory = new PointerHistory(this);
        pointerHistory.setPointer(Paths.get("C:\\"));
        folderFiles = new FolderFiles(this);
    }

    public Path getPointer() {
        return pointerHistory.getPointer();
    }

    public void setPointer(Path pointer) {
        pointerHistory.setPointer(pointer);
    }

    public void removePointersAtIndex(int index) {
        pointerHistory.removePointersAtIndex(index);
    }

    public Path getPointerForward() {
        return pointerHistory.getPointerForward();
    }

    public Path getPointerBackwards() {
        return pointerHistory.getPointerBackwards();
    }

    public List<Path> getPointerHistory() {
        return pointerHistory.getPointerHistory();
    }

    public List<File> getFilesFrom(Path path) {
        return folderFiles.getFilesFrom(path);
    }


    public void makeDirectory(Path path, String directoryName) {
        try {
            Files.createDirectory(Paths.get(path + "\\" + directoryName));
        } catch (FileAlreadyExistsException e) {
            System.out.println("Ordner existiert bereits: " + path.toAbsolutePath());
        } catch (Exception e) {
            System.err.println("Fehler: " + e.getMessage());
        }
    }

    public void makeFile(Path path, String fileName) {
        try {
            Files.createFile(Paths.get(path + "\\" + fileName));
        } catch (FileAlreadyExistsException e) {
            System.out.println("Ordner existiert bereits: " + path.toAbsolutePath());
        } catch (Exception e) {
            System.err.println("Fehler: " + e.getMessage());
        }
    }

    public void renameFile(Path path, String name) {
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

    public void deleteFile(Path path) {
        Path file = Paths.get(path + "\\" + chosenFile.getName());
        try {
            Files.delete(file);
        } catch (Exception e) {
            System.err.println("Fehler: " + e.getMessage());
        }
    }

    public void copyFile(Path path) {
        copyFileSourcePath = path;
        copyFile = chosenFile;
    }

    public void pasteFile() {
        try {
            Path source = Paths.get(copyFileSourcePath + "\\" + copyFile.getName());
            Path target = Paths.get(pointerHistory.getPointer() + "\\" + copyFile.getName());
            Files.copy(source, target);
        } catch (Exception e) {
            System.err.println("Fehler: " + e.getMessage());
        }
    }

    public List<File> search(SearchMethod searchMethod, String searchQuery, int depth) {
        FileSearch fileSearch;
        if (searchMethod == SearchMethod.NIO) {
            fileSearch = new FileSearchWithNio();
        } else {
            fileSearch = new FileSearchRecursive(this);
        }
        return fileSearch.search(pointerHistory.getPointer(), searchQuery, depth);
    }
}
