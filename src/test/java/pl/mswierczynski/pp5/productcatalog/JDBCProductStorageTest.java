package pl.mswierczynski.pp5.productcatalog;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JDBCProductStorageTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ProductStorage productStorage;


    @Before
    public void setUp() {
        this.productStorage = this.thereIsProductStorage(jdbcTemplate);

        jdbcTemplate.execute("DROP TABLE products_catalog__products IF EXISTS");

        jdbcTemplate.execute("CREATE TABLE `products_catalog__products` (" +
                "id VARCHAR(255) PRIMARY KEY NOT NULL," +
                "description TEXT," +
                "picture VARCHAR(255)," +
                "price DECIMAL(79.99)" +
                ");");
    }

    @Test
    public void itAllowCreateProduct() {
        Product p1 = new Product(UUID.randomUUID());
        p1.setDescription("test1");
        p1.setPrice(BigDecimal.valueOf(25.88));
        Product p2 = new Product(UUID.randomUUID());
        p2.setDescription("test2");
        p2.setPrice(BigDecimal.valueOf(25.99));

        this.productStorage.save(p1);
        this.productStorage.save(p2);
        int result = this.productStorage.getAllPublished().size();

        assertThat(result).isEqualTo(2);
    }

    @Test
    public void itAllowUpdateProduct() {
        final UUID id = UUID.randomUUID();
        Product p1 = new Product(id);
        productStorage.save(p1);
        p1.setDescription("test");

        productStorage.update(p1);

        Product product = productStorage.getById(id.toString()).orElseThrow();

        assertThat(product.getDescription()).isEqualTo("test");

    }

    @Test
    public void itAllowDeleteProduct() {
        Product product = new Product(UUID.randomUUID());
        product.setDescription("asd");
        product.setPrice(BigDecimal.TEN);

        productStorage.save(product);
        productStorage.delete(product.getId());

        int result = productStorage.getAllPublished().size();
        assertThat(result).isEqualTo(0);
    }

    @Test
    public void itLoadsProduct() {
        Product product = new Product(UUID.randomUUID());
        product.setDescription("asd");
        product.setPrice(BigDecimal.TEN);
        productStorage.save(product);

        Product p = productStorage.getById(product.getId()).orElseThrow();

        assertThat(product.getId()).isEqualTo(p.getId());
    }

    private ProductStorage thereIsProductStorage(JdbcTemplate connection) {
        return new JDBCProductStorage(connection);
    }
}
