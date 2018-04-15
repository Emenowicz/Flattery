package zpi.flattery.webscrapper;


import zpi.flattery.model.OfferType;
import zpi.flattery.model.RoomType;
import zpi.flattery.webscrapper.strategy.ScrapStrategy;

import java.util.Observable;
import java.util.Observer;

public class OfferScrapper {

    private ScrapStrategy scrapStrategy;

    public OfferScrapper(ScrapStrategy scrapStrategy) {
        this.scrapStrategy = scrapStrategy;
    }

    public void scrapOffers() {
        scrapStrategy.scrap();
    }

    public static OfferScrapperBuilder builder(ScrapStrategy scrapStrategy) {
        return new OfferScrapperBuilder(scrapStrategy);
    }

    public static class OfferScrapperBuilder {
        private ScrapStrategy scrapStrategy;
        private RoomType roomType;
        private OfferType offerType;
        private String query;
        private String place;
        private int maxPrice;
        private int minPrice;
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

        public OfferScrapperBuilder addObserver(Observer observer) {
            ((Observable) scrapStrategy).addObserver(observer);
            return this;
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

        public OfferScrapperBuilder maxPrice(int maxPrice) {
            this.maxPrice = maxPrice;
            return this;
        }

        public OfferScrapperBuilder minPrice(int minPrice) {
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

        public OfferScrapperBuilder maxDaysOld(int daysOld){
            this.daysOld = daysOld;
            return this;
        }
    }
}
