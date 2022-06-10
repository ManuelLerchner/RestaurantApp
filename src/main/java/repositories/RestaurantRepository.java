package repositories;

import model.restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

//macht die Anfrage auf die Datenbank
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    public boolean existsByName(String name);

    public List<Restaurant> findByName(String name);

    @Query("select max(r.id) from Restaurant r")
    public Integer findMaxId();

}
