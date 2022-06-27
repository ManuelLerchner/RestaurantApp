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
    @Query("select r from Restaurant r where r.restaurantType = ?1")
    List<Restaurant> findByRestaurantType(RestaurantType restaurantType);

    @Query("select r from Restaurant r where r.priceCategory = ?1")
    List<Restaurant> findByPriceCategory(PriceCategory priceCategory);

    @Query("select r from Restaurant r where r.averageRating >= ?1")
    List<Restaurant> findByAverageRating(Double averageRating);

    @Query("select r from Restaurant r where r.restaurantType = ?1 and r.priceCategory = ?2")
    List<Restaurant> findByRestaurantTypeAndPriceCategory(RestaurantType restaurantType, PriceCategory priceCategory);

    @Query("select r from Restaurant r where r.priceCategory = ?1 and r.averageRating >= ?2")
    List<Restaurant> findByPriceCategoryAndAverageRating(PriceCategory priceCategory, Double averageRating);

    @Query("select r from Restaurant r where r.restaurantType = ?1 and r.averageRating >= ?2")
    List<Restaurant> findByRestaurantTypeAndAverageRating(RestaurantType restaurantType, Double averageRating);

    @Query("select r from Restaurant r where r.restaurantType = ?1 and r.priceCategory = ?2 and r.averageRating >= ?3")
    List<Restaurant> findByRestaurantTypeAndPriceCategoryAndAverageRating(RestaurantType restaurantType, PriceCategory priceCategory, Double averageRating);

    @Transactional
    @Modifying
    @Query("update Restaurant r set r.averageRating = ?1 where r.id = ?2")
    int updateAverageRatingById(Double averageRating, Long id);

    @Transactional
    @Modifying
    @Query("update Restaurant r set r.commentCount = ?1 where r.id = ?2")
    int updateCommentCountById(Double commentCount, Long id);


}
