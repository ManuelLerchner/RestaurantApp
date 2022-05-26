package controller;

import model.restaurant.PriceCategory;
import model.restaurant.Restaurant;
import model.restaurant.RestaurantType;
import model.util.Location;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.RestaurantService;
import java.util.List;

@RestController
//@RequestMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
public class RestaurantController {

    private RestaurantService restaurantService = new RestaurantService();


    // averageRating, restaurantType, priceCategory, Anzahl, maxDistance, (freeTimeSlots)
    @GetMapping("restaurants")
    public ResponseEntity<List<Restaurant>> retrieveRestaurants(
            @RequestParam(name = "restaurantType", defaultValue = "DEFAULT") RestaurantType restaurantType,
            @RequestParam(name = "priceCategory", defaultValue = "DEFAULT") PriceCategory priceCategory,
            @RequestParam(name = "maxDistance", defaultValue = "-1") double maxDistance,
            @RequestParam(name = "minRating", defaultValue = "1") int minRating,
            @RequestParam(name = "number", defaultValue = "50") int number,
            @RequestBody(required = false) Location userLocation
    ) {

        if(!isValidParameters(restaurantType, priceCategory, maxDistance, userLocation, minRating, number)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(restaurantService.filterRestaurant(
                number,
                restaurantType,
                priceCategory,
                userLocation,
                maxDistance,
                minRating
        ));
    }

    private boolean isValidParameters(RestaurantType restaurantType, PriceCategory priceCategory, double maxDistance, Location userLocation, int minRating, int number) {

        boolean isValidRestaurantType = false;
        for(RestaurantType t : RestaurantType.values()) {
            if(t == restaurantType) {
                isValidRestaurantType = true;
                break;
            }
        }

        boolean isValidPriceCategory = false;
        for(PriceCategory t : PriceCategory.values()) {
            if(t == priceCategory) {
                isValidPriceCategory = true;
                break;
            }
        }

        if(number < 1 || minRating < 1 || (maxDistance > 0 && userLocation == null) || !isValidRestaurantType || !isValidPriceCategory) {
            return false;
        }
        return true;
    }

    @GetMapping("restaurants/{restaurantId}")
    public ResponseEntity<Restaurant> retrieveDetailsForRestaurant(@PathVariable Integer restaurantId) {
        // TODO check whether restaurantId exists
        return ResponseEntity.ok(restaurantService.retrieveRestaurant(restaurantId));
    }

}

