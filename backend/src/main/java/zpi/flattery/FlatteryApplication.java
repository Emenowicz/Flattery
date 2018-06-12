package zpi.flattery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zpi.flattery.models.enums.OfferType;
import zpi.flattery.models.enums.RoomType;
import zpi.flattery.webscrapper.OfferScrapper;
import zpi.flattery.webscrapper.strategy.OlxStrategy;

import java.util.Observer;

@SpringBootApplication
public class FlatteryApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlatteryApplication.class, args);
	}
}
