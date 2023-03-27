module com.example.projet_reseaux {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.projet_reseaux to javafx.fxml;
    exports com.example.projet_reseaux;
}