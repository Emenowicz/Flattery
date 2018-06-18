package zpi.flattery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zpi.flattery.models.Offer;

import java.util.Optional;

@Repository
public interface OfferDao extends JpaRepository<Offer, Integer> {

    Optional<Offer> findByTitleAndCityAndPublishingDate(String title, String city, String publishingDate);

    Optional<Offer> findByUrlToOffer(String urlToOffer);
}