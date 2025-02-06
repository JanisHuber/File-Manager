package org.example.filemanager;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class SceneController {
    @FXML
    private AnchorPane anchorPane;
    FileController fileController = new FileController();

    @FXML
    private void initialize() {
        System.out.println("SceneController initialized");
    }

    @FXML
    public void updateTreeView() {
        anchorPane.getChildren().clear();
        List<File> files = fileController.getFilesFrom(fileController.getPointer());



        for (File file : files) {
            anchorPane.getChildren().add(file);
        }
    }

    @FXML
    public void updateListView() {

    }

    @FXML
    public void updatePathView() {

    }
}