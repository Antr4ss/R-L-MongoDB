package co.edu.uptc.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class Main extends Application {
    private static Stage primaryStage;

    private static void main(String[] args) {
        launch();
    }

    public static void setRoot(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource(fxml + ".fxml")));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));

    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle("Opciones");
        setRoot("main");
        primaryStage.show();
    }



}