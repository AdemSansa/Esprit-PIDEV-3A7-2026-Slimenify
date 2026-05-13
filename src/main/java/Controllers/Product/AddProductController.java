package Controllers.Product;

import Entities.Product;
import Service.ProductService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import util.SceneManager;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

public class AddProductController {

    @FXML private TextField nameField;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private TextField priceField;
    @FXML private TextField stockField;
    @FXML private TextField photoUrlField;
    @FXML private DatePicker expirationDatePicker;
    @FXML private ComboBox<String> statusComboBox;
    @FXML private TextArea descriptionArea;
    @FXML private StackPane imagePlaceholder;

    private File selectedImageFile;
    private final ProductService productService = new ProductService();

    @FXML
    public void initialize() {
        categoryComboBox.setItems(FXCollections.observableArrayList(
                "Authorized Vitamins", "Psychology Books", "Relaxing Products", "Therapeutic Games & Activities"
        ));
        
        statusComboBox.setItems(FXCollections.observableArrayList(
                "available", "unavailable", "out_of_stock"
        ));
        
        statusComboBox.setValue("available");
    }

    @FXML
    public void handleBrowseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Product Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(nameField.getScene().getWindow());
        if (selectedFile != null) {
            this.selectedImageFile = selectedFile;
            photoUrlField.setText(selectedFile.getName() + " (Ready to upload)");
            
            // Update Preview
            try {
                javafx.scene.image.Image image = new javafx.scene.image.Image(selectedFile.toURI().toString());
                javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(image);
                imageView.setFitHeight(230);
                imageView.setFitWidth(230);
                imageView.setPreserveRatio(true);
                imagePlaceholder.getChildren().clear();
                imagePlaceholder.getChildren().add(imageView);
                imagePlaceholder.setStyle("-fx-background-color: transparent; -fx-border-style: solid; -fx-border-color: #7F8F69;");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void handleSave() {
        if (!validateInputs()) {
            return;
        }

        Product product = new Product();
        product.setName(nameField.getText());
        product.setCategory(categoryComboBox.getValue());
        product.setPrice(new BigDecimal(priceField.getText()));
        product.setStockQuantity(Integer.parseInt(stockField.getText()));
        
        // Handle Cloudinary Upload
        String photoUrl = photoUrlField.getText();
        if (selectedImageFile != null) {
            try {
                photoUrl = util.CloudinaryUtil.upload(selectedImageFile);
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Upload Error", "Could not upload image to Cloudinary: " + e.getMessage());
                return;
            }
        }
        
        product.setPhotoUrl(photoUrl);
        product.setExpirationDate(expirationDatePicker.getValue());
        product.setStatus(statusComboBox.getValue());
        product.setDescription(descriptionArea.getText());
        // supplierId is optional, leaving it null for now or could be linked to current user if they are a supplier

        try {
            productService.create(product);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Product created successfully!");
            handleBack();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not save product: " + e.getMessage());
        }
    }

    @FXML
    public void handleBack() {
        SceneManager.loadPage("/com/example/psy/Product/Shop.fxml");
    }

    private boolean validateInputs() {
        StringBuilder errors = new StringBuilder();

        if (nameField.getText().isEmpty()) errors.append("- Name is required\n");
        if (categoryComboBox.getValue() == null) errors.append("- Category is required\n");
        
        try {
            new BigDecimal(priceField.getText());
        } catch (Exception e) {
            errors.append("- Price must be a valid number\n");
        }

        try {
            Integer.parseInt(stockField.getText());
        } catch (Exception e) {
            errors.append("- Stock must be a valid integer\n");
        }

        if (errors.length() > 0) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fix the following errors:\n" + errors.toString());
            return false;
        }

        return true;
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
