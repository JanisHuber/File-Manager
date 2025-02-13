package org.example.filemanager.UI;

import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class NameDialog {
    public static String directoryName() {
        TextInputDialog dialog = new TextInputDialog("NeuerOrdner");
        dialog.setTitle("Ordner erstellen");
        dialog.setHeaderText("Gib den Namen des neuen Ordners ein:");
        dialog.setContentText("Ordnername:");

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    public static String fileName() {
        TextInputDialog dialog = new TextInputDialog("Neues Dokument");
        dialog.setTitle("Dokument erstellen");
        dialog.setHeaderText("Gib den Namen des neuen Dokumentes ein:");
        dialog.setContentText("Dokumentenname:");

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }


    public static String directoryRename(String directoryName) {
        TextInputDialog dialog = new TextInputDialog(directoryName);
        dialog.setTitle("Ordner umbennen");
        dialog.setHeaderText("Gib den neuen Namen des Ordners ein:");
        dialog.setContentText("Ordnername:");

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    public static String fileRename(String fileName) {
        TextInputDialog dialog = new TextInputDialog(fileName);
        dialog.setTitle("Dokument umbennenen");
        dialog.setHeaderText("Gib den neuen Namen des Dokumentes ein:");
        dialog.setContentText("Dokumentenname:");

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }
}
