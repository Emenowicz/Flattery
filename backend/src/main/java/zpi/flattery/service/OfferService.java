package zpi.flattery.service;

import io.reactivex.schedulers.Schedulers;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;
import zpi.flattery.controllers.model.SearchRequest;
import zpi.flattery.models.Favourite;
import zpi.flattery.models.Offer;
import zpi.flattery.models.User;
import zpi.flattery.repository.FavouriteDao;
import zpi.flattery.repository.OfferDao;
import zpi.flattery.webscrapper.OfferScrapper;
import zpi.flattery.webscrapper.strategy.OlxStrategy;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class OfferService {

    @Resource
    OfferDao offerDao;

    @Resource
    FavouriteDao favouriteDao;

    public void scrapOffers(SearchRequest searchRequest, DeferredResult<List<Offer>> deferredResult){
        OfferScrapper scrapper = OfferScrapper.Builder(new OlxStrategy())
                .place(searchRequest.getCity())
                .query(searchRequest.getQuery())
                .minPrice(searchRequest.getMinPrice())
                .maxPrice(searchRequest.getMaxPrice())
                .roomType(searchRequest.getRoomType())
                .offerType(searchRequest.getOfferType())
                .build();

        scrapper.scrapOffers()
        .subscribeOn(Schedulers.io())
        .subscribe(response -> handleScrapperResponse(response, deferredResult),
                error -> handleScrapperError(error, deferredResult));
    }

    private void handleScrapperResponse(List<Offer> offerList, DeferredResult<List<Offer>> result) {
        result.setResult(offerList);
    }

    private void handleScrapperError(Throwable e, DeferredResult<List<Offer>> result) {
        e.printStackTrace();
        result.setErrorResult(e);
    }

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
