package zpi.flattery.webscrapper.strategy;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import zpi.flattery.models.Offer;
import zpi.flattery.models.enums.OfferType;
import zpi.flattery.models.enums.RoomType;
import zpi.flattery.webscrapper.googleapi.GeocodeResponse;
import zpi.flattery.webscrapper.googleapi.GoogleGeocodeApi;

import java.text.Normalizer;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class ScrapStrategy {

    abstract public Single<List<Offer>> scrap();
    abstract public void setScrapParameters(RoomType roomType, OfferType offerType, String query, double minPrice, double maxPrice, String place, Integer radius, Integer daysOld);

    protected String getNormalizedString(String location){
        location = Normalizer.normalize(location, Normalizer.Form.NFD);
        location = location.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        //'ł' is still not transformed to 'l'
        location = location.replaceAll("ł", "l");
        location = location.replaceAll("Ł", "L");
        location = location.replaceAll("\\s+", "-");
        return location;
    }

    protected boolean isOfferFreshEnough(Offer offer, int daysOld) {
        if (daysOld == 0)
            return true;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime parsedDate = LocalDateTime.parse(offer.getPublishingDate(), formatter);
        LocalDateTime currentDate = LocalDateTime.now();
        long daysBetween = Duration.between(parsedDate, currentDate).toDays();
        return daysBetween <= daysOld;
    }

    protected Single<List<Offer>> addLatLang(ArrayList<Offer> scrappedOffers) {
        //Creating Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GoogleGeocodeApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        GoogleGeocodeApi service = retrofit.create(GoogleGeocodeApi.class);

        //Creating Observable objects from offers and addidng them to list
        List<Observable<Offer>> observableList = new LinkedList<>();
        for (Offer offer : scrappedOffers) {
            observableList.add(service.getGeocode(offer.getCity(), GoogleGeocodeApi.API_KEY)
                    .map(response -> combineResponseWithOffer(offer, response)));
        }
        //Observables from list are observed on the same thread so the whole process will wait for all of them to being processed, before completion
        return Observable.fromIterable(observableList)
                .flatMap(task -> {
                    task.observeOn(Schedulers.computation());
                    return task;
                }).toList();
    }


    private Offer combineResponseWithOffer(Offer offer, GeocodeResponse geocodeResponse) {
        if (!geocodeResponse.status.equals("OVER_QUERY_LIMIT")) {
            offer.setCoordinates("lat=" + geocodeResponse.results[0].geometry.location.lat + ", lng=" + geocodeResponse.results[0].geometry.location.lng);
        }
        return offer;
    }
}