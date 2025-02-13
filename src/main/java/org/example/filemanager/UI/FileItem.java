package org.example.filemanager.UI;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import org.example.filemanager.filehandling.File;
import org.example.filemanager.filehandling.FileController;

import java.awt.*;
import java.nio.file.Path;

public class FileItem {
    public static HBox createFileItem(String fileName, Path filepath, String fileDate, boolean isDirectory, FileController fileController, SceneController sceneController) {
        ImageView imageView = new ImageView();
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);

        imageView.setImage(new Image(FileItem.class.getResourceAsStream((isDirectory) ? "/icons/folder.png" : "/icons/file.png")));

        Label nameLabel = new Label(fileName);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label dateLabel = new Label(fileDate);

        HBox fileItemBox = new HBox(10, imageView, nameLabel, spacer, dateLabel);

        if (fileController.chosenFile != null && fileController.chosenFile.getName().equals(fileName)) {
            fileItemBox.setStyle("-fx-background-color: #6ba2c9;");
        }

        fileItemBox.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (event.getClickCount() == 2) {
                if (isDirectory) {
                    Path NextPointer = fileController.getPointerForward();

                    if (!NextPointer.equals(filepath.resolve(fileName))) {
                        fileController.removePointersAtIndex(fileController.getPointerHistory().indexOf(fileController.getPointer()));
                    }
                    if (fileController.chosenFile != null) {
                        fileController.chosenFile.setIsChosenTo(false);
                        fileController.chosenFile = null;
                    }

                    Path nextPointer = filepath.resolve(fileName);
                    fileController.setPointer(nextPointer);
                    sceneController.updateTreeView(fileController.getFilesFrom(fileController.getPointer()));
                    sceneController.updatePathView();
                } else {
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            desktop.open(new java.io.File(filepath + "\\" + fileName));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
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
                fileController.setPointer(filepath);
                sceneController.updateTreeView(fileController.getFilesFrom(fileController.getPointer()));
            }
        });
        return fileItemBox;
    }
}
