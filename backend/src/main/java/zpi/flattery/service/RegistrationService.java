package zpi.flattery.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Resource
    PasswordEncoder passwordEncoder;


    public void register(RegistrationForm form) throws Exception {
        if (userDao.existsByEmailAddressOrUserName(form.getEmailAddress(), form.getUserName())) {
            throw new Exception("That user already exists");
        }
        User user = modelMapper.map(form, User.class);
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        userDao.save(user);
    }
}
