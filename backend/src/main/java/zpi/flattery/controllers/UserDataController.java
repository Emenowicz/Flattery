package zpi.flattery.controllers;

import io.reactivex.Observer;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import zpi.flattery.controllers.model.LocationRequest;
import zpi.flattery.models.User;
import zpi.flattery.service.UserDataService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.security.Principal;

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
            userDataService.saveUserLocation(locationRequest.getLogitude(), locationRequest.getLatitude(), principal, result);
        } catch (NotFoundException e) {
            result.setErrorResult("HTTP response is: " + HttpStatus.NOT_FOUND);
        }
        return result;
    }
}