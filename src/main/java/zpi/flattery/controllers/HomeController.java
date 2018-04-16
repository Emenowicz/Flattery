package zpi.flattery.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @GetMapping(value = "/")
    public String home(){
        return "home";
    }

    @GetMapping(value = "/recyclerview/OfferTombstone")
    public String offerTombstone() {
        return "recyclerview/OfferTombstone";
    }

    @GetMapping(value = "/recyclerview/OfferItem")
    public String offerItem() {
        return "recyclerview/OfferItem";
    }

    @GetMapping(value = "/recyclerview/offer-fetch")
    public String offerFetch() {
        return "recyclerview/offer-fetch";
    }
}
