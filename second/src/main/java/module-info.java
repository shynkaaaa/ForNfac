module com.example.second {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.second to javafx.fxml;
    exports com.example.second;
}