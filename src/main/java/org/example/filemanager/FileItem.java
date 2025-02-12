package org.example.filemanager;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class FileItem {
    FileController fileController;

    public static HBox createFileItem(String fileName, String filepath, String fileDate, boolean isDirectory, FileController fileController, SceneController sceneController) {
        ImageView imageView = new ImageView();
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        if (isDirectory) {
            imageView.setImage(new Image(FileItem.class.getResourceAsStream("/icons/folder.png")));
        } else {
            imageView.setImage(new Image(FileItem.class.getResourceAsStream("/icons/file.png")));
        }

        Label nameLabel = new Label(fileName);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label dateLabel = new Label(fileDate);

        HBox fileItemBox = new HBox(10, imageView, nameLabel, spacer,  dateLabel);

        if (fileController.chosenFile != null && fileController.chosenFile.getName().equals(fileName)) {
            fileItemBox.setStyle("-fx-background-color: #6ba2c9;");
        }

        fileItemBox.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    if (isDirectory) {
                        String NextPointer = fileController.pointerHistory.getPointerForward();

                        if (!NextPointer.equals(fileController.getPointer() + fileName + "\\")) {
                            fileController.pointerHistory.removePointersAtIndex(fileController.pointerHistory.getPointerHistory().indexOf(fileController.getPointer()));
                        }
                        if (fileController.chosenFile != null) {
                            fileController.chosenFile.setIsChosenTo(false);
                            fileController.chosenFile = null;
                        }
                        fileController.setPointer(fileController.getPointer() + fileName + "\\");
                        sceneController.updateTreeView(fileController.getFilesFrom(fileController.getPointer()));
                        sceneController.updatePathView();
                    }
                } else {
                    if (fileController.chosenFile != null) {
                        fileController.chosenFile.setIsChosenTo(false);
                        if (fileController.chosenFile.getName().equals(fileName)) {
                            fileController.chosenFile = null;
                            sceneController.updateTreeView(fileController.getFilesFrom(fileController.getPointer()));
                            return;
                        }
                    }
                    fileController.chosenFile = new File(fileName, 0, fileDate, filepath, isDirectory);
                    fileController.chosenFile.setIsChosenTo(true);
                    sceneController.updateTreeView(fileController.getFilesFrom(fileController.getPointer()));
                }
            }
        });
        return fileItemBox;
    }
}
