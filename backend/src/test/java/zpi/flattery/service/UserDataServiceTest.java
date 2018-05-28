package zpi.flattery.service;

import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.security.acl.PrincipalImpl;
import zpi.flattery.models.User;
import zpi.flattery.repository.UserDao;

import javax.annotation.Resource;

import java.security.Principal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDataServiceTest {
    @Mock
    UserDao userDao;
    @InjectMocks
    UserDataService userDataService;

    @Test
    public void shouldReturnDataForLoggedUser() throws NotFoundException {
        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Testowy");
        Principal principal = new PrincipalImpl("Test");


        when(userDao.findByUserName(anyString())).thenReturn(Optional.of(user));

        User result = userDataService.getDataForLoggedUser(principal);

        assertEquals(result,user);
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowNullPointer() throws NotFoundException {
        when(userDao.findByUserName(anyString())).thenReturn(Optional.empty());
        Principal principal = new PrincipalImpl("Test");

        userDataService.getDataForLoggedUser(principal);
    }

}