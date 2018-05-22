package zpi.flattery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zpi.flattery.models.User;

@Repository
public interface UserDao extends JpaRepository<User,Integer> {
    boolean existsByEmailAddressOrUserName(String email,String Username);
    User findByUserName(String username);

}
