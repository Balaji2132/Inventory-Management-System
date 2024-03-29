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

public class OrdersController {

    @FXML
    private TableView<Order> ordersTable;

    @FXML
    private TableColumn<Order, Integer> orderIdColumn;

    @FXML
    private TableColumn<Order, Integer> customerIdColumn;

    @FXML
    private TableColumn<Order, String> orderDateColumn;

    @FXML
    private TableColumn<Order, Double> totalAmountColumn;

    @FXML
    private TextField customerIdField;

    @FXML
    private TextField orderDateField;

    @FXML
    private TextField totalAmountField;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private void initialize() {
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        totalAmountColumn.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));

        addButton.setOnAction(this::handleAddButton);
        deleteButton.setOnAction(this::handleDeleteButton);

        fetchDataFromDatabase("orders");
    }

    private void fetchDataFromDatabase(String tableName) {
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + tableName);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Clear existing data
            ordersTable.getItems().clear();

            // Process the result set and add data to the table
            while (resultSet.next()) {
                int orderId = resultSet.getInt("order_id");
                int customerId = resultSet.getInt("customer_id");
                String orderDate = resultSet.getString("order_date");
                double totalAmount = resultSet.getDouble("total_amount");

                Order order = new Order(orderId, customerId, orderDate, totalAmount);
                ordersTable.getItems().add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddButton(ActionEvent event) {
        try {
            int customerId = Integer.parseInt(customerIdField.getText());
            String orderDate = orderDateField.getText();
            double totalAmount = Double.parseDouble(totalAmountField.getText());

            // Add a new Order with auto-incremented Order ID
            Order newOrder = new Order(0, customerId, orderDate, totalAmount);

            // Add the new Order to the table
            ordersTable.getItems().add(newOrder);

            clearInputFields();

        } catch (NumberFormatException e) {
            showAlert("Invalid input. Please enter valid values.");
        }
    }

    @FXML
    private void handleDeleteButton(ActionEvent event) {
        Order selectedOrder = ordersTable.getSelectionModel().getSelectedItem();

        if (selectedOrder != null) {
            ordersTable.getItems().remove(selectedOrder);
        } else {
            showAlert("No item selected. Please select a row to delete.");
        }
    }

    private void clearInputFields() {
        customerIdField.clear();
        orderDateField.clear();
        totalAmountField.clear();
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
