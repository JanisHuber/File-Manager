package org.example.filemanager.ui;

import org.example.filemanager.filehandling.File;
import org.example.filemanager.filehandling.FileController;

import java.awt.*;

public class FileItemActions {
    FileController fileController;
    SceneController sceneController;

    public FileItemActions(FileController fileController, SceneController sceneController) {
        this.fileController = fileController;
        this.sceneController = sceneController;
    }


    public void handleSingleClick(File file) {
        if (fileIsAlreadyChosen(file)) {
            deselectFile();
        } else {
            selectFile(file);
        }
    }

    public void handleDoubleClick(File file) {
        if (file.isDirectory()) {
            navigateToDirectory(file);
        } else {
            openFile(file);
        }
    }

    private boolean fileIsAlreadyChosen(File file) {
        if (!fileController.getChosenFile().isPresent()) {
            return false;
        }
        return fileController.getChosenFile().get().equals(file);
    }

    private void navigateToDirectory(File file) {
        fileController.navigateTo(file.getPath().resolve(file.getName()));
        sceneController.updateTreeView(fileController.getFilesFromPointer());
    }

    private void deselectFile() {
        fileController.getChosenFile().ifPresent(file -> {
            fileController.getChosenFile().get().setIsChosenTo(false);
            fileController.setChosenFile(null);
            sceneController.updateTreeView(fileController.getFilesFromPointer());
        });
    }

    private void selectFile(File file) {
        fileController.setChosenFile(new File(file.getName(), file.getSize(), file.getDate(), file.getPath(), file.isDirectory()));
        fileController.getChosenFile().ifPresent(f -> f.setIsChosenTo(true));
        fileController.navigateTo(file.getPath());
        sceneController.updateTreeView(fileController.getFilesFromPointer());
    }

    private void openFile(File file) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(new java.io.File(file.getPath() + "\\" + file.getName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
