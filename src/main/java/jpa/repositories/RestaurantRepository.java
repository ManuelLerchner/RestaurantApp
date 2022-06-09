package jpa.repositories;

import jpa.model.restaurant.PriceCategory;
import jpa.model.restaurant.Restaurant;
import jpa.model.restaurant.RestaurantType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    public boolean existsByName(String name);

    public List<Restaurant> findByType(RestaurantType restaurantType);

    @Query("SELECT r FROM Restaurant r WHERE r.getRestaurantType() = ?1 and r.getPriceCatergory = ?2")
    List<Restaurant> findRestaurants(RestaurantType restaurantType, PriceCategory priceCategory);

    @Query("SELECT r FROM Restaurant r WHERE r.getRestaurantType() = :type and r.getPriceCategory = :category")
    List<Restaurant> findRestaurantsNamedParams(
            @Param("type") RestaurantType restaurantType,
            @Param("category") PriceCategory priceCategory);
}