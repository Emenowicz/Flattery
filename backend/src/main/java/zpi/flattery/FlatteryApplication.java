package zpi.flattery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zpi.flattery.models.enums.OfferType;
import zpi.flattery.models.enums.RoomType;
import zpi.flattery.webscrapper.OfferScrapper;
import zpi.flattery.webscrapper.strategy.GumtreeStrategy;
import zpi.flattery.webscrapper.strategy.OlxStrategy;

import java.util.Observer;

@SpringBootApplication
public class FlatteryApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlatteryApplication.class, args);
//		OfferScrapper scrapper = OfferScrapper.Builder(new GumtreeStrategy())
//				.place("Wrocław")
//				.query("balkon")
//				.minPrice(300)
//				.maxPrice(1200)
//				.roomType(RoomType.SinglePerson)
//				.offerType(OfferType.Flat)
//				.maxDaysOld(2)
//				.radius(10)
//				.build();
//
//		scrapper.scrapOffers();
	}
}
