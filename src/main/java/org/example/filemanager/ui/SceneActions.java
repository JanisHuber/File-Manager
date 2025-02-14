package org.example.filemanager.ui;

import javafx.fxml.FXML;
import org.example.filemanager.filehandling.FileController;

import java.util.Optional;

public class SceneActions {
    FileController fileController;
    SceneController sceneController;

    public SceneActions(SceneController sceneController, FileController fileController) {
        this.sceneController = sceneController;
        this.fileController = fileController;
    }


    @FXML
    public void navigateBackwards() {
        fileController.navigateBackwards();
        resetChosenFile();
        sceneController.updateTreeView(fileController.getFilesFromPointer());
    }

    @FXML
    public void navigateForwards() {
        fileController.navigateForwards();
        resetChosenFile();
        sceneController.updateTreeView(fileController.getFilesFromPointer());
    }

    @FXML
    public void createNewFolder() {
        Optional<String> directoryName = NameDialog.directoryName();
        directoryName.ifPresent(dir -> {
            fileController.makeDirectoryInCurrentFolder(dir);
            sceneController.updateTreeView(fileController.getFilesFromPointer());
        });
    }

    @FXML
    public void createNewFile() {
        Optional<String> fileName = NameDialog.fileName();
        fileName.ifPresent(dir -> {
            fileController.makeFileInCurrentFolder(dir);
            sceneController.updateTreeView(fileController.getFilesFromPointer());
        });
    }


    @FXML
    public void renameFile() {
        fileController.getChosenFile().ifPresent(file -> {
            if (fileController.getChosenFile().get().isDirectory()) {
                Optional<String> newDirectoryName = NameDialog.directoryRename(fileController.getChosenFile().get().getName());
                newDirectoryName.ifPresent(dir -> {
                    fileController.renameFile(fileController.getChosenFile().get().getPath(), dir);
                });
            } else {
                Optional<String> newFileName = NameDialog.fileRename(fileController.getChosenFile().get().getName());
                newFileName.ifPresent(dir -> {
                    fileController.renameFile(fileController.getChosenFile().get().getPath(), dir);
                });
            }
        });
        sceneController.updateTreeView(fileController.getFilesFromPointer());
    }

    @FXML
    public void deleteFile() {
        fileController.getChosenFile().ifPresent(file -> {
            fileController.deleteFile(fileController.getChosenFile().get().getPath());
        });
        sceneController.updateTreeView(fileController.getFilesFromPointer());
    }

    @FXML
    public void copyFile() {
        fileController.getChosenFile().ifPresent(file -> {
            fileController.copyFile(fileController.getChosenFile().get().getPath());
        });
        sceneController.updateTreeView(fileController.getFilesFromPointer());
    }

    @FXML
    public void pasteFile() {
        fileController.pasteFile();
        sceneController.updateTreeView(fileController.getFilesFromPointer());
    }

    private void resetChosenFile() {
        fileController.getChosenFile().ifPresent(file -> {
            fileController.getChosenFile().get().setIsChosenTo(false);
            fileController.setChosenFile(null);
        });
    }
}
