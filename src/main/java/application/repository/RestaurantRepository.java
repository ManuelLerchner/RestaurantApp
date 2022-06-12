package application.repository;

import application.model.Restaurant;
import application.model.enums.PriceCategory;
import application.model.enums.RestaurantType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query("""
            select r from Restaurant r
            where r.restaurantType = ?1 and r.priceCategory = ?2 and r.averageRating = ?3
            order by r.averageRating""")
    List<Restaurant> findByRestaurantTypeAndPriceCategoryAndAverageRatingOrderByAverageRatingAsc(RestaurantType restaurantType, PriceCategory priceCategory, Double averageRating);



}
