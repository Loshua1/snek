module com.example.snek {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires javafx.graphics;
    requires javafx.base;
    requires com.almasb.fxgl.entity;

    opens com.example.snek to javafx.fxml;
    exports com.example.snek;
    exports com.example.snek.unneeded;
    opens com.example.snek.unneeded to javafx.fxml;
}