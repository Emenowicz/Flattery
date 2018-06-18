package zpi.flattery.webscrapper.googleapi;

import retrofit2.http.GET;
import retrofit2.http.Query;

import io.reactivex.Observable;

public interface GoogleGeocodeApi {
    String BASE_URL = "https://maps.googleapis.com";
    String API_KEY = "AIzaSyA8SItRXDVTPIST00vdPqCUTLO6kmdgMdI";

    @GET("/maps/api/geocode/json")
    Observable<GeocodeResponse> getGeocode(@Query("address") String address, @Query("key") String key);

    @GET("/maps/api/geocode/json")
    Observable<GeocodeResponse> getCity(@Query("latlng") String latlng, @Query("key") String key);

}
