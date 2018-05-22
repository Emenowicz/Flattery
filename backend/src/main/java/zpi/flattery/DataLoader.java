package zpi.flattery;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import zpi.flattery.models.User;
import zpi.flattery.repository.UserDao;

import javax.annotation.Resource;


@Component
public class DataLoader implements ApplicationRunner {


    @Resource
    UserDao userDao;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userDao.save(new User("Test", "User", "test@test.com", "user", "password"));
        userDao.save(new User("Test1", "User1", "test1@test.com", "user1", "password"));
        userDao.save(new User("Test2", "User2", "test2@test.com", "flattery", "encrypt"));
    }
}
