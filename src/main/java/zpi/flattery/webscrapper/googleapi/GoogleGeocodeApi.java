package zpi.flattery.webscrapper.googleapi;

import retrofit2.http.GET;
import retrofit2.http.Query;

import io.reactivex.Observable;

public interface GoogleGeocodeApi {
    String BASE_URL = "https://maps.googleapis.com";
    String API_KEY = "AIzaSyAJ4sJz028nuy-yw7KL1kr6Ay7VYIX86J0";

    @GET("/maps/api/geocode/json")
    Observable<GeocodeResponse> getGeocode(@Query("address") String address, @Query("key") String key);
}
