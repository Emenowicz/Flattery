package zpi.flattery.flattery;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zpi.flattery.models.User;
import zpi.flattery.repository.UserDao;
import zpi.flattery.service.UserDataService;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlatteryApplicationTests {


	@Test
	public void contextLoads() {
	}
}
