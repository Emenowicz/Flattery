package zpi.flattery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zpi.flattery.models.Favourite;
import zpi.flattery.models.Offer;
import zpi.flattery.models.User;

import java.util.List;

@Repository
public interface FavouriteDao extends JpaRepository<Favourite, Favourite.FavouriteId> {

    List<Favourite> getAllByUser(User user);

    void removeByOfferAndUser(Offer offer, User user);
}
