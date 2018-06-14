package zpi.flattery.controllers.model;


import zpi.flattery.models.enums.OfferType;
import zpi.flattery.models.enums.RoomType;

public class SearchRequest {
    private String query;
    private String city;
    private double minPrice;
    private double maxPrice;
    private RoomType roomType;
    private OfferType offerType;
    private int offerDaysOld;
    private int radiusFromLocation;


    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public OfferType getOfferType() {
        return offerType;
    }

    public void setOfferType(OfferType offerType) {
        this.offerType = offerType;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getOfferDaysOld() {
        return offerDaysOld;
    }

    public void setOfferDaysOld(int offerDaysOld) {
        this.offerDaysOld = offerDaysOld;
    }

    public int getRadiusFromLocation() {
        return radiusFromLocation;
    }

    public void setRadiusFromLocation(int radiusFromLocation) {
        this.radiusFromLocation = radiusFromLocation;
    }
}
