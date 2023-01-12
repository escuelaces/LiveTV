module com.escuelaces.livetv {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.controlsfx.controls;

    opens com.escuelaces.livetv to javafx.fxml;
    exports com.escuelaces.livetv;
}