package zpi.flattery.webscrapper.strategy;

import io.reactivex.Single;
import zpi.flattery.models.Offer;
import zpi.flattery.models.enums.OfferType;
import zpi.flattery.models.enums.RoomType;

import java.io.IOException;
import java.util.List;

public interface ScrapStrategy {
    Single<List<Offer>> scrap();
    void setScrapParameters(RoomType roomType, OfferType offerType, String query, double minPrice, double maxPrice, String place, Integer radius, Integer daysOld);

}