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

import javax.annotation.Resource;
import java.security.Principal;
import java.util.List;

@RestController
public class OfferController {

    @Resource
    OfferService offerService;

    @RequestMapping(value = "/offers", produces = "application/json", method = RequestMethod.POST)
    public DeferredResult<List<Offer>> searchForOffers(@RequestBody SearchRequest searchRequest) {
        DeferredResult<List<Offer>> result = new DeferredResult<>();
        offerService.scrapOffers(searchRequest, result);
        return result;
    }
}
