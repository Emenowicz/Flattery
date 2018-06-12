package zpi.flattery.controllers;

import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import zpi.flattery.controllers.model.LocationRequest;
import zpi.flattery.controllers.model.UserRequest;
import zpi.flattery.models.User;
import zpi.flattery.service.UserDataService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@RestController
public class UserDataController {

    @Resource
    UserDataService userDataService;

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
    public ResponseEntity changeUserData(@RequestBody UserRequest userRequest){
            Optional<User> oldUser = userDataService.findUserById(userRequest.getUser().getId());
        if(oldUser.isPresent()){
            oldUser.get().setFirstName(userRequest.getUser().getFirstName());
            oldUser.get().setLastName(userRequest.getUser().getLastName());
            oldUser.get().setEmailAddress(userRequest.getUser().getEmailAddress());
            userDataService.saveOrUpdateUser(oldUser.get(), userRequest.getPassword());
            return new ResponseEntity(userRequest.getUser(), HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.CONFLICT);
    }
}