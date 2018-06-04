package zpi.flattery.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="Favourites")
public class Favourite {

    @EmbeddedId
    private FavouriteId favouriteId;

    @MapsId(value = "userId")
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @MapsId(value = "offerId")
    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;

    public Favourite(User user, Offer offer) {
        this.user = user;
        this.offer = offer;
        this.favouriteId = new FavouriteId(user.getId(), offer.getId());
    }

    public Favourite(){}

    public FavouriteId getFavouriteId() {
        return favouriteId;
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

    @Embeddable
    public static class FavouriteId implements Serializable {

        @Column(name = "user_id")
        private Long userId;

        @Column(name = "offer_id")
        private Long offerId;

        public FavouriteId() {}

        public FavouriteId(@NotNull long userId, @NotNull long offerId) {
            this.userId = userId;
            this.offerId = offerId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FavouriteId that = (FavouriteId) o;
            return userId == that.userId &&
                    offerId == that.offerId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, offerId);
        }
    }
}
