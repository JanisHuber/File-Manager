module org.example.filemanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires java.xml.crypto;

    opens org.example.filemanager to javafx.fxml;
    exports org.example.filemanager;
    exports org.example.filemanager.UI;
    opens org.example.filemanager.UI to javafx.fxml;
    exports org.example.filemanager.filesearch;
    opens org.example.filemanager.filesearch to javafx.fxml;
    exports org.example.filemanager.filehandling;
    opens org.example.filemanager.filehandling to javafx.fxml;
}