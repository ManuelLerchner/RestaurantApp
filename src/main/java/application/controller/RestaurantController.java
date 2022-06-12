package application.controller;

import application.model.Restaurant;
import application.model.enums.PriceCategory;
import application.model.enums.RestaurantType;
import application.model.util.Location;
import application.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;


    @GetMapping("restaurants/{restaurantId}")
    public ResponseEntity<Restaurant> retrieveDetailsForRestaurant(@PathVariable Long restaurantId) {
        Restaurant returnObject = restaurantService.retrieveRestaurant(restaurantId);
        if(returnObject == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(returnObject);
    }

    @GetMapping("restaurants")
    public ResponseEntity<List<Restaurant>> retrieveRestaurants(
            @RequestParam(name = "restaurantType", defaultValue = "DEFAULT") RestaurantType restaurantType,
            @RequestParam(name = "priceCategory", defaultValue = "DEFAULT") PriceCategory priceCategory,
            @RequestParam(name = "maxDistance", defaultValue = "-1") double maxDistance,
            @RequestParam(name = "minRating", defaultValue = "1") int minRating,
            @RequestParam(name = "number", defaultValue = "50") int number,
            @RequestBody(required = false) Location userLocation
    ) {
        if (!isValidParameters(restaurantType, priceCategory, maxDistance, userLocation, minRating, number)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(restaurantService.readFilteredRestaurants(
                restaurantType,
                priceCategory,
                minRating,
                maxDistance,
                userLocation,
                number
        ));
    }


    private boolean isValidParameters(RestaurantType restaurantType, PriceCategory priceCategory, double maxDistance, Location userLocation, int minRating, int number) {

        boolean isValidRestaurantType = false;
        for (RestaurantType t : RestaurantType.values()) {
            if (t == restaurantType) {
                isValidRestaurantType = true;
                break;
            }
        }

        boolean isValidPriceCategory = false;
        for (PriceCategory t : PriceCategory.values()) {
            if (t == priceCategory) {
                isValidPriceCategory = true;
                break;
            }
        }

        if (number < 1 || minRating < 1 || (maxDistance > 0 && userLocation == null) || !isValidRestaurantType || !isValidPriceCategory) {
            return false;
        }
        return true;
    }



    // **************************
    // Test purpose
    // **************************

    @RequestMapping(value = "info", method = RequestMethod.GET)
    public String info() {
        return "The application is started!";
    }

    @RequestMapping(value = "createRestaurant", method = RequestMethod.POST)
    public String createRestaurant(@RequestBody Restaurant restaurant) {
        System.out.println(restaurant.getId());
        return restaurantService.createRestaurant(restaurant);
    }

    @RequestMapping(value = "readRestaurants", method = RequestMethod.GET)
    public List<Restaurant> readRestaurants() {
        return restaurantService.readRestaurants();
    }

    @RequestMapping(value = "updateRestaurant", method = RequestMethod.PUT)
    public String updateRestaurantName(@RequestBody Restaurant restaurant) {
        return restaurantService.updateRestaurant(restaurant);
    }

    @RequestMapping(value = "deleteRestaurant", method = RequestMethod.DELETE)
    public String deleteRestaurant(@RequestBody Restaurant restaurant) {
        return restaurantService.deleteRestaurant(restaurant);
    }
}
