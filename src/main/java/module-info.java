module com.couro.sadio.locationvoitures.locationvoitures {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;

    // Ouvrir entities à la fois à Hibernate et JavaFX Base
    opens com.couro.sadio.locationvoitures.entities to org.hibernate.orm.core, javafx.base;

    opens com.couro.sadio.locationvoitures to javafx.fxml;
    opens com.couro.sadio.locationvoitures.controller to javafx.fxml;
    opens com.couro.sadio.locationvoitures.controller.adminControllers to javafx.fxml;
    opens com.couro.sadio.locationvoitures.data to javafx.fxml;
    opens com.couro.sadio.locationvoitures.Runtime to javafx.fxml;

    exports com.couro.sadio.locationvoitures;
    exports com.couro.sadio.locationvoitures.data;
    exports com.couro.sadio.locationvoitures.controller.adminControllers to javafx.fxml;
    exports com.couro.sadio.locationvoitures.Runtime;
}