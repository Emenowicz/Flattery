package zpi.flattery.service;

import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import zpi.flattery.models.User;
import zpi.flattery.repository.UserDao;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Optional;

@Service
public class UserDataService {

    @Resource
    UserDao userDao;

    public User getDataForLoggedUser(Principal principal) throws NotFoundException {

        Optional<User> user = userDao.findByUserName(principal.getName());
        if (!user.isPresent()) {
            throw new NotFoundException("User is not found");
        }

        return user.get();
    }
}
