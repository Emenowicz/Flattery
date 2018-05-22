package zpi.flattery.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.*;
import zpi.flattery.dto.LoginForm;
import zpi.flattery.security.LoginAuthenticationProvider;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class LoginController {

    @Autowired
    LoginAuthenticationProvider authenticationProvider;

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity loginUser(@RequestBody @Valid LoginForm loginForm, HttpServletRequest request) {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginForm.getUserName(), loginForm.getPassword());
            token.setDetails(new WebAuthenticationDetails(request));
            Authentication auth = authenticationProvider.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (AuthenticationCredentialsNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity("Logged in", HttpStatus.OK);
    }

    @RequestMapping(value = "/user")
    public String loggedUser() {
        return "You are logged in!";
    }


}
