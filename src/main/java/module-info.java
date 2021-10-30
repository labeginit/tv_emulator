module com.example.tv {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;
    requires tyrus.standalone.client;
    requires org.json;


    opens com.example.tv to javafx.fxml;
    exports com.example.tv;
}