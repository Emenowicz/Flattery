package zpi.flattery;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import zpi.flattery.dto.RegistrationForm;
import zpi.flattery.models.User;
import zpi.flattery.repository.UserDao;
import zpi.flattery.service.RegistrationService;

import javax.annotation.Resource;


@Component
public class DataLoader implements ApplicationRunner {

    @Resource
    RegistrationService registrationService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        RegistrationForm registrationForm = new RegistrationForm("User","Userski","user","pass12","pass12","user@user.com");
        registrationService.register(registrationForm);
        RegistrationForm registrationForm1 = new RegistrationForm("User","Userski","user1","pass12","pass12","user1@user.com");
        registrationService.register(registrationForm1);
    }
}
