package zpi.flattery.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import zpi.flattery.dto.RegistrationForm;
import zpi.flattery.service.RegistrationService;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Resource
    RegistrationService registrationService;

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity registerNewAccount(@RequestBody @Valid RegistrationForm form) {
        try {
            registrationService.register(form);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
