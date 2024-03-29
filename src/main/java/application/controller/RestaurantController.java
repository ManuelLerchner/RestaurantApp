package application.controller;

import application.model.Comment;
import application.model.Restaurant;
import application.model.RestaurantSimple;
import application.model.enums.PriceCategory;
import application.model.enums.RestaurantType;
import application.model.util.DateTimeSlot;
import application.model.util.Location;
import application.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    /**
     * This method searches for the restaurant with the specified ID
     *
     * @param restaurantId
     * @return ResponseEntity containing the restaurant with the specified id or notFound() if the restaurant does not exist
     */
    @GetMapping("restaurants/{restaurantId}")
    public ResponseEntity<Restaurant> retrieveDetailsForRestaurant(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantService.retrieveRestaurant(restaurantId);
        if (restaurant == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(restaurant);
    }

    /**
     * This method searches/filters the restaurants that fulfill the specified filter-parameters
     * <p>
     * The method only filters on the criteria specified directly as RequestParam,
     * unspecified parameters are replaced by a default value that does not affect the filtering
     * <p>
     * Filtering for free Tables requires date, timeSlot and capacity:
     * When specifying date and capacity, a default timeSlot (18.00 - 20.00) is automatically used if not specified
     * Otherwise if you specify timeSlot, date and capacity are not automatically specified and the specification of
     * the timeSlot does not affect the filtering
     * <p>
     * Filtering for distance to a certain location requires the maxDistance and the userPosition,
     * specification of just one parameter does not affect the filtering
     *
     * @param restaurantType
     * @param priceCategory
     * @param maxDistance
     * @param minRating      must be between 1 and 5
     * @param listSize
     * @param capacity
     * @param userPosition
     * @param date
     * @param timeSlot
     * @return By listSize limited List of restaurants fulfilling the filter criteria
     */
    @GetMapping("restaurants")
    public ResponseEntity<List<RestaurantSimple>> retrieveRestaurants(
            @RequestParam(name = "restaurantType", defaultValue = "DEFAULT") RestaurantType restaurantType,
            @RequestParam(name = "priceCategory", defaultValue = "DEFAULT") PriceCategory priceCategory,
            @RequestParam(name = "maxDistance", defaultValue = "-1") double maxDistance,
            @RequestParam(name = "minRating", defaultValue = "1") int minRating,
            @RequestParam(name = "listSize", defaultValue = "50") int listSize,
            @RequestParam(name = "capacity", defaultValue = "-1") int capacity,
            @RequestParam(name = "userPosition", defaultValue = "11.5755203, 48.1372264") List<Double> userPosition,
            @RequestParam(name = "date", defaultValue = "null") String date,
            @RequestParam(name = "timeSlot", defaultValue = "18.0, 20.0") List<Double> timeSlot
    ) {

        //System.out.println(date + "   " + timeSlot);
        DateTimeSlot dateTimeSlot = DateTimeSlot.convertToDateTimeSlot(date, timeSlot.get(0), timeSlot.get(1));
        //System.out.println(dateTimeSlot);

        Location userLocation;
        double longitude = userPosition.get(0);
        double latitude = userPosition.get(1);
        if (userPosition.get(0) <= -180.0 || longitude > 180.0 || latitude < -90.0 || latitude > 90.0) {
            userLocation = null;
        } else {
            userLocation = new Location();
            userLocation.setLongitude(longitude);
            userLocation.setLatitude(latitude);
        }

        if (!isValidParameters(restaurantType, priceCategory, maxDistance, userLocation, minRating, listSize, dateTimeSlot)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(restaurantService.readFilteredRestaurants(
                restaurantType,
                priceCategory,
                minRating,
                maxDistance,
                userLocation,
                listSize,
                dateTimeSlot,
                capacity
        ));
    }


    @GetMapping(value = "restaurants/getSuitableTables")
    public List<Boolean> getSuitableTables(
            @RequestParam Long restaurantId,
            @RequestParam Integer numberOfPersons,
            @RequestParam(name = "date", defaultValue = "null") String date,
            @RequestParam(name = "timeSlot", defaultValue = "18.0, 20.0") List<Double> timeSlot) {
        DateTimeSlot dateTimeSlot = DateTimeSlot.convertToDateTimeSlot(date, timeSlot.get(0), timeSlot.get(1));
        if(restaurantId == null || numberOfPersons == null || dateTimeSlot == null || numberOfPersons < 1) {
            return null;
        }

        return restaurantService.findSuitableTables(restaurantId, numberOfPersons, dateTimeSlot);

    }

    /**
     * This method adds the given comment to the restaurant
     * Notice that the specification of the restaurant is part of the comment object itself
     *
     * @param comment
     * @return String with information about success or reason for failing
     */
    @PostMapping(value = "restaurants/addComment")
    public String addComment(@RequestBody(required = true) Comment comment) {
        if (!isValidComment(comment)) {
            return "no valid comment";
        }
        Date date = new Date(System.currentTimeMillis());
        comment.setDate(date);
        return restaurantService.addCommentToRestaurant(comment);
    }

    /**
     * This method checks whether the given comment is valid
     * <p>
     * valid comment has to fulfill the following requirements:
     * comment must not be null,
     * comment.getHeadline() must not be null,
     * comment.getRating() must not be null and between 1 and 5,
     * comment.getText() must not be null,
     * comment.getRestaurant() must not be null,
     * comment.getId() must be null
     *
     * @param comment
     * @return true if the comment is valid, false otherwise
     */
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

    /**
     * This method checks whether the given parameters are valid
     * <p>
     * valid parameters have to fulfill the following requirements:
     * restaurantType has to exist,
     * priceCategory has to exist,
     * maxDistance > 0 if the userLocation is specified (not null)
     * minRating must be between 1 and 5
     * if dateTimeSlot is specified, the attributes startTime, endTime and date must not be null
     * number must be greater than 1
     *
     * @param restaurantType
     * @param priceCategory
     * @param maxDistance
     * @param userLocation
     * @param minRating
     * @param number
     * @param dateTimeSlot
     * @return true if the parameters for filtering are valid, false otherwise
     */
    private boolean isValidParameters(RestaurantType restaurantType, PriceCategory priceCategory, double maxDistance, Location userLocation, int minRating, int number, DateTimeSlot dateTimeSlot) {

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

        if (dateTimeSlot != null && (dateTimeSlot.getDate() == null || dateTimeSlot.getStartTime() == null || dateTimeSlot.getEndTime() == null || dateTimeSlot.getStartTime().compareTo(dateTimeSlot.getEndTime()) > 0)) {
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
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        if (!isValidRestaurant(restaurant)) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("ErrorMessage", "Required parameters are missing");
            return new ResponseEntity<>(restaurant, headers, HttpStatus.BAD_REQUEST);
        }
        Restaurant restaurantEntity = restaurantService.createRestaurant(restaurant);
        if (restaurantEntity != null) {
            return ResponseEntity.ok(restaurantEntity);
        }
        return ResponseEntity.badRequest().build(); // id already exists
    }

    private boolean isValidRestaurant(Restaurant restaurant) {
        if (restaurant.getName() == null || restaurant.getLocation() == null || restaurant.getOpeningTimes() == null || restaurant.getLinkToWebsite() == null || restaurant.getPriceCategory() == null || restaurant.getRestaurantType() == null || restaurant.getLayoutId() == null) {
            return false;
        }
        return true;
    }

    @RequestMapping(value = "createRestaurants", method = RequestMethod.POST)
    public String createRestaurants(@RequestBody List<Restaurant> restaurants) {
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

    @RequestMapping(value = "deleteAllRestaurants", method = RequestMethod.DELETE)
    public String deleteAllRestaurants() {
        return restaurantService.deleteAllRestaurants();
    }

    @PutMapping("updateAverageRating")
    public String updateAverageRatings() {
        restaurantService.updateAverageRating();
        return "averageRating updated";
    }

    @PutMapping("updateCommentCount")
    public String updateCommentCount() {
        restaurantService.updateCommentCount();
        return "commentCount updated";
    }
}
