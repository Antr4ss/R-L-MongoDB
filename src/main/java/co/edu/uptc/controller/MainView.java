package co.edu.uptc.controller;

import co.edu.uptc.view.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class MainView {
    @FXML
    Button registerButton;

    @FXML
    public void registerButton() throws IOException {
        Main.setRoot("register");
    }

    public void loginButton() throws IOException {
        Main.setRoot("login");
    }
}
