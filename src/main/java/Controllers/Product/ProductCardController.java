package Controllers.Product;

import Entities.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class ProductCardController {

    @FXML
    private Label categoryLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label stockLabel;
    @FXML
    private ImageView productImageView;
    @FXML
    private StackPane imageContainer;

    private Product product;

    public void setData(Product product) {
        this.product = product;

        nameLabel.setText(product.getName());
        categoryLabel.setText(product.getCategory());
        priceLabel.setText("$" + product.getPrice().toString());
        stockLabel.setText(String.valueOf(product.getStockQuantity()));

        if (product.getPhotoUrl() != null && !product.getPhotoUrl().isEmpty()) {
            try {
                productImageView.setImage(new Image(product.getPhotoUrl(), true));
            } catch (Exception e) {
                System.err.println("Error loading image: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleAddToCart() {
        System.out.println("Product added to cart: " + product.getName());
        // Cart logic can be added later
    }
}
