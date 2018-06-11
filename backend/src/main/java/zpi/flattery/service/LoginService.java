package zpi.flattery.service;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zpi.flattery.dto.LoginForm;
import zpi.flattery.models.User;
import zpi.flattery.repository.UserDao;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class LoginService implements UserDetailsService {


    @Resource
    private UserDao userDao;
    @Resource
    private PasswordEncoder passwordEncoder;

    public User getUser(LoginForm loginForm) throws Exception {
        Optional<User> user = userDao.findByUserName(loginForm.getUserName());
        if (user.isPresent()) {
            if(passwordEncoder.matches(loginForm.getPassword(),user.get().getPassword())){
                return user.get();
            }
        }

        throw new AuthenticationCredentialsNotFoundException("Username or password are incorrect!");
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
