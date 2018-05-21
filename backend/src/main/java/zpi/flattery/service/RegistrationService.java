package zpi.flattery.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import zpi.flattery.dto.RegistrationForm;
import zpi.flattery.models.User;
import zpi.flattery.repository.UserDao;

import javax.annotation.Resource;

@Service
public class RegistrationService {

    @Resource
    UserDao userDao;
    @Resource
    ModelMapper modelMapper;

    public void register(RegistrationForm form) throws Exception {
        if (userDao.existsByEmailAddressOrUserName(form.getEmailAddress(), form.getUserName())) {
            throw new Exception("That user already exists");
        }
        userDao.save(modelMapper.map(form, User.class));
    }
}
