package zpi.flattery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zpi.flattery.models.Favourite;
import java.util.List;

@Repository
public interface FavouriteDao extends JpaRepository<Favourite, Favourite.FavouriteId> { }
