package co.edu.uptc.controller;

import co.edu.uptc.model.Persona;
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
import java.util.regex.Pattern;

public class RegisterController {

    @FXML
    Button loginButton;
    @FXML
    Button backButton;
    @FXML
    private TextField nameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField confirmPasswordField;
    @FXML
    private TextField userField;

    @FXML
    public void register(ActionEvent event) throws Exception {
        Persona persona = new Persona(
                nameField.getText(),
                lastNameField.getText(),
                emailField.getText(),
                userField.getText(),
                passwordField.getText(),
                confirmPasswordField.getText()

        );

        // Data validation
        if (persona.getName() == null || persona.getName().isEmpty() ||
                persona.getLastName() == null || persona.getLastName().isEmpty() ||
                persona.getEmail() == null || persona.getEmail().isEmpty() ||
                persona.getUser() == null || persona.getUser().isEmpty() ||
                persona.getPassword() == null || persona.getPassword().isEmpty() ||
                persona.getPasswordConfirmation() == null || persona.getPasswordConfirmation().isEmpty()
        ){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Input Error");
            alert.setContentText("All fields must be filled");
            alert.showAndWait();
            return;
        }

        if (!persona.getPassword().equals(persona.getPasswordConfirmation())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Input Error");
            alert.setContentText("Passwords do not match");
            alert.showAndWait();
            return;
        }
        try {
            if (!Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", persona.getEmail())) {
                throw new Exception("Invalid email format");
            }

            if (!Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$", persona.getPassword())) {
                throw new Exception("Password must have at least 8 characters, one uppercase letter and one number");
            }


            ConnectionString connectionString = new ConnectionString(
                    "mongodb+srv://haroldguerrero02:UhfpnBBig38ssicV@cluster0.symk4rx.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
            );
            try (MongoClient mongoClient = MongoClients.create(connectionString)) {
                MongoDatabase database = mongoClient.getDatabase("CRUD"); // Use your database name
                MongoCollection<Document> collection = database.getCollection("Personas"); // Use your collection name

                // Check if user already exists
                Document existingUser = collection.find(Filters.eq("User", persona.getUser())).first();
                Document existingEmail = collection.find(Filters.eq("email", persona.getEmail())).first();
                if (existingUser != null || existingEmail != null) {
                    throw new Exception("User already exists");
                }

                Document doc = new Document("name", persona.getName())
                        .append("lastName", persona.getLastName())
                        .append("email", persona.getEmail())
                        .append("User", persona.getUser())
                        .append("password", persona.getPassword());
                collection.insertOne(doc);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success Register");
            alert.setContentText("User registered successfully");
            alert.showAndWait();

            nameField.clear();
            lastNameField.clear();
            emailField.clear();
            passwordField.clear();
            confirmPasswordField.clear();
            userField.clear();

        } catch (Exception e) {
            if (e.getMessage().equals("User already exists")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Registration Error");
                alert.setContentText("User already exists");
                alert.showAndWait();
            } else {
                // Handle other exceptions...
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Registration Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }

    }

    @FXML
    public void backButton() throws IOException {
        Main.setRoot("main");
    }

    @FXML
    public void loginButton() throws IOException {
        Main.setRoot("login");
    }
}