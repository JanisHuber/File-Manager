package org.example.filemanager.ui;

import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class NameDialog {
    public static Optional<String> directoryName() {
        return showTextInputDialog("Ordner erstellen", "Gib den Namen des neuen Ordners ein:", "Ordnername:", "NeuerOrdner");
    }

    public static Optional<String> fileName() {
        return showTextInputDialog("Dokument erstellen", "Gib den Namen des neuen Dokumentes ein:", "Dokumentenname:", "Neues Dokument");
    }

    public static Optional<String> directoryRename(String currentName) {
        return showTextInputDialog("Ordner umbenennen", "Gib den neuen Namen des Ordners ein:", "Ordnername:", currentName);
    }

    public static Optional<String> fileRename(String currentName) {
        return showTextInputDialog("Dokument umbenennen", "Gib den neuen Namen des Dokumentes ein:", "Dokumentenname:", currentName);
    }

    private static Optional<String> showTextInputDialog(String title, String header, String content, String defaultValue) {
        TextInputDialog dialog = new TextInputDialog(defaultValue);
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);

        Optional<String> result = dialog.showAndWait();
        return result;
    }
}
