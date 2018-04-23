package zpi.flattery.models;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "Reported_Offers")
public class ReportedOffer {

    @Id
    @GeneratedValue()
    private int reporedOfferId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_IdU")
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "offer_IdO")
    private Offer offer;

    @NotNull
    private Date reportDate;

    @NotNull
    @Size(min = 10, max = 255)
    private String reportArgument;

    public ReportedOffer(User user, Offer offer, String reportArgument) {
        this.user = user;
        this.offer = offer;
        this.reportDate = new Date();
        this.reportArgument = reportArgument;
    }

    public ReportedOffer(User user, Offer offer, Date reportDate, String reportArgument) {
        this.user = user;
        this.offer = offer;
        this.reportDate = reportDate;
        this.reportArgument = reportArgument;
    }

    public ReportedOffer() {
    }

    public int getReporedOfferId() {
        return reporedOfferId;
    }

    public void setReporedOfferId(int reporedOfferId) {
        this.reporedOfferId = reporedOfferId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportArgument() {
        return reportArgument;
    }

    public void setReportArgument(String reportArgument) {
        this.reportArgument = reportArgument;
    }

    
}
