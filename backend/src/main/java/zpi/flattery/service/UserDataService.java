package zpi.flattery.service;

import io.reactivex.schedulers.Schedulers;
import javassist.NotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import zpi.flattery.models.User;
import zpi.flattery.repository.UserDao;
import zpi.flattery.webscrapper.googleapi.GeocodeResponse;
import zpi.flattery.webscrapper.googleapi.GoogleGeocodeApi;

import javax.annotation.Resource;
import java.security.Principal;

import java.util.Optional;

@Service
public class UserDataService {

    @Resource
    UserDao userDao;

    @Resource
    PasswordEncoder passwordEncoder;

    public User getDataForLoggedUser(Principal principal) throws NotFoundException {

        Optional<User> user = userDao.findByUserName(principal.getName());
        if (!user.isPresent()) {
            throw new NotFoundException("User is not found");
        }

        return user.get();
    }

    public Optional<User> findUserById(long id) {
        return userDao.findUserById(id);
    }

    public void saveOrUpdateUser(User user, String password) {
        if(password != null){
            user.setPassword(passwordEncoder.encode(password));
        }
        userDao.save(user);
    }

    public void saveUserLocation(Double longitude, Double latitude, Principal principal, DeferredResult<String> result) throws NotFoundException {
        Optional<User> user = userDao.findByUserName(principal.getName());
        if (user.isPresent()) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(GoogleGeocodeApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            GoogleGeocodeApi service = retrofit.create(GoogleGeocodeApi.class);

            service.getCity(latitude + "," + longitude, GoogleGeocodeApi.API_KEY)
                    .subscribeOn(Schedulers.io())
                    .subscribe(response -> handleLocationRespond(response, principal, result));

            user.get().setLongitude(longitude);
            user.get().setLatitude(latitude);
            userDao.save(user.get());
        } else {
            throw new NotFoundException("User is not found");
        }
    }

    private void handleLocationRespond(GeocodeResponse geocodeResponse, Principal principal, DeferredResult<String> result) {
        if (geocodeResponse.status.equals("OVER_QUERY_LIMIT")) {
            Optional<User> user = userDao.findByUserName(principal.getName());
            //formatted_address looks like this "Oławska 4a, 50-123 Wrocław, Poland"
            //there are two possible scenarios for city name: with zipcode or without it
            String[] location = geocodeResponse.results[0].formatted_address.split(",");
            String cityName = location[location.length - 2];

            if (cityName.matches(".*\\d+.*")) {
                int lastDigitIndex = cityName.length() - 1;
                while (Character.getNumericValue(cityName.charAt(lastDigitIndex)) < Character.getNumericValue('0')
                        || Character.getNumericValue(cityName.charAt(lastDigitIndex)) > Character.getNumericValue('9')) {
                    lastDigitIndex--;
                }
                cityName = cityName.substring(lastDigitIndex + 2);
            }
            user.get().setLocation(cityName);
            userDao.save(user.get());
            result.setResult(cityName);
        } else {
            System.out.println("GoogleApi: OVER_QUERY_LIMIT");
        }
    }
}
