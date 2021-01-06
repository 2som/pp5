package pl.mswierczynski.pp5.productcatalog;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import pl.mswierczynski.pp5.productcatalog.models.Product;

import java.math.BigDecimal;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductCatalogHTTPTEST {
    @LocalServerPort
    int localServerPort;

    @Autowired
    ProductCatalogFacade productCatalogFacade;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void itListAllAvailableProducts() {
        //Arrange
        Product p1 = new Product(UUID.randomUUID());
        p1.setDescription("test");
        p1.setPrice(BigDecimal.TEN);

        //Act
        var url = String.format("http://localhost:%s/api/products/", localServerPort);
        ResponseEntity<Product[]> response = restTemplate.getForEntity(url, Product[].class);

        //Assert

        assertThat(response.getStatusCode())
            .isEqualTo(HttpStatus.OK);

        assertThat(response.getBody())
            .hasSize(1)
            .extracting(Product::getDescription)
            .contains("test");
    }
}
