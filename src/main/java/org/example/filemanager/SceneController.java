package org.example.filemanager;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import java.util.List;

public class SceneController {
    @FXML
    private TextField pathFieldView;
    @FXML
    private VBox vboxContainer;
    @FXML
    private TreeView treeListView;
    @FXML
    private TextField searchBar;

    FileController fileController = new FileController();

    @FXML
    private void initialize() {
        treeListView.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (fileController.chosenFile != null) {
                    fileController.chosenFile.setIsChosenTo(false);
                    fileController.chosenFile = null;
                    updateTreeView();
                }
            }
            });
        updateTreeView();
        updatePathView();

        searchBar.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().toString().equals("ENTER")) {
                File file = fileController.searchForFile(searchBar.getText());
                vboxContainer.getChildren().clear();
                vboxContainer.getChildren().add(FileItem.createFileItem(file.getName(), file.getPath(), file.getDate(), file.isDirectory(), fileController, this));

            }
        });
    }

    @FXML
    public void updateTreeView() {
        vboxContainer.getChildren().clear();
        List<File> files = fileController.getFilesFrom(fileController.getPointer());

        for (File file : files) {
            vboxContainer.getChildren().add(FileItem.createFileItem(file.getName(), file.getPath(), file.getDate(), file.isDirectory(), fileController, this));
        }
    }

    @FXML
    public void updateListView() {

    }

    @FXML
    public void updatePathView() {
        pathFieldView.setText(fileController.getPointer());
        pathFieldView.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().toString().equals("ENTER")) {

                String NextPointer = fileController.pointerHistory.getPointerForward();
                if (!NextPointer.equals(pathFieldView.getText())) {
                    fileController.pointerHistory.removePointersAtIndex(fileController.pointerHistory.getPointerHistory().indexOf(fileController.getPointer()));
                }

                fileController.setPointer(pathFieldView.getText());
                if (!fileController.getPointer().endsWith("\\"))
                {
                    fileController.setPointer(fileController.getPointer() + "\\");
                }
                if (fileController.chosenFile != null) {
                    fileController.chosenFile.setIsChosenTo(false);
                    fileController.chosenFile = null;
                }

                updateTreeView();
            }
        });
    }

    @FXML
    public void backwardsButtonClicked() {
        fileController.setPointer(fileController.pointerHistory.getPointerBackwards());
        if (fileController.chosenFile != null) {
            fileController.chosenFile.setIsChosenTo(false);
            fileController.chosenFile = null;
        }
        updateTreeView();
        updatePathView();
    }

    @FXML
    public void forwardsButtonClicked() {
        fileController.setPointer(fileController.pointerHistory.getPointerForward());
        if (fileController.chosenFile != null) {
            fileController.chosenFile.setIsChosenTo(false);
            fileController.chosenFile = null;
        }
        updateTreeView();
        updatePathView();
    }

    @FXML
    public void createNewFolder() {
        String directoryName = AskForNaming.askForDirectoryName();
        fileController.makeDirectory(fileController.getPointer(), directoryName);
        updateTreeView();
    }

    @FXML
    public void createNewFile() {
        String fileName = AskForNaming.askForFileName();
        fileController.makeFile(fileController.getPointer(), fileName);
        updateTreeView();
    }


    @FXML
    public void renameFile() {
        if (fileController.chosenFile != null) {
            if (fileController.chosenFile.isDirectory()) {
                String newDirectoryName = AskForNaming.askForDirectoryRename(fileController.chosenFile.getName());
                fileController.renameFile(fileController.chosenFile.getPath(), newDirectoryName);
            } else {
                String newFileName = AskForNaming.askForFileRename(fileController.chosenFile.getName());
                fileController.renameFile(fileController.chosenFile.getPath(), newFileName);
            }
        }
        updateTreeView();
    }

    @FXML
    public void deleteFile() {
        if (fileController.chosenFile != null) {
            fileController.deleteFile(fileController.chosenFile.getPath());
        }
        updateTreeView();
    }

    @FXML
    public void copyFile() {
        if (fileController.chosenFile != null) {
            fileController.copyFile(fileController.chosenFile.getPath());
        }
        updateTreeView();
    }

    @FXML
    public void pasteFile() {
        fileController.pasteFile();
        updateTreeView();
    }
}