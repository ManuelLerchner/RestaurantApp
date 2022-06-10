package application.service;

import application.entity.Restaurant;
import application.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Transactional
    public String createRestaurant(Restaurant restaurant) {
        try {
            if (!restaurantRepository.existsByName(restaurant.getName())) {
                restaurantRepository.save(restaurant);
                return "Restaurant record created successfully";
            } else {
                return "Restaurant already exists";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Restaurant> readRestaurants() {
        return restaurantRepository.findAll();
    }

    @Transactional
    public String
    updateRestaurant(Restaurant restaurant) {
        try {
            Restaurant restaurantToBeUpdate = restaurantRepository.findById(restaurant.getId()).get();
            restaurantToBeUpdate.setName(restaurant.getName());
            restaurantRepository.save(restaurantToBeUpdate);
            return "Restaurant record updated.";
        } catch (NoSuchElementException e) {
            return "Restaurant not existent";
        }
    }


    @Transactional
    public String deleteRestaurant(Restaurant restaurant) {
        if (restaurantRepository.existsByName(restaurant.getName())) {
            try {
                List<Restaurant> restaurants = restaurantRepository.findByName(restaurant.getName());
                restaurantRepository.deleteAll(restaurants);
                return "Restaurant record deleted successfully.";
            } catch (Exception e) {
                throw e;
            }

        } else {
            return "Restaurant does not exist";
        }
    }
}
