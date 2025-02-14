package org.example.filemanager.ui;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.example.filemanager.filehandling.File;
import org.example.filemanager.filehandling.FileController;
import org.example.filemanager.filesearch.SearchMethod;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SceneController {
    FileController fileController = new FileController();
    SceneActions sceneactions = new SceneActions(this, fileController);
    FileItem fileItem = new FileItem(fileController, this);
    @FXML
    private TextField pathFieldView;
    @FXML
    private VBox vboxContainer;
    @FXML
    private TextField searchBar;
    @FXML
    private CheckBox deepsearch;
    @FXML
    private CheckBox lockedPath;
    @FXML
    private CheckBox recursiveSearch;

    @FXML
    public void onBackwardsButtonClicked() {
        sceneactions.navigateBackwards();
    }

    @FXML
    public void onForwardsButtonClicked() {
        sceneactions.navigateForwards();
    }

    @FXML
    public void onNewFolderButtonClicked() {
        sceneactions.createNewFolder();
    }

    @FXML
    public void onNewFileButtonClicked() {
        sceneactions.createNewFile();
    }

    @FXML
    public void onRenameFileButtonClicked() {
        sceneactions.renameFile();
    }

    @FXML
    public void onDeleteFileButtonClicked() {
        sceneactions.deleteFile();
    }

    @FXML
    public void onCopyFileButtonClicked() {
        sceneactions.copyFile();
    }

    @FXML
    public void onPasteFileButtonClicked() {
        sceneactions.pasteFile();
    }

    @FXML
    private void initialize() {
        updateTreeView(fileController.getFilesFromPointer());

        searchBar.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().toString().equals("ENTER")) {
                SearchMethod searchMethod = recursiveSearch.isSelected() ? SearchMethod.RECURSIVE : SearchMethod.NIO;
                int depth = deepsearch.isSelected() ? Integer.MAX_VALUE : 3;
                Path path = lockedPath.isSelected() ? fileController.getPointer() : Paths.get("C:\\");

                List<File> searchFiles = fileController.search(searchMethod, searchBar.getText(), depth, path);
                updateTreeView(searchFiles);
            }
        });
    }

    @FXML
    public void updateTreeView(List<File> files) {
        vboxContainer.getChildren().clear();

        for (File file : files) {
            vboxContainer.getChildren().add(fileItem.createFileItem(file));
        }
        if (!pathFieldView.getText().equals(fileController.getPointer().toString())) {
            updatePathView();
        }
    }

    @FXML
    public void updatePathView() {
        pathFieldView.setText(fileController.getPointer().toString());
        pathFieldView.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().toString().equals("ENTER")) {
                fileController.navigateTo(Paths.get(pathFieldView.getText()));

                fileController.getChosenFile().ifPresent(file -> {
                    fileController.getChosenFile().get().setIsChosenTo(false);
                    fileController.setChosenFile(null);
                });
                updateTreeView(fileController.getFilesFromPointer());
            }
        });
    }
}