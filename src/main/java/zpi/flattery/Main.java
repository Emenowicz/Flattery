import model.Offer;
import model.OfferType;
import model.RoomType;
import webcrawler.OfferScrapper;
import webcrawler.strategy.OlxStrategy;
import webcrawler.strategy.ScrapStrategy;

import java.util.List;
import java.util.Observer;

public class Main {

    public static void main(String[] args) {
        Observer observer = (o, arg) -> {
            List<Offer> scrappedOffers = ((ScrapStrategy) o).getScrappedOffers();
            for (Offer offer : scrappedOffers)
                System.out.println(offer.toString());
        };

        OfferScrapper offerScrapper = OfferScrapper.builder(new OlxStrategy())
                .minPrice(500)
                .maxPrice(1000)
                .place("Wrocław")
                .query("balkon")
                .radius(10)
                .offerType(OfferType.Room)
                .roomType(RoomType.Two)
                .addObserver(observer)
                .build();
        offerScrapper.scrapOffers();
    }
}