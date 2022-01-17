module com.ajaguilar.Multihilos {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.ajaguilar.Multihilos to javafx.fxml;
    exports com.ajaguilar.Multihilos;
}
