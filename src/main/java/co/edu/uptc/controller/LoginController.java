package co.edu.uptc.controller;

import co.edu.uptc.view.Main;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.bson.Document;

import java.io.IOException;

public class LoginController {
    @FXML
    Button backButton;
    @FXML
    private TextField userField;
    @FXML
    private TextField passwordField;
    @FXML
    Button registerButton;

    @FXML
    private void login(ActionEvent event) throws Exception {
        String user = userField.getText();
        String password = passwordField.getText();
        if (user.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Input Error");
            alert.setContentText("All fields must be filled");
            alert.showAndWait();
            return;
        }
        ConnectionString connectionString = new ConnectionString(
                "mongodb+srv://haroldguerrero02:UhfpnBBig38ssicV@cluster0.symk4rx.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
        );
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("CRUD"); // Use your database name
            MongoCollection<Document> collection = database.getCollection("Personas"); // Use your collection name

            Document foundUser = collection.find(Filters.and(Filters.eq("User", user), Filters.eq("password", password))).first();
            if (foundUser == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Login Error");
                alert.setContentText("Invalid username or password");
                alert.showAndWait();
            } else {
                // User found, proceed with login
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login Successful");
                alert.setContentText("Welcome back, " + user + "!");
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void backButton() throws IOException {
        Main.setRoot("main");
    }
    @FXML
    public void registerButton() throws IOException {
        Main.setRoot("register");
    }

}
