package zpi.flattery.controllers.model;

import zpi.flattery.models.Offer;

public class FavouriteRequest {

    private Offer offer;

    public FavouriteRequest(){}

    public FavouriteRequest(Offer offer) {
        this.offer = offer;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }
}
