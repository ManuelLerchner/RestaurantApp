package application.controller;

import application.model.Comment;
import application.model.Restaurant;
import application.model.enums.PriceCategory;
import application.model.enums.RestaurantType;
import application.model.util.Location;
import application.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;


    /**
     * @param restaurantId
     * @return
     */
    @GetMapping("restaurants/{restaurantId}")
    public ResponseEntity<Restaurant> retrieveDetailsForRestaurant(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantService.retrieveRestaurant(restaurantId);
        if(restaurant == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(restaurant);
    }

    /**
     * @param restaurantType
     * @param priceCategory
     * @param maxDistance
     * @param minRating
     * @param number
     * @param userLocation
     * @return
     */
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

    /**
     * @param comment required attributes for comment: headline, text, rating, user, restaurant
     * @return
     */
    @PostMapping(value = "restaurants/addComment")
    public String addComment(@RequestBody(required = true) Comment comment) {
        if(!isValidComment(comment)) {
            return "no valid comment";
        }
        Date date = new Date(System.currentTimeMillis());
        comment.setDate(date);
        return restaurantService.addCommentToRestaurant(comment);
    }

    private boolean isValidComment(Comment comment) {
        // check not null for comment, headline, rating, text and user
        if (comment == null || comment.getHeadline() == null || comment.getRating() == null || comment.getText() == null || comment.getUser() == null || comment.getRestaurant() == null) {
            return false;
        }
        // check value of rating is in valid interval [1; 5]
        if (comment.getRating() < 1 || comment.getRating() > 5) {
            return false;
        }
        // check id is not specified yet
        if (comment.getId() != null) {
            return false;
        }
        return true;
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
        return restaurantService.createRestaurant(restaurant);
    }

    @RequestMapping(value = "createRestaurants", method = RequestMethod.POST)
    public String createRestaurant(@RequestBody List<Restaurant> restaurants) {
        return restaurantService.createRestaurants(restaurants);
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
