package zpi.flattery.service;

import javassist.NotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import zpi.flattery.models.User;
import zpi.flattery.repository.UserDao;

import javax.annotation.Resource;
import java.security.Principal;

@Service
public class UserDataService {

    @Resource
    UserDao userDao;

    public User getDataForLoggedUser(Principal principal) throws NotFoundException {

        User user = userDao.findByUserName(principal.getName());
        if(user == null){
            throw new NotFoundException("User data not found");
        }
        return user;
    }
}
