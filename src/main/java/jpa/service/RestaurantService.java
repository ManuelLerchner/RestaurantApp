package jpa.service;

import jpa.model.restaurant.Restaurant;
import jpa.model.restaurant.RestaurantType;
import jpa.repositories.RestaurantRepository;
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
        try {
            if (!restaurantRepository.existsByName(restaurant.getName())) {
                restaurantRepository.save(restaurant);
                return "Restaurant saved successfully";
            } else {
                return "Restaurant already in database";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public List<Restaurant> retrieveAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Transactional
    public List<Restaurant> retrieveByType(RestaurantType restaurantType) {
        return restaurantRepository.findByType(restaurantType);
    }
}
