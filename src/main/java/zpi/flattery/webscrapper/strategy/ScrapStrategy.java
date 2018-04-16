package zpi.flattery.webscrapper.strategy;

import zpi.flattery.models.Offer;
import zpi.flattery.models.enums.OfferType;
import zpi.flattery.models.enums.RoomType;

import java.util.List;

public interface ScrapStrategy {
    void scrap();

    void setScrapParameters(RoomType roomType, OfferType offerType, String query, double minPrice, double maxPrice, String place, Integer radius, Integer daysOld);

    List<Offer> getScrappedOffers();
}