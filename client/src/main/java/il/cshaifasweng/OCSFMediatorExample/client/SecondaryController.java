package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SecondaryController {

    @FXML
    private ComboBox<String> mealComboBox; // Dropdown for meal selection

    @FXML
    private TextField newPriceField; // Field for entering the new price

    @FXML
    private Label statusLabel; // Label to display the update status (success/failure)

    @FXML
    private Button switchToPrimaryButton; // Button to switch to primary view

    @FXML
    private void initialize() {
        // Populate the combo box with menu items
        mealComboBox.getItems().addAll(
                "Pizza", "Hamburger", "Veggie Hamburger",
                "Sour Cream Spinach Pasta", "Caesar Salad",
                "Fatto Tiramisu", "Scugnizzielli Nutella Gelato",
                "Lemon Meringue", "Chocolate Salted Caramel",
                "Espresso", "Macchiato"
        );

        // Ensure the combo box starts with no selection
        mealComboBox.setValue(null);

        // Clear the status label
        statusLabel.setText("");

        // Set action for switching back to the primary view
        if (switchToPrimaryButton != null) {
            switchToPrimaryButton.setOnAction(event -> {
                try {
                    switchToPrimary();
                } catch (IOException e) {
                    e.printStackTrace();
                    statusLabel.setText("Error switching to primary view.");
                }
            });
        }
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary1"); // Load the primary view FXML file
    }
// nothing
    @FXML
    private void updatePrice() {
        // Get selected meal and entered price
        String selectedMeal = mealComboBox.getValue();
        String newPriceText = newPriceField.getText();

        // Validate input
        if (selectedMeal == null || selectedMeal.isEmpty()) {
            statusLabel.setText("Please select a meal.");
            return;
        }
        if (newPriceText == null || newPriceText.isEmpty()) {
            statusLabel.setText("Please enter a new price.");
            return;
        }

        try {
            double newPrice = Double.parseDouble(newPriceText);

            if (newPrice <= 0) {
                statusLabel.setText("Price must be a positive value.");
                return;
            }

            // Send the request to the server
            String request = "UPDATE_PRICE;" + selectedMeal + ";" + newPrice;
            SimpleClient.getClient().sendToServer(request);

            statusLabel.setText("Price update request sent for " + selectedMeal + ".");
        } catch (NumberFormatException e) {
            statusLabel.setText("Invalid price format. Please enter a valid number.");
        } catch (IOException e) {
            statusLabel.setText("Failed to connect to the server.");
        }
    }
}
