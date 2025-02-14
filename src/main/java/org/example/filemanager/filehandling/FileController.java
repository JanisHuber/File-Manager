package org.example.filemanager.filehandling;

import org.example.filemanager.filesearch.*;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;


public class FileController {
    private File chosenFile = null;
    private Path copyFileSourcePath = null;
    private PointerHistory pointerHistory;
    private File copyFile = null;
    private FolderFiles folderFiles;

    public FileController() {
        this.pointerHistory = new PointerHistory(this);
        pointerHistory.setPointer(Paths.get("C:\\"));
        folderFiles = new FolderFiles(this);
    }

    public Optional<File> getChosenFile() {
        return Optional.ofNullable(chosenFile);
    }

    public void setChosenFile(File chosenFile) {
        this.chosenFile = chosenFile;
    }

    public Path getPointer() {
        return pointerHistory.getPointer();
    }

    public List<Path> getPointerHistory() {
        return pointerHistory.getPointerHistory();
    }

    public List<File> getFilesFrom(Path path) {
        return folderFiles.getFilesFrom(path);
    }

    public List<File> getFilesFromPointer() {
        return getFilesFrom(getPointer());
    }

    public void navigateTo(Path pointer) {
        if (!pointer.endsWith("\\")) {
            pointer = Paths.get(pointer + "\\");
        }
        pointerHistory.setPointer(pointer);

        if (!pointer.equals(pointerHistory.getPointerForward())) {
            int index = getPointerHistory().indexOf(getPointer());
            pointerHistory.removePointersAtIndex(index);
        }
    }

    public void navigateBackwards() {
        pointerHistory.setPointer(pointerHistory.getPointerBackwards());
    }

    public void navigateForwards() {
        navigateTo(pointerHistory.getPointerForward());
    }

    public void makeDirectoryInCurrentFolder(String directoryName) {
        try {
            Files.createDirectory(Paths.get(getPointer() + "\\" + directoryName));
        } catch (FileAlreadyExistsException e) {
            System.out.println("Ordner existiert bereits: " + getPointer().toAbsolutePath());
        } catch (Exception e) {
            System.err.println("Fehler: " + e.getMessage());
        }
    }

    public void makeFileInCurrentFolder(String fileName) {
        try {
            Files.createFile(Paths.get(getPointer() + "\\" + fileName));
        } catch (FileAlreadyExistsException e) {
            System.out.println("Ordner existiert bereits: " + getPointer().toAbsolutePath());
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

    public List<File> search(SearchMethod searchMethod, String searchQuery, int depth, Path sourceDirectory) {
        FileSearch fileSearch;
        if (searchMethod == SearchMethod.NIO) {
            fileSearch = new FileSearchWithNio();
        } else {
            fileSearch = new FileSearchRecursive(this);
        }
        return fileSearch.search(sourceDirectory, searchQuery, depth);
    }
}
