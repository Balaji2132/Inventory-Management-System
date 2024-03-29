package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void loginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Perform authentication against the database using UserDAO
        if (UserDAO.isValidUser(username, password)) {
            // Successful login, navigate to the home page
            Main.loadScene("Home.fxml", "Inventory Management - Home");
        } else {
            // Display an error message for invalid credentials
            displayError("Invalid Credentials", "Please check your username and password.");
        }
    }

    private void displayError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
