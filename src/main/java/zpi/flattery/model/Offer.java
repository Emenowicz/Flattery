package zpi.flattery.model;

import java.time.LocalDateTime;

public class Offer {
    private String title;

    private String link;

    private OfferType offerType;

    private RoomType roomType;

    private double price;

    private LocalDateTime publishedDate;

    private String photo;

    private String place;

    private String coordinates;

    public Offer(String title, String link, String place, OfferType offerType, RoomType roomType, double price, LocalDateTime publishedDate, String photo) {
        this.title = title;
        this.link = link;
        this.offerType = offerType;
        this.roomType = roomType;
        this.price = price;
        this.publishedDate = publishedDate;
        this.photo = photo;
        this.place = place;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public OfferType getOfferType() {
        return offerType;
    }

    public void setOfferType(OfferType offerType) {
        this.offerType = offerType;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object obj) {
        Offer offerToCheck = (Offer) obj;
        return offerToCheck.title.equals(title) && offerToCheck.publishedDate.equals(publishedDate);
    }

    @Override
    public String toString() {
        return "Offer{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", offerType=" + offerType +
                ", roomType=" + roomType +
                ", price=" + price +
                ", publishedDate=" + publishedDate +
                ", photo='" + photo + '\'' +
                ", place='" + place + '\'' +
                ", coordinates='" + coordinates + '\'' +
                '}';
    }
}
