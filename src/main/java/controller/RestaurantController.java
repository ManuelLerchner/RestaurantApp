package controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class RestaurantController {

    // https://www.postman.com/apicuy/workspace/api-srv-appx-dprri-s-public-workspace/request/3138027-d0ec25db-b153-4d1c-8426-3c0823009ef5

    //@Autowired
    //private RestaurantService restaurantService;

    // TODO parse to json
    /*@GetMapping("/restaurants")
    public List<Restaurant> retrieveRestaurants(@RequestParam(value = "number") int number) {
        return restaurantService.retrieveRestaurants(number);
    }

    @GetMapping("/restaurants/{restaurantId}")
    public Restaurant retrieveDetailsForRestaurant(@PathVariable Integer restaurantId) {
        return restaurantService.retrieveRestaurant(restaurantId);
    }*/

    @GetMapping("/restaurants/{restaurantId}")
    public String retrieveDetailsForRestaurant(@PathVariable Integer restaurantId) {
        return "restaurant details for: " + restaurantId;
    }




}

