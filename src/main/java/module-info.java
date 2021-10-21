module com.example.tv {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tv to javafx.fxml;
    exports com.example.tv;
}