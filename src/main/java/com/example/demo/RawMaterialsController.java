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

public class RawMaterialsController {

    @FXML
    private TableView<RawMaterial> rawMaterialsTable;

    @FXML
    private TableColumn<RawMaterial, Integer> materialIdColumn;

    @FXML
    private TableColumn<RawMaterial, String> materialNameColumn;

    @FXML
    private TableColumn<RawMaterial, Integer> quantityColumn;

    @FXML
    private TableColumn<RawMaterial, Double> unitPriceColumn;

    @FXML
    private TextField materialNameField;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField unitPriceField;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private void initialize() {
        materialIdColumn.setCellValueFactory(new PropertyValueFactory<>("materialId"));
        materialNameColumn.setCellValueFactory(new PropertyValueFactory<>("materialName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        fetchDataFromDatabase("raw_materials");

        addButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        deleteButton.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white;");

        addButton.setOnAction(this::handleAddButton);
        deleteButton.setOnAction(this::handleDeleteButton);
    }

    private void fetchDataFromDatabase(String tableName) {
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + tableName);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            rawMaterialsTable.getItems().clear();

            while (resultSet.next()) {
                int materialId = resultSet.getInt("material_id");
                String materialName = resultSet.getString("material_name");
                int quantity = resultSet.getInt("quantity");
                double unitPrice = resultSet.getDouble("unit_price");

                rawMaterialsTable.getItems().add(new RawMaterial(materialId, materialName, quantity, unitPrice));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error fetching data from the database.");
        }
    }

    @FXML
    private void handleAddButton(ActionEvent event) {
        try {
            // Exclude Material ID from user input
            String materialName = materialNameField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            double unitPrice = Double.parseDouble(unitPriceField.getText());

            // Start auto-increment from the last Material ID
            int nextMaterialId = getNextMaterialId();

            // Add a new RawMaterial with auto-incremented Material ID
            RawMaterial newMaterial = new RawMaterial(nextMaterialId, materialName, quantity, unitPrice);

            // Add the new RawMaterial to the table
            rawMaterialsTable.getItems().add(newMaterial);

            clearInputFields();

        } catch (NumberFormatException e) {
            showAlert("Invalid input. Please enter valid values.");
        }
    }

    private int getNextMaterialId() {
        int lastMaterialId = 0;

        // Get the last Material ID from the table
        if (!rawMaterialsTable.getItems().isEmpty()) {
            lastMaterialId = rawMaterialsTable.getItems().get(rawMaterialsTable.getItems().size() - 1).getMaterialId();
        }

        // Start auto-increment from the last Material ID
        return lastMaterialId + 1;
    }

    @FXML
    private void handleDeleteButton(ActionEvent event) {
        RawMaterial selectedMaterial = rawMaterialsTable.getSelectionModel().getSelectedItem();

        if (selectedMaterial != null) {
            rawMaterialsTable.getItems().remove(selectedMaterial);
        } else {
            showAlert("No item selected. Please select a row to delete.");
        }
    }

    private void clearInputFields() {
        materialNameField.clear();
        quantityField.clear();
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
        Main.loadScene("home.fxml", "Inventory Management - Home");
    }
}
