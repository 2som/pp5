package pl.mswierczynski.pp5.productcatalog;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class ProductCatalogTest {
    @Test
    public void itAllowsCreateProduct() {
        ProductCatalogFacade api = thereIsProductCatalog();

        String productId = api.createProduct();

        Assert.assertTrue(api.isExists(productId));
    }

    @Test
    public void itAllowsLoadProduct() {
        ProductCatalogFacade api = thereIsProductCatalog();

        String productId = api.createProduct();
        Product lodaded = api.getById(productId);

        Assert.assertEquals(productId, lodaded.getId());
    }

    @Test
    public void itAllowToSetProductDetails() {
        ProductCatalogFacade api = thereIsProductCatalog();
        String productId = api.createProduct();

        String myFancyProduct = "My Fancy Product";
        String picture = "http:/my.image.jpeg";
        api.updateProductDetails(productId, myFancyProduct, picture);
        Product lodaded = api.getById(productId);

        Assert.assertEquals(myFancyProduct, lodaded.getDescription());
        Assert.assertEquals(picture, lodaded.getPicture());
    }

    @Test
    public void itAllowToApplyPrice() {
        ProductCatalogFacade api = thereIsProductCatalog();
        String productId = api.createProduct();

        api.applyPrice(productId, BigDecimal.TEN);

        Assert.assertEquals(api.getById(productId).getPrice(), BigDecimal.TEN);
    }

    @Test
    public void itAllowToListAllPublishedProducts() {
        ProductCatalogFacade api = thereIsProductCatalog();
        String productId = api.createProduct();
        String draftProductId = api.createProduct();

        api.applyPrice(productId, BigDecimal.TEN);
        api.updateProductDetails(productId, "test", "test.jpeg");

        List<Product> products = api.allPublishedProducts();

        Assert.assertEquals(1, products.size());
    }

    private static ProductCatalogFacade thereIsProductCatalog() {
        return new ProductCatalogFacade();
    }
}
