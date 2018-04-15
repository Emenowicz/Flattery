package zpi.flattery.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zpi.flattery.controllers.model.SearchRequest;
import zpi.flattery.models.Offer;

import java.util.List;

@Controller
public class OfferController {

    @RequestMapping(value = "/offers", produces = "application/json", method = RequestMethod.GET)
    public List<Offer> searchForOffers(@RequestBody SearchRequest searchRequest){

    }
}
