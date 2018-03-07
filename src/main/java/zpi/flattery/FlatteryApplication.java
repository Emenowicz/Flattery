package zpi.flattery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zpi.flattery.model.Offer;
import zpi.flattery.model.OfferType;
import zpi.flattery.model.RoomType;
import zpi.flattery.webscrapper.OfferScrapper;
import zpi.flattery.webscrapper.strategy.OlxStrategy;
import zpi.flattery.webscrapper.strategy.ScrapStrategy;

import java.util.List;
import java.util.Observer;

@SpringBootApplication
public class FlatteryApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlatteryApplication.class, args);

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
