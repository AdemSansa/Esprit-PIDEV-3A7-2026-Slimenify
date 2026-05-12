package Controllers.Product;

import Entities.Product;
import Service.ProductService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ShopController {

    @FXML
    private FlowPane productsFlowPane;
    @FXML
    private TextField searchField;

    private final ProductService productService = new ProductService();
    private List<Product> allProducts;

    @FXML
    public void initialize() {
        loadProducts();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterProducts(newValue);
        });
    }

    private void loadProducts() {
        try {
            allProducts = productService.list();
            displayProducts(allProducts);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayProducts(List<Product> products) {
        productsFlowPane.getChildren().clear();
        for (Product product : products) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/psy/Product/ProductCard.fxml"));
                Node card = loader.load();
                ProductCardController controller = loader.getController();
                controller.setData(product);
                productsFlowPane.getChildren().add(card);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void filterProducts(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            displayProducts(allProducts);
            return;
        }

        String lowerKeyword = keyword.toLowerCase();
        List<Product> filtered = allProducts.stream()
                .filter(p -> p.getName().toLowerCase().contains(lowerKeyword) || 
                             p.getCategory().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
        
        displayProducts(filtered);
    }
}
