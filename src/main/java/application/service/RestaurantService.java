package application.service;

import application.model.Restaurant;
import application.model.enums.PriceCategory;
import application.model.enums.RestaurantType;
import application.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Transactional
    public String createRestaurant(Restaurant restaurant) {
        //if (!restaurantRepository.existsById(restaurant.getId())) {
        restaurantRepository.save(restaurant);
        return "Restaurant record created successfully";
        //} else {
        //  return "Restaurant already exists";
        //}
    }

    @Transactional
    public String
    updateRestaurant(Restaurant restaurant) {
        if (restaurantRepository.existsById(restaurant.getId())) {
            restaurantRepository.save(restaurant);
            return "Restaurant record updated.";
        } else {
            return "Restaurant not existent";
        }
    }


    @Transactional
    public String deleteRestaurant(Restaurant restaurant) {
        if (restaurantRepository.existsById(restaurant.getId())) {
            try {
                restaurantRepository.delete(restaurantRepository.findById(restaurant.getId()).get());
                return "Restaurant record deleted successfully.";
            } catch (Exception e) {
                throw e;
            }
        } else {
            return "Restaurant does not exist";
        }
    }

    @Transactional
    public List<Restaurant> readRestaurants() {
        return restaurantRepository.findAll();
    }

    @Transactional
    public List<Restaurant> readFilteredRestaurants(RestaurantType restaurantType, PriceCategory priceCategory, double averageRating) {
        return restaurantRepository.findByRestaurantTypeAndPriceCategoryAndAverageRatingOrderByAverageRatingAsc(restaurantType, priceCategory, averageRating);
    }

    @Transactional
    public Restaurant retrieveRestaurant(Long id) {
        if (restaurantRepository.existsById(id) && restaurantRepository.findById(id).isPresent()) {
            return restaurantRepository.findById(id).get();
        }
        return null;
    }


}
