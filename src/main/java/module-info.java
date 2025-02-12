module org.example.filemanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens org.example.filemanager to javafx.fxml;
    exports org.example.filemanager;
}