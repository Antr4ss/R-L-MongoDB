module Base.Datos {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;
    requires org.mongodb.driver.sync.client;

    opens co.edu.uptc.view to javafx.fxml;

    exports co.edu.uptc.view;
    exports co.edu.uptc.controller;
    opens co.edu.uptc.controller to javafx.fxml;
    exports co.edu.uptc.model;


}