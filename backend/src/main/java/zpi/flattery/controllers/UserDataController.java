package zpi.flattery.controllers;

import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import zpi.flattery.models.User;
import zpi.flattery.service.UserDataService;

import javax.annotation.Resource;
import java.security.Principal;

@RestController
public class UserDataController {

    @Resource
    UserDataService userDataService;

    @RequestMapping(value = "/loggedUserData", method = RequestMethod.GET)
    public ResponseEntity getUserData() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user;
        try {
            user = userDataService.getDataForLoggedUser((Principal) auth.getPrincipal());
        } catch (NotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(user, HttpStatus.OK);
    }
}
