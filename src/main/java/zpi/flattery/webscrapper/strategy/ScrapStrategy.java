package zpi.flattery.webscrapper.strategy;

import zpi.flattery.model.Offer;
import zpi.flattery.model.OfferType;
import zpi.flattery.model.RoomType;

import java.util.ArrayList;
import java.util.List;

public interface ScrapStrategy {
    void scrap();

    void setScrapParameters(RoomType roomType, OfferType offerType, String query, Integer minPrice, Integer maxPrice, String place, Integer radius, Integer daysOld);

    List<Offer> getScrappedOffers();
}