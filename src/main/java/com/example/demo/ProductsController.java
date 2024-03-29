package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductsController {

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Product, Integer> productIdColumn;

    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private TableColumn<Product, String> descriptionColumn;

    @FXML
    private TableColumn<Product, Double> unitPriceColumn;

    @FXML
    private TextField productNameField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField unitPriceField;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private void initialize() {
        // Set cell value factories for each column
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        addButton.setOnAction(this::handleAddButton);
        deleteButton.setOnAction(this::handleDeleteButton);

        // Fetch data from the database
        fetchDataFromDatabase("products");
    }

    private void fetchDataFromDatabase(String tableName) {
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + tableName);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Clear existing data
            productsTable.getItems().clear();

            // Process the result set and display data
            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String productName = resultSet.getString("product_name");
                String description = resultSet.getString("description");
                double unitPrice = resultSet.getDouble("unit_price");

                // Add logic to display or process the product data
                Product product = new Product(productId, productName, description, unitPrice);
                productsTable.getItems().add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddButton(ActionEvent event) {
        try {
            String productName = productNameField.getText();
            String description = descriptionField.getText();
            double unitPrice = Double.parseDouble(unitPriceField.getText());

            // Add a new Product with auto-incremented Product ID
            Product newProduct = new Product(0, productName, description, unitPrice);

            // Add the new Product to the table
            productsTable.getItems().add(newProduct);

            clearInputFields();

        } catch (NumberFormatException e) {
            showAlert("Invalid input. Please enter valid values.");
        }
    }

    @FXML
    private void handleDeleteButton(ActionEvent event) {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {
            productsTable.getItems().remove(selectedProduct);
        } else {
            showAlert("No item selected. Please select a row to delete.");
        }
    }

    private void clearInputFields() {
        productNameField.clear();
        descriptionField.clear();
        unitPriceField.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void goBackToHome() {
        Main.loadScene("Home.fxml", "Inventory Management - Home");
    }
}
