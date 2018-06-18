package zpi.flattery.service;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.async.DeferredResult;
import zpi.flattery.controllers.model.FavouriteRequest;
import zpi.flattery.controllers.model.SearchRequest;
import zpi.flattery.models.Favourite;
import zpi.flattery.models.Offer;
import zpi.flattery.models.User;
import zpi.flattery.repository.FavouriteDao;
import zpi.flattery.repository.OfferDao;
import zpi.flattery.webscrapper.OfferScrapper;
import zpi.flattery.webscrapper.strategy.GumtreeStrategy;
import zpi.flattery.webscrapper.strategy.OlxStrategy;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class OfferService {

    @Resource
    OfferDao offerDao;

    @Resource
    FavouriteDao favouriteDao;

    public void scrapOffers(SearchRequest searchRequest, DeferredResult<List<Offer>> deferredResult){
        OfferScrapper olxScrapper = OfferScrapper.Builder(new OlxStrategy())
                .place(searchRequest.getCity())
                .query(searchRequest.getQuery())
                .minPrice(searchRequest.getMinPrice())
                .maxPrice(searchRequest.getMaxPrice())
                .roomType(searchRequest.getRoomType())
                .offerType(searchRequest.getOfferType())
                .maxDaysOld(searchRequest.getOfferDaysOld())
                .radius(searchRequest.getRadiusFromLocation())
                .build();


        OfferScrapper gumtreeScrapper = OfferScrapper.Builder(new GumtreeStrategy())
                .place(searchRequest.getCity())
                .query(searchRequest.getQuery())
                .minPrice(searchRequest.getMinPrice())
                .maxPrice(searchRequest.getMaxPrice())
                .roomType(searchRequest.getRoomType())
                .offerType(searchRequest.getOfferType())
                .maxDaysOld(searchRequest.getOfferDaysOld())
                .radius(searchRequest.getRadiusFromLocation())
                .build();

        Single.zip(
                olxScrapper.scrapOffers().subscribeOn(Schedulers.io()),
                gumtreeScrapper.scrapOffers().subscribeOn(Schedulers.io()),
                (a, b) -> {
                    a.addAll(b);
                    Collections.sort(a);
                    return a;
                }
        )
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
            else
                favouriteDao.save(new Favourite(user, offer));
        } else {
            offer = offerDao.save(offer);
            favouriteDao.save(new Favourite(user, offer));
        }
    }

    @Transactional
    public void removeByOfferAndUser(Offer offer, User user) {
        favouriteDao.removeByOfferAndUser(offer, user);
    }

    public Optional<Offer> findByUrToOffer(String urlToOffer) {
        return offerDao.findByUrlToOffer(urlToOffer);
    }

    public List<Favourite> findAllFavourites() {
        return favouriteDao.findAll();
    }

    public List<Favourite> getUsersFavouriteOffers(User user){
        return favouriteDao.getAllByUser(user);
    }
}
