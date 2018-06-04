package zpi.flattery.service;

import org.springframework.stereotype.Service;
import zpi.flattery.models.Favourite;
import zpi.flattery.models.Offer;
import zpi.flattery.models.User;
import zpi.flattery.repository.FavouriteDao;
import zpi.flattery.repository.OfferDao;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class OfferService {

    @Resource
    OfferDao offerDao;

    @Resource
    FavouriteDao favouriteDao;

    public void addFavouriteOfferToUser(Offer offer, User user) throws IllegalArgumentException {
        Optional<Offer> offerFromDb = offerDao.findByTitleAndCityAndPublishingDate(
                offer.getTitle(),
                offer.getCity(),
                offer.getPublishingDate());
        if (offerFromDb.isPresent()) {
            offer = offerFromDb.get();
            if (favouriteDao.existsById(new Favourite.FavouriteId(user.getId(), offer.getId())))
                throw new IllegalArgumentException("Offer is already added as favourite");
        } else {
            offer = offerDao.save(offer);
            favouriteDao.save(new Favourite(user, offer));
        }
    }

    public List<Favourite> findAllFavourites() {
        return favouriteDao.findAll();
    }
}
