package zpi.flattery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zpi.flattery.models.User;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User,Integer> {
    public boolean existsByEmailAddressOrUserName(String email,String Username);

    boolean existsByUserName(String userName);

    Optional<User> findByUserName(String userName);
}
