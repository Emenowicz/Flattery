package zpi.flattery.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue
    private int userId;

    @NotNull
    @Size(min = 3, max = 30)
    private String lastName;

    @NotNull
    @Size(min = 3, max = 20)
    private String firstName;

    @NotNull
    @Size(min = 5, max = 30)
    @Email
    @Column(unique = true)
    private String emailAddress;

    @NotNull
    @Size(min = 4, max = 20)
    @Column(unique = true)
    private String userName;

    @NotNull
    @Size(min = 6, max = 20)
    @JsonIgnore
    @Column(name = "Password")
    private String password;

    @NotNull
    @Column(name = "Is_Admin")
    private boolean isAdmin;

    @ManyToMany
    @JoinTable(name = "users_offers",
            joinColumns = @JoinColumn(name = "users_idu"),
            inverseJoinColumns = @JoinColumn(name = "offers_ido")
    )
    @JsonIgnoreProperties({"user", "favouritedByUsers", "reports"})
    private List<Offer> favoriteOffers;

    @Column(name = "Longitude")
    private double longitude;

    @Column(name = "Latitude")
    private double latitude;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties({"user"})
    private List<Offer> offers;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties({"user"})
    private List<ReportedOffer> reports;

    public User(String lastName, String firstName, String emailAddress, String userName, String password) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.emailAddress = emailAddress;
        this.userName = userName;
        this.password = password;
        this.isAdmin = false;
    }

    public User(String lastName, String firstName, String emailAddress, String userName, String password, boolean isAdmin) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.emailAddress = emailAddress;
        this.userName = userName;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public User() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public List<Offer> getFavoriteOffers() {
        return favoriteOffers;
    }

    public void setFavoriteOffers(List<Offer> favoriteOffers) {
        this.favoriteOffers = favoriteOffers;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public List<ReportedOffer> getReports() {
        return reports;
    }

    public void setReports(List<ReportedOffer> reports) {
        this.reports = reports;
    }
}
