package pl.mswierczynski.pp5.productcatalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class JDBCProductStorage implements ProductStorage {

    private JdbcTemplate jdbcTemplate;

    public JDBCProductStorage(JdbcTemplate connection) {
        jdbcTemplate = connection;
    }

    @Override
    public void save(Product newProduct) {
        var query = "INSERT INTO `products_catalog__products` (`id`, `description`, `picture`, `price`) values" +
                "(?, ?, ? ,?);";
        jdbcTemplate.update(query, newProduct.getId(), newProduct.getDescription(), newProduct.getPicture(), newProduct.getPrice());
    }

    @Override
    public Optional<Product> getById(String productId) {
        var query = "SELECT * FROM `products_catalog__products` WHERE id = ?";

        Product product = jdbcTemplate.queryForObject(query, new Object[] {productId}, (rs, i) -> {
            Product p = new Product(UUID.fromString(rs.getString("id")));
            p.setDescription(rs.getString("description"));
            return p;
        });

        return Optional.ofNullable(product);
    }

    @Override
    public List<Product> getAllPublished() {
        var selectQuery = "SELECT * FROM `products_catalog__products` WHERE description IS NOT NULL AND price IS NOT NULL;";

        List<Product> products = jdbcTemplate.query(selectQuery, (rs, i) -> {
            Product p = new Product(UUID.fromString(rs.getString("id")));
            p.setDescription(rs.getString("description"));
            p.setPicture(rs.getString("picture"));
            p.setPrice(rs.getBigDecimal("price"));

            return p;
        });

        return products;
    }

    @Override
    public Product update(Product product) {
        var updateQuery = "UPDATE `products_catalog__products` set description = ?, price = ?, picture = ? WHERE id = ?;";
        jdbcTemplate.update(updateQuery, product.getDescription(), product.getPrice(), product.getPicture(), product.getId());
        var selectQuery = "SELECT * FROM `products_catalog__products` WHERE id = ?;";

        Product updatedProduct = jdbcTemplate.queryForObject(selectQuery, new Object[] {product.getId()}, (rs, i) -> {
            Product p = new Product(UUID.fromString(rs.getString("id")));
            p.setDescription(rs.getString("description"));
            p.setPrice(rs.getBigDecimal("price"));
            p.setPicture(rs.getString("picture"));
            return p;
        });

        return updatedProduct;
    }

    @Override
    public void delete(String productId) {
        var deleteQuery = "DELETE FROM `products_catalog__products` WHERE id = ?;";
        jdbcTemplate.update(deleteQuery, productId);
    }

}
