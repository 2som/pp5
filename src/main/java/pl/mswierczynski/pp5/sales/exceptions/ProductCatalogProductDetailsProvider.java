package pl.mswierczynski.pp5.sales.exceptions;

import pl.mswierczynski.pp5.productcatalog.ProductCatalogFacade;
import pl.mswierczynski.pp5.sales.Product.ProductDetail;
import pl.mswierczynski.pp5.sales.Product.ProductDetailsProvider;

public class ProductCatalogProductDetailsProvider implements ProductDetailsProvider {

    private final ProductCatalogFacade productCatalogFacade;

    public ProductCatalogProductDetailsProvider(ProductCatalogFacade productCatalogFacade) {
        this.productCatalogFacade = productCatalogFacade;
    }

    @Override
    public ProductDetail getByProductId(String id) {
        var product = productCatalogFacade.getById(id);

        return new ProductDetail(
            product.getId(),
            product.getDescription(),
            product.getPrice()
        );
    }
}
