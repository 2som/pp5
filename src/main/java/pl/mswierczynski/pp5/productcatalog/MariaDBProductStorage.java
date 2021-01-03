package pl.mswierczynski.pp5.productcatalog;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class MariaDBProductStorage implements ProductStorage{
    private Connection connection;
    private String tableName;
    public MariaDBProductStorage(Connection connection, String tableName) {
        this.tableName = tableName;
        this.connection = connection;
    }

    @Override
    public void save(Product newProduct) {
        try {
            PreparedStatement query = connection.prepareStatement("INSERT INTO " + tableName + " (id, description, picture, price ) values (?,?,?,?);");
            query.setString(1, newProduct.getId());
            query.setString(2, newProduct.getDescription());
            query.setString(3, newProduct.getPicture());
            query.setBigDecimal(4, newProduct.getPrice());
            query.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public Optional<Product> getById(String productId) {
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM " + tableName +" WHERE id = ?");
            query.setString(1, productId);
            var result = query.executeQuery();
            result.first();
            String id = result.getString(1);
            String description = result.getString(2);
            String picture = result.getString(3);
            BigDecimal price = result.getBigDecimal(4);
            Product p = new Product(UUID.fromString(id));
            p.setDescription(description);
            p.setPicture(picture);
            p.setPrice(price);
            return Optional.ofNullable(p);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Product> getAllPublished() {
        List<Product> products = new ArrayList<>(Collections.emptyList());
        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM " + tableName + ";");

            var result = query.executeQuery();
            while(result.next()) {
                Product p = new Product(UUID.fromString(result.getString(1)));
                p.setDescription(result.getString(2));
                p.setPicture(result.getString(3));
                p.setPrice(result.getBigDecimal(4));
                products.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return products;
    }

    @Override
    public void delete(String productId) {
        try {
            PreparedStatement query = connection.prepareStatement("DELETE FROM " + tableName + " WHERE id = ?;");
            query.setString(1, productId);
            query.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Product update(Product p) {
        try {
            PreparedStatement query = connection.prepareStatement("UPDATE "+ tableName + " set description = ?, price = ?, picture = ? WHERE id = ?;");
            query.setString(1, p.getDescription());
            query.setBigDecimal(2, p.getPrice());
            query.setString(3, p.getPicture());
            query.setString(4, p.getId());
            System.out.println(query);
            query.executeUpdate();
            return p;
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
