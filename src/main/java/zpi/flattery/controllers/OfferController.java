package zpi.flattery.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import zpi.flattery.controllers.model.SearchRequest;
import zpi.flattery.models.Offer;
import zpi.flattery.webscrapper.OfferScrapper;
import zpi.flattery.webscrapper.strategy.OlxStrategy;
import zpi.flattery.webscrapper.strategy.ScrapStrategy;

import java.util.List;
import java.util.Observer;

@RestController
public class OfferController {


    @RequestMapping(value = "/offers", produces = "application/json", method = RequestMethod.POST)
    public DeferredResult<List<Offer>> searchForOffers(@RequestBody SearchRequest searchRequest){

        DeferredResult<List<Offer>> result = new DeferredResult<>();
        Observer observer = (o, arg) -> result.setResult(((ScrapStrategy) o).getScrappedOffers());

        OfferScrapper scrapStrategy = OfferScrapper.builder(new OlxStrategy())
                .place(searchRequest.getCity())
                .query(searchRequest.getQuery())
                .minPrice(searchRequest.getMinPrice())
                .maxPrice(searchRequest.getMaxPrice())
                .roomType(searchRequest.getRoomType())
                .offerType(searchRequest.getOfferType())
                .addObserver(observer)
                .build();
        scrapStrategy.scrapOffers();
        return result;
    }

}
