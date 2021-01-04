package pl.mswierczynski.pp5.productcatalog.exceptions;

public class ProductNotFoundException extends IllegalStateException{
    public ProductNotFoundException(String format) {
        super(format);
    }
}
