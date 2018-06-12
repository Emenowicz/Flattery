package zpi.flattery.webscrapper;

import io.reactivex.Single;
import zpi.flattery.models.Offer;
import zpi.flattery.models.enums.OfferType;
import zpi.flattery.models.enums.RoomType;
import zpi.flattery.webscrapper.strategy.ScrapStrategy;

import java.io.IOException;
import java.util.List;


public class OfferScrapper {

    private ScrapStrategy scrapStrategy;

    public OfferScrapper(ScrapStrategy scrapStrategy) {
        this.scrapStrategy = scrapStrategy;
    }

    public Single<List<Offer>> scrapOffers() {
        return scrapStrategy.scrap();
    }

    public static OfferScrapperBuilder Builder(ScrapStrategy scrapStrategy) {
        return new OfferScrapperBuilder(scrapStrategy);
    }

    public static class OfferScrapperBuilder {
        private ScrapStrategy scrapStrategy;
        private RoomType roomType;
        private OfferType offerType;
        private String query = "";
        private String place = "";
        private double maxPrice = 0;
        private double minPrice = 0;
        private Integer radius;
        private Integer daysOld;

        private OfferScrapperBuilder(ScrapStrategy scrapStrategy) {
            this.scrapStrategy = scrapStrategy;
        }

        public OfferScrapper build() {
            if (place == null)
                radius = null;
            if (offerType != OfferType.Room) {
                roomType = null;
            }
            scrapStrategy.setScrapParameters(roomType, offerType, query, minPrice, maxPrice, place, radius, daysOld);
            return new OfferScrapper(scrapStrategy);
        }

        public OfferScrapperBuilder roomType(RoomType roomType) {
            this.roomType = roomType;
            return this;
        }

        public OfferScrapperBuilder offerType(OfferType offerType) {
            this.offerType = offerType;
            return this;
        }

        public OfferScrapperBuilder place(String place) {
            this.place = place;
            return this;
        }

        public OfferScrapperBuilder maxPrice(double maxPrice) {
            this.maxPrice = maxPrice;
            return this;
        }

        public OfferScrapperBuilder minPrice(double minPrice) {
            this.minPrice = minPrice;
            return this;
        }

        public OfferScrapperBuilder radius(int radius) {
            this.radius = radius;
            return this;
        }

        public OfferScrapperBuilder query(String query) {
            this.query = query;
            return this;
        }

        public OfferScrapperBuilder maxDaysOld(int daysOld) {
            this.daysOld = daysOld;
            return this;
        }
    }
}
