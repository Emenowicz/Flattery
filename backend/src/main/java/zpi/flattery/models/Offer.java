package zpi.flattery.models;


import zpi.flattery.models.enums.BuildingType;
import zpi.flattery.models.enums.OfferType;
import zpi.flattery.models.enums.RoomType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "Offers")
public class Offer {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Users_id")
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
    private String publishingDate;

    private char[] mainPhoto;

    @ElementCollection
    private List<byte[]> photos;

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

    private String coordinates;

    private String photoUrl;

    @ManyToMany(mappedBy = "favoriteOffers")
    private List<User> favoritedByUsers;

    @OneToMany(mappedBy = "offer")
    private List<ReportedOffer> reports;

    public Offer(User user, String title, String city, double price) {
        this.user = user;
        this.title = title;
        this.city = city;
        this.price = price;
    }

    public Offer(User user, String title, String city, double price, String publishingDate) {
        this.user = user;
        this.title = title;
        this.city = city;
        this.price = price;
        this.publishingDate = publishingDate;
    }

    public Offer(@NotNull String title, @NotNull String city, String urlToOffer, OfferType offerType, RoomType roomType, @NotNull double price, @NotNull String publishingDate, String photoUrl) {
        this.photoUrl = photoUrl;
        this.title = title;
        this.city = city;
        this.urlToOffer = urlToOffer;
        this.offerType = offerType;
        this.roomType = roomType;
        this.price = price;
        this.publishingDate = publishingDate;
    }

    public Offer() {
    }


    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate(String publishingDate) {
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

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", user=" + user +
                ", title='" + title + '\'' +
                ", region='" + region + '\'' +
                ", city='" + city + '\'' +
                ", urlToOffer='" + urlToOffer + '\'' +
                ", offerType=" + offerType +
                ", roomType=" + roomType +
                ", price=" + price +
                ", publishingDate='" + publishingDate + '\'' +
                ", mainPhoto=" + Arrays.toString(mainPhoto) +
                ", photos=" + photos +
                ", description='" + description + '\'' +
                ", numberOfRooms=" + numberOfRooms +
                ", floor=" + floor +
                ", buildingType=" + buildingType +
                ", squareMetrage=" + squareMetrage +
                ", femaleOnly=" + femaleOnly +
                ", maleOnly=" + maleOnly +
                ", isSmokingAllowed=" + isSmokingAllowed +
                ", arePetsAllowed=" + arePetsAllowed +
                ", hasBalcony=" + hasBalcony +
                ", isFurnished=" + isFurnished +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", coordinates='" + coordinates + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", favoritedByUsers=" + favoritedByUsers +
                ", reports=" + reports +
                '}';
    }
}
