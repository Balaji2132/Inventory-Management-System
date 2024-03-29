package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.DropShadow;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class HomeController {

    @FXML
    private Button rawMaterialsButton;

    @FXML
    private Button customersButton;

    @FXML
    private Button ordersButton;

    @FXML
    private Button productsButton;

    @FXML
    private Button logoutButton;

    public void initialize() {
        applyDropShadow(rawMaterialsButton);
        applyDropShadow(customersButton);
        applyDropShadow(ordersButton);
        applyDropShadow(productsButton);
        applyDropShadow(logoutButton);
    }

    private void applyDropShadow(Button button) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(20);
        dropShadow.setSpread(0.2);
        button.setEffect(dropShadow);
    }

    @FXML
    private void openRawMaterials() {
        openPage("RawMaterials.fxml", "Raw Materials");
    }

    @FXML
    private void openCustomers() {
        openPage("Customers.fxml", "Customers");
    }

    @FXML
    private void openOrders() {
        openPage("Orders.fxml", "Orders");
    }

    @FXML
    private void openProducts() {
        openPage("Products.fxml", "Products");
    }

    @FXML
    private void displayMessage(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void openChangePasswordDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Change Password");
        dialog.setHeaderText("Enter your new password:");
        dialog.setContentText("New Password:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(password -> displayMessage("Password Changed", "Password has been changed successfully."));
    }

    @FXML
    private void logout() {
        // Implement the logic for logging out
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            openPage("Login.fxml", "Inventory Management - Login");
        }
    }

    private void openPage(String fxmlFile, String title) {
        try {
            Main.loadScene(fxmlFile, title);
        } catch (Exception e) {
            e.printStackTrace();
            displayMessage("Error", "Error loading " + title + " page.");
        }
    }
}
