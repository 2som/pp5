package pl.mswierczynski.pp5.sales;

import pl.mswierczynski.pp5.sales.Offer.Offer;

import java.util.UUID;

public class Reservation {
    public static Reservation of(Offer currentOffer, ClientData clientData) {
        return new Reservation();
    }

    public String getId() {
        return UUID.randomUUID().toString();
    }
}
