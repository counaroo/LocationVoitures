module com.couro.sadio.locationvoitures.locationvoitures {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires  java.naming;

    opens com.couro.sadio.locationvoitures.entities to org.hibernate.orm.core;

    opens com.couro.sadio.locationvoitures to javafx.fxml;
    opens com.couro.sadio.locationvoitures.controller to javafx.fxml;
    exports com.couro.sadio.locationvoitures;
    exports com.couro.sadio.locationvoitures.data;
    exports com.couro.sadio.locationvoitures.controller.adminControllers to javafx.fxml;

    opens com.couro.sadio.locationvoitures.data to javafx.fxml;
    exports com.couro.sadio.locationvoitures.Runtime;
    opens com.couro.sadio.locationvoitures.Runtime to javafx.fxml;
}