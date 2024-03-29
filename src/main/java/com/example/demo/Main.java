package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.util.List;

public class Main extends Application {
    private static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
        List<User> userList = UserDAO.getAllUsers();

        // Display user information
        for (User user : userList) {
            System.out.println("Username: " + user.getUsername() + ", Password: " + user.getPassword());
        }
    }

    @Override
    public void start(Stage primaryStage)  {
        Main.primaryStage = primaryStage;
        loadScene("Login.fxml", "Inventory Management - Login");
    }

    public static void loadScene(String fxmlFile, String title){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlFile));
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle(title);
            primaryStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}