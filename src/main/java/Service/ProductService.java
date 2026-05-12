package Service;

import Database.dbconnect;
import Entities.Product;
import interfaces.Iservice;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductService implements Iservice<Product> {

    @Override
    public void create(Product product) throws SQLException {
        String requete = "INSERT INTO products (name, description, price, stock_quantity, category, expiration_date, photo_url, status, supplier_id, created_at, updated_at) VALUES (?,?,?,?,?,?,?,?,?,NOW(),NOW())";
        PreparedStatement statement = dbconnect.getInstance().getConnection().prepareStatement(requete);
        statement.setString(1, product.getName());
        statement.setString(2, product.getDescription());
        statement.setBigDecimal(3, product.getPrice());
        statement.setInt(4, product.getStockQuantity());
        statement.setString(5, product.getCategory());
        statement.setDate(6, product.getExpirationDate() != null ? java.sql.Date.valueOf(product.getExpirationDate()) : null);
        statement.setString(7, product.getPhotoUrl());
        statement.setString(8, product.getStatus());
        if (product.getSupplierId() != null) {
            statement.setInt(9, product.getSupplierId());
        } else {
            statement.setNull(9, java.sql.Types.INTEGER);
        }
        statement.executeUpdate();
    }

    @Override
    public List<Product> list() throws SQLException {
        String requete = "SELECT * FROM products";
        List<Product> products = new ArrayList<>();
        PreparedStatement statement = dbconnect.getInstance().getConnection().prepareStatement(requete);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            products.add(mapResultSetToProduct(rs));
        }
        return products;
    }

    @Override
    public Product read(int id) throws SQLException {
        String requete = "SELECT * FROM products WHERE id = ?";
        PreparedStatement statement = dbconnect.getInstance().getConnection().prepareStatement(requete);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            return mapResultSetToProduct(rs);
        }
        return null;
    }

    @Override
    public void update(Product product) throws SQLException {
        String requete = "UPDATE products SET name = ?, description = ?, price = ?, stock_quantity = ?, category = ?, expiration_date = ?, photo_url = ?, status = ?, supplier_id = ?, updated_at = NOW() WHERE id = ?";
        PreparedStatement statement = dbconnect.getInstance().getConnection().prepareStatement(requete);
        statement.setString(1, product.getName());
        statement.setString(2, product.getDescription());
        statement.setBigDecimal(3, product.getPrice());
        statement.setInt(4, product.getStockQuantity());
        statement.setString(5, product.getCategory());
        statement.setDate(6, product.getExpirationDate() != null ? java.sql.Date.valueOf(product.getExpirationDate()) : null);
        statement.setString(7, product.getPhotoUrl());
        statement.setString(8, product.getStatus());
        if (product.getSupplierId() != null) {
            statement.setInt(9, product.getSupplierId());
        } else {
            statement.setNull(9, java.sql.Types.INTEGER);
        }
        statement.setInt(10, product.getId());
        statement.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String requete = "DELETE FROM products WHERE id = ?";
        PreparedStatement statement = dbconnect.getInstance().getConnection().prepareStatement(requete);
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setId(rs.getInt("id"));
        p.setName(rs.getString("name"));
        p.setDescription(rs.getString("description"));
        p.setPrice(rs.getBigDecimal("price"));
        p.setStockQuantity(rs.getInt("stock_quantity"));
        p.setCategory(rs.getString("category"));
        if (rs.getDate("expiration_date") != null) {
            p.setExpirationDate(rs.getDate("expiration_date").toLocalDate());
        }
        p.setPhotoUrl(rs.getString("photo_url"));
        p.setStatus(rs.getString("status"));
        p.setCreatedAt(rs.getTimestamp("created_at"));
        p.setUpdatedAt(rs.getTimestamp("updated_at"));
        int supplierId = rs.getInt("supplier_id");
        if (!rs.wasNull()) {
            p.setSupplierId(supplierId);
        }
        return p;
    }
}
