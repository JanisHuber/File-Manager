package org.example.filemanager.UI;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.example.filemanager.filehandling.File;
import org.example.filemanager.filehandling.FileController;
import org.example.filemanager.filesearch.SearchMethod;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SceneController {
    FileController fileController = new FileController();
    SceneActions sceneactions = new SceneActions(this, fileController);
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
        updateTreeView(fileController.getFilesFrom(fileController.getPointer()));
        updatePathView();

        searchBar.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().toString().equals("ENTER")) {
                List<File> searchFiles = new ArrayList<>();
                if (deepsearch.isSelected()) {
                    fileController.setPointer((lockedPath.isSelected()) ? fileController.getPointer() : Paths.get("C:\\"));
                    if (recursiveSearch.isSelected()) {
                        searchFiles = fileController.search(SearchMethod.RECURSIVE, searchBar.getText(), Integer.MAX_VALUE);
                    } else {
                        searchFiles = fileController.search(SearchMethod.NIO, searchBar.getText(), Integer.MAX_VALUE);
                    }
                } else {
                    if (recursiveSearch.isSelected()) {
                        searchFiles = fileController.search(SearchMethod.RECURSIVE, searchBar.getText(), 4);
                    } else {
                        searchFiles = fileController.search(SearchMethod.NIO, searchBar.getText(), 2);
                    }
                }
                updateTreeView(searchFiles);
            }
        });
    }

    @FXML
    public void updateTreeView(List<File> files) {
        vboxContainer.getChildren().clear();

        for (File file : files) {
            vboxContainer.getChildren().add(FileItem.createFileItem(file.getName(), file.getPath(), file.getDate(), file.isDirectory(), fileController, this));
        }
    }

    @FXML
    public void updatePathView() {
        pathFieldView.setText(fileController.getPointer().toString());
        pathFieldView.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().toString().equals("ENTER")) {

                Path nextPointer = fileController.getPointerForward();
                if (!nextPointer.equals(pathFieldView.getText())) {
                    fileController.removePointersAtIndex(fileController.getPointerHistory().indexOf(fileController.getPointer()));
                }

                fileController.setPointer(Paths.get(pathFieldView.getText()));
                if (!fileController.getPointer().endsWith("\\")) {
                    fileController.setPointer(Paths.get(fileController.getPointer() + "\\"));
                }
                if (fileController.chosenFile != null) {
                    fileController.chosenFile.setIsChosenTo(false);
                    fileController.chosenFile = null;
                }
                updateTreeView(fileController.getFilesFrom(fileController.getPointer()));
            }
        });
    }
}