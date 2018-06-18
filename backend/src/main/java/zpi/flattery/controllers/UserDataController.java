package zpi.flattery.controllers;

import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import zpi.flattery.controllers.model.FavouriteRequest;
import zpi.flattery.controllers.model.LocationRequest;
import zpi.flattery.controllers.model.UserRequest;
import zpi.flattery.models.Favourite;
import zpi.flattery.models.Offer;
import zpi.flattery.models.User;
import zpi.flattery.service.OfferService;
import zpi.flattery.service.UserDataService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class UserDataController {

    @Resource
    UserDataService userDataService;

    @Resource
    OfferService offerService;

    @RequestMapping(value = "/loggedUserData", method = RequestMethod.GET)
    public ResponseEntity getUserData(Principal principal) {
        User user;
        if (principal == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        try {
            user = userDataService.getDataForLoggedUser(principal);
        } catch (NotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/saveUserLocation", method = RequestMethod.PUT)
    public DeferredResult<String> saveUserLocation(@RequestBody @Valid LocationRequest locationRequest, Principal principal) {
        DeferredResult<String> result = new DeferredResult<>();
        if (principal == null)
            result.setErrorResult("HTTP response is: " + HttpStatus.NOT_FOUND);
        try {
            userDataService.saveUserLocation(locationRequest.getLongitude(), locationRequest.getLatitude(), principal, result);
        } catch (NotFoundException e) {
            result.setErrorResult("HTTP response is: " + HttpStatus.NOT_FOUND);
        }
        return result;
    }

    @RequestMapping(value = "/changeUserData", method = RequestMethod.POST)
    public ResponseEntity changeUserData(@RequestBody UserRequest userRequest) {
        Optional<User> oldUser = userDataService.findUserById(userRequest.getUser().getId());
        if (oldUser.isPresent()) {
            oldUser.get().setFirstName(userRequest.getUser().getFirstName());
            oldUser.get().setLastName(userRequest.getUser().getLastName());
            oldUser.get().setEmailAddress(userRequest.getUser().getEmailAddress());
            userDataService.saveOrUpdateUser(oldUser.get(), userRequest.getPassword());
            return new ResponseEntity(userRequest.getUser(), HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/getUsersFavourites", method = RequestMethod.GET)
    public List<Favourite> getUsersFavouriteOffers(Principal principal) {
        try {
            User user = userDataService.getDataForLoggedUser(principal);
            List<Favourite> offers = offerService.getUsersFavouriteOffers(user);
            return offers.isEmpty() ? Collections.emptyList() : offers;
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @RequestMapping(value = "/removeFavouriteOffer", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<String> removeFavouriteOffer(@RequestBody FavouriteRequest favouriteRequest, Principal principal) {
        try {
            User user = userDataService.getDataForLoggedUser(principal);
            Offer offer = offerService.findByUrToOffer(favouriteRequest.getOffer().getUrlToOffer()).get();
            offerService.removeByOfferAndUser(offer,user);
            return ResponseEntity.status(HttpStatus.OK).body("Offer has been removed from favourites");
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error has occurred");
    }
}