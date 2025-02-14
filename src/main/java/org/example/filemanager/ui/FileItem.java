package org.example.filemanager.ui;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import org.example.filemanager.filehandling.File;
import org.example.filemanager.filehandling.FileController;

public class FileItem {
    FileController fileController;
    SceneController sceneController;
    FileItemActions fileItemActions;

    public FileItem(FileController fileController, SceneController sceneController) {
        this.fileController = fileController;
        this.sceneController = sceneController;
        this.fileItemActions = new FileItemActions(fileController, sceneController);
    }

    public HBox createFileItem(File file) {
        ImageView imageView = new ImageView();
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        imageView.setImage(new Image(FileItem.class.getResourceAsStream((file.isDirectory()) ? "/icons/folder.png" : "/icons/file.png")));
        Label nameLabel = new Label(file.getName());
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Label dateLabel = new Label(file.getDate());

        HBox fileItemBox = new HBox(10, imageView, nameLabel, spacer, dateLabel);

        if (fileController.chosenFile != null && fileController.chosenFile.getName().equals(file.getName())) {
            fileItemBox.setStyle("-fx-background-color: #6ba2c9;");
        }

        fileItemBox.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (event.getClickCount() == 1) {
                fileItemActions.handleSingleClick(file);
            } else if (event.getClickCount() == 2) {
                fileItemActions.handleDoubleClick(file);
            }
        });
        return fileItemBox;
    }
}
