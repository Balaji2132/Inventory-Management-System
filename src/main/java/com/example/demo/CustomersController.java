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

public class CustomersController {

    @FXML
    private TableView<Customer> customersTable;

    @FXML
    private TableColumn<Customer, Integer> customerIdColumn;

    @FXML
    private TableColumn<Customer, String> customerNameColumn;

    @FXML
    private TableColumn<Customer, String> emailColumn;

    @FXML
    private TableColumn<Customer, String> phoneNumberColumn;

    @FXML
    private TextField customerNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private void initialize() {
        // Clear existing items
        customersTable.getItems().clear();

        // Initialize TableColumn with PropertyValueFactory
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        addButton.setOnAction(this::handleAddButton);
        deleteButton.setOnAction(this::handleDeleteButton);

        fetchDataFromDatabase("customers");
    }

    private void fetchDataFromDatabase(String tableName) {
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + tableName);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Process the result set and display data
            while (resultSet.next()) {
                int customerId = resultSet.getInt("customer_id");
                String customerName = resultSet.getString("customer_name");
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phone_number");

                // Add logic to display or process the customer data
                Customer customer = new Customer(customerId, customerName, email, phoneNumber);
                customersTable.getItems().add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddButton(ActionEvent event) {
        try {
            String customerName = customerNameField.getText();
            String email = emailField.getText();
            String phoneNumber = phoneNumberField.getText();

            // Add a new Customer with auto-incremented Customer ID
            Customer newCustomer = new Customer(0, customerName, email, phoneNumber);

            // Add the new Customer to the table
            customersTable.getItems().add(newCustomer);

            clearInputFields();

        } catch (NumberFormatException e) {
            showAlert("Invalid input. Please enter valid values.");
        }
    }

   @FXML
    private void handleDeleteButton(ActionEvent event) {
        Customer selectedCustomer = customersTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer != null) {
            customersTable.getItems().remove(selectedCustomer);
        } else {
            showAlert("No item selected. Please select a row to delete.");
        }
    }

    private void clearInputFields() {
        customerNameField.clear();
        emailField.clear();
        phoneNumberField.clear();
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
