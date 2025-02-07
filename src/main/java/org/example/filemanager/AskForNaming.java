package org.example.filemanager;

import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class AskForNaming {
    public static String askForDirectoryName() {
        TextInputDialog dialog = new TextInputDialog("NeuerOrdner");
        dialog.setTitle("Ordner erstellen");
        dialog.setHeaderText("Gib den Namen des neuen Ordners ein:");
        dialog.setContentText("Ordnername:");

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    public static String askForFileName() {
        TextInputDialog dialog = new TextInputDialog("Neues Dokument");
        dialog.setTitle("Dokument erstellen");
        dialog.setHeaderText("Gib den Namen des neuen Dokumentes ein:");
        dialog.setContentText("Dokumentenname:");

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }


    public static String askForDirectoryRename(String directoryName) {
        TextInputDialog dialog = new TextInputDialog(directoryName);
        dialog.setTitle("Ordner umbennen");
        dialog.setHeaderText("Gib den neuen Namen des Ordners ein:");
        dialog.setContentText("Ordnername:");

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    public static String askForFileRename(String fileName) {
        TextInputDialog dialog = new TextInputDialog(fileName);
        dialog.setTitle("Dokument umbennenen");
        dialog.setHeaderText("Gib den neuen Namen des Dokumentes ein:");
        dialog.setContentText("Dokumentenname:");

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }
}
