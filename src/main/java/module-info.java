module com.couro.sadio.locationvoitures.locationvoitures {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.couro.sadio.locationvoitures to javafx.fxml;
    exports com.couro.sadio.locationvoitures;
}