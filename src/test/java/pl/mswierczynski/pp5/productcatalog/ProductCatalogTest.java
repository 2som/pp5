package pl.mswierczynski.pp5.productcatalog;

import org.junit.Assert;
import org.junit.Test;
import pl.mswierczynski.pp5.productcatalog.models.Product;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

public class ProductCatalogTest {
    @Test
    public void itAllowsCreateProduct() {
        ProductCatalogFacade api = thereIsProductCatalog();

        Product product = api.createProduct("test1", "test.jpeg", BigDecimal.valueOf(11));

        Assert.assertTrue(api.isExists(product.getId()));
    }

    @Test
    public void itAllowsLoadProduct() {
        ProductCatalogFacade api = thereIsProductCatalog();

        Product product = api.createProduct("test1", "test.jpeg", BigDecimal.valueOf(12));
        Product lodaded = api.getById(product.getId());

        Assert.assertEquals(product.getId(), lodaded.getId());
    }

    @Test
    public void itAllowToSetProductDetails() {
        ProductCatalogFacade api = thereIsProductCatalog();
        Product product = api.createProduct("test1", "test.jpeg", BigDecimal.valueOf(12));

        String myFancyProduct = "My Fancy Product";
        String picture = "http:/my.image.jpeg";
        BigDecimal price = BigDecimal.valueOf(25.67);
        api.updateProductDetails(product.getId(), myFancyProduct, picture, price);
        Product lodaded = api.getById(product.getId());

        Assert.assertEquals(myFancyProduct, lodaded.getDescription());
        Assert.assertEquals(picture, lodaded.getPicture());
        Assert.assertEquals(price, lodaded.getPrice());
    }

    @Test
    public void itDentyActionsOnNotExistingProduct() {
        ProductCatalogFacade api = thereIsProductCatalog();

        assertThatThrownBy(() -> api.getById("notExists"))
                .hasMessage("There is no product with id notExists");
        assertThatThrownBy(() -> api.updateProductDetails("notExists", "test", "test", BigDecimal.valueOf(11.11)))
                .hasMessage("There is no product with id notExists");
    }

    private static ProductCatalogFacade thereIsProductCatalog() {
        return new ProductCatalogConfiguration().fixturesAwareProductCatalogFacade();
    }
}
