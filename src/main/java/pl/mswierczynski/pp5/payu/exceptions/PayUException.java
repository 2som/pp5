package pl.mswierczynski.pp5.payu.exceptions;


public class PayUException extends Exception {
    public PayUException(Exception e) {
        super(e);
    }
}
