package org.example.filemanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FileManagerApp extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FileManagerApp.class.getResource("FileManagerView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1512, 800);
        stage.setTitle("FileManager");
        stage.setScene(scene);
        stage.show();
    }
}