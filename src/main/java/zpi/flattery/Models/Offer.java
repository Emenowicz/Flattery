package zpi.flattery.Models;


import zpi.flattery.Models.Enums.BuildingType;
import zpi.flattery.Models.Enums.OfferType;
import zpi.flattery.Models.Enums.RoomType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Offers")
public class Offer {

    @Id
    @GeneratedValue
    private int offerId;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "user_IdU")
    private User user;

    @NotNull
    private String title;

    private String region;

    @NotNull
    private String city;

    private String urlToOffer;

    private OfferType offerType;

    private RoomType roomType;

    @NotNull
    @Column(name = "Price")
    private double price;

    @NotNull
    private Date publishingDate;

    private char[] mainPhoto;

    @ElementCollection
    private List<byte[]> photos;

    @NotNull
    private String description;

    private int numberOfRooms;

    private int floor;

    private BuildingType buildingType;

    private double squareMetrage;

    private boolean femaleOnly;

    private boolean maleOnly;

    private boolean isSmokingAllowed;

    private boolean arePetsAllowed;

    private boolean hasBalcony;

    private boolean isFurnished;

    private String phoneNumber;

    @ManyToMany(mappedBy = "favoriteOffers")
    private List<User> favoritedByUsers;

    @OneToMany(mappedBy = "offer")
    private List<ReportedOffer> reports;

    public Offer(User user, String title, String city, double price) {
        this.user = user;
        this.title = title;
        this.city = city;
        this.price = price;
        this.publishingDate = new Date();
    }

    public Offer(User user, String title, String city, double price, Date publishingDate) {
        this.user = user;
        this.title = title;
        this.city = city;
        this.price = price;
        this.publishingDate = publishingDate;
    }

    public Offer() {
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUrlToOffer() {
        return urlToOffer;
    }

    public void setUrlToOffer(String urlToOffer) {
        this.urlToOffer = urlToOffer;
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

    public Date getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate(Date publishingDate) {
        this.publishingDate = publishingDate;
    }

    public char[] getMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(char[] mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

    public List<byte[]> getPhotos() {
        return photos;
    }

    public void setPhotos(List<byte[]> photos) {
        this.photos = photos;
    }

    public List<User> getFavoritedByUsers() {
        return favoritedByUsers;
    }

    public void setFavoritedByUsers(List<User> favoritedByUsers) {
        this.favoritedByUsers = favoritedByUsers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(BuildingType buildingType) {
        this.buildingType = buildingType;
    }

    public double getSquareMetrage() {
        return squareMetrage;
    }

    public void setSquareMetrage(double squareMetrage) {
        this.squareMetrage = squareMetrage;
    }

    public boolean isFemaleOnly() {
        return femaleOnly;
    }

    public void setFemaleOnly(boolean femaleOnly) {
        this.femaleOnly = femaleOnly;
    }

    public boolean isMaleOnly() {
        return maleOnly;
    }

    public void setMaleOnly(boolean maleOnly) {
        this.maleOnly = maleOnly;
    }

    public boolean isSmokingAllowed() {
        return isSmokingAllowed;
    }

    public void setSmokingAllowed(boolean smokingAllowed) {
        isSmokingAllowed = smokingAllowed;
    }

    public boolean isArePetsAllowed() {
        return arePetsAllowed;
    }

    public void setArePetsAllowed(boolean arePetsAllowed) {
        this.arePetsAllowed = arePetsAllowed;
    }

    public boolean isHasBalcony() {
        return hasBalcony;
    }

    public void setHasBalcony(boolean hasBalcony) {
        this.hasBalcony = hasBalcony;
    }

    public boolean isFurnished() {
        return isFurnished;
    }

    public void setFurnished(boolean furnished) {
        isFurnished = furnished;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<ReportedOffer> getReports() {
        return reports;
    }

    public void setReports(List<ReportedOffer> reports) {
        this.reports = reports;
    }
}
