package pl.mswierczynski.pp5.sales.Product;

import pl.mswierczynski.pp5.sales.Product.ProductDetail;

public interface ProductDetailsProvider {
    ProductDetail getByProductId(String id);
}
