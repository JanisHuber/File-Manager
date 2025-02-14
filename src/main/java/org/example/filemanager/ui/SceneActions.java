package org.example.filemanager.ui;

import javafx.fxml.FXML;
import org.example.filemanager.filehandling.FileController;

public class SceneActions {
    FileController fileController;
    SceneController sceneController;

    public SceneActions(SceneController sceneController, FileController fileController) {
        this.sceneController = sceneController;
        this.fileController = fileController;
    }


    @FXML
    public void navigateBackwards() {
        fileController.setPointer(fileController.getPointerBackwards()); //todo
        resetChosenFile();
        sceneController.updateTreeView(fileController.getFilesFrom(fileController.getPointer()));
    }

    @FXML
    public void navigateForwards() {
        fileController.setPointer(fileController.getPointerForward());
        resetChosenFile();
        sceneController.updateTreeView(fileController.getFilesFrom(fileController.getPointer()));
    }

    @FXML
    public void createNewFolder() {
        String directoryName = NameDialog.directoryName();
        fileController.makeDirectory(fileController.getPointer(), directoryName);
        sceneController.updateTreeView(fileController.getFilesFrom(fileController.getPointer()));
    }

    @FXML
    public void createNewFile() {
        String fileName = NameDialog.fileName();
        fileController.makeFile(fileController.getPointer(), fileName);
        sceneController.updateTreeView(fileController.getFilesFrom(fileController.getPointer()));
    }


    @FXML
    public void renameFile() {
        if (fileController.chosenFile != null) {
            if (fileController.chosenFile.isDirectory()) {
                String newDirectoryName = NameDialog.directoryRename(fileController.chosenFile.getName());
                fileController.renameFile(fileController.chosenFile.getPath(), newDirectoryName);
            } else {
                String newFileName = NameDialog.fileRename(fileController.chosenFile.getName());
                fileController.renameFile(fileController.chosenFile.getPath(), newFileName);
            }
        }
        sceneController.updateTreeView(fileController.getFilesFrom(fileController.getPointer()));
    }

    @FXML
    public void deleteFile() {
        if (fileController.chosenFile != null) {
            fileController.deleteFile(fileController.chosenFile.getPath());
        }
        sceneController.updateTreeView(fileController.getFilesFrom(fileController.getPointer()));
    }

    @FXML
    public void copyFile() {
        if (fileController.chosenFile != null) {
            fileController.copyFile(fileController.chosenFile.getPath());
        }
        sceneController.updateTreeView(fileController.getFilesFrom(fileController.getPointer()));
    }

    @FXML
    public void pasteFile() {
        fileController.pasteFile();
        sceneController.updateTreeView(fileController.getFilesFrom(fileController.getPointer()));
    }

    private void resetChosenFile() {
        if (fileController.chosenFile != null) {
            fileController.chosenFile.setIsChosenTo(false);
            fileController.chosenFile = null;
        }
    }
}
