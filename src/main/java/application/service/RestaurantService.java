package application.service;

import application.model.Restaurant;
import application.model.enums.PriceCategory;
import application.model.enums.RestaurantType;
import application.model.util.Location;
import application.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Transactional
    public List<Restaurant> readFilteredRestaurants(RestaurantType restaurantType, PriceCategory priceCategory, int minRating, double maxDistance, Location userLocation, int number) {
        List<Restaurant> restaurants = new ArrayList<>();
        System.out.println(minRating);
        // filter for restaurantType, priceCategory and minRating with database query
        if (restaurantType != RestaurantType.DEFAULT && priceCategory != PriceCategory.DEFAULT && minRating > 1) {
            restaurants = restaurantRepository.findByRestaurantTypeAndPriceCategoryAndAverageRating(restaurantType, priceCategory, (double) minRating);
        } else if (restaurantType != RestaurantType.DEFAULT && priceCategory != PriceCategory.DEFAULT) {
            restaurants = restaurantRepository.findByRestaurantTypeAndPriceCategory(restaurantType, priceCategory);
        } else if (restaurantType != RestaurantType.DEFAULT && minRating > 1) {
            restaurants = restaurantRepository.findByRestaurantTypeAndAverageRating(restaurantType, (double) minRating);
        } else if (priceCategory != PriceCategory.DEFAULT && minRating > 1) {
            restaurants = restaurantRepository.findByPriceCategoryAndAverageRating(priceCategory, (double) minRating);
        } else if (restaurantType != RestaurantType.DEFAULT) {
            restaurants = restaurantRepository.findByRestaurantType(restaurantType);
        } else if (priceCategory != PriceCategory.DEFAULT) {
            restaurants = restaurantRepository.findByPriceCategory(priceCategory);
        } else if (minRating > 1) {
            restaurants = restaurantRepository.findByAverageRating((double) minRating);
        } else {
            restaurants = restaurantRepository.findAll();
        }

        // sorting for average Rating
        restaurants = restaurants.stream().sorted(Comparator.comparingDouble(Restaurant::getAverageRating).reversed()).toList();

        // filter for maxDistance and Location
        if (maxDistance > 0 && userLocation != null && userLocation.getLongitude() != null && userLocation.getLatitude() != null) {
            restaurants = restaurants.stream().filter(r -> {
                if(r.getLocation() != null && r.getLocation().getLatitude() != null && r.getLocation().getLongitude() != null) {
                    double distance = r.getLocation().getDistanceTo(userLocation);
                    if(r.getLocation().getDistanceTo(userLocation) <= maxDistance) {
                        // value exists only in response -> not stored in database
                        r.setDistanceToUser(distance);
                        return true;
                    }
                }
                return false;
            }).toList();
            // sorting for distanceToUser
            restaurants = restaurants.stream().sorted(Comparator.comparingDouble(Restaurant::getDistanceToUser)).toList();
        }

        // Limit list of restaurants to number
        return restaurants.subList(0, Math.min(restaurants.size(), number));
    }

    @Transactional
    public Restaurant retrieveRestaurant(Long id) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
        return optionalRestaurant.orElse(null);
    }


    // **************************
    // Test purpose
    // **************************

    @Transactional
    public String createRestaurant(Restaurant restaurant) {
        if (restaurant.getId() == null) {
            restaurantRepository.save(restaurant);
            return "Restaurant record created successfully";
        } else {
            return "Restaurant already exists";
        }
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
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurant.getId());
        if (optionalRestaurant.isPresent()) {
            restaurantRepository.delete(optionalRestaurant.get());
            return "Restaurant record deleted successfully.";
        } else {
            return "Restaurant does not exist";
        }
    }

    @Transactional
    public List<Restaurant> readRestaurants() {
        return restaurantRepository.findAll();
    }

}
