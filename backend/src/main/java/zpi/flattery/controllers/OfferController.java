package zpi.flattery.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import zpi.flattery.controllers.model.FavouriteRequest;
import zpi.flattery.controllers.model.SearchRequest;
import zpi.flattery.models.Favourite;
import zpi.flattery.models.Offer;
import zpi.flattery.service.OfferService;
import zpi.flattery.service.UserDataService;
import zpi.flattery.webscrapper.OfferScrapper;
import zpi.flattery.webscrapper.strategy.OlxStrategy;
import zpi.flattery.webscrapper.strategy.ScrapStrategy;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.List;
import java.util.Observer;

@RestController
public class OfferController {

    @Resource
    UserDataService userDataService;

    @Resource
    OfferService offerService;

    @RequestMapping(value = "/offers", produces = "application/json", method = RequestMethod.POST)
    public DeferredResult<List<Offer>> searchForOffers(@RequestBody SearchRequest searchRequest){
        DeferredResult<List<Offer>> result = new DeferredResult<>();
        offerService.scrapOffers(searchRequest, result);
        return result;
    }

    @RequestMapping(value= "/addFavourite", produces = "application/json", method = RequestMethod.PUT)
    public ResponseEntity<String> addFavouriteOffer(@RequestBody FavouriteRequest favouriteRequest, Principal principal){
        try{
            offerService.addFavouriteOfferToUser(favouriteRequest.getOffer(), userDataService.getDataForLoggedUser(principal));
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .body(e.getMessage());
        }

        List<Favourite> list = offerService.findAllFavourites();
        for(Favourite fav: list){
            System.out.println(fav);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Offer successfully added as favourite.");
    }
}
