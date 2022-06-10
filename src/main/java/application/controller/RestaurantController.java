package application.controller;

import application.entity.Restaurant;
import application.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @RequestMapping(value = "info", method = RequestMethod.GET)
    public String info() {
        return "The application is started!";
    }

    @RequestMapping(value = "createRestaurant", method = RequestMethod.POST)
    public String createRestaurant(@RequestBody Restaurant restaurant) {
        return restaurantService.createRestaurant(restaurant);
    }

    @RequestMapping(value = "readRestaurants", method = RequestMethod.GET)
    public List<Restaurant> readRestaurants() {
        return restaurantService.readRestaurants();
    }

    @RequestMapping(value = "updateRestaurant", method = RequestMethod.PUT)
    public String updateRestaurant(@RequestBody Restaurant restaurant) {
        return restaurantService.updateRestaurant(restaurant);
    }

    @RequestMapping(value = "deleteRestaurant", method = RequestMethod.DELETE)
    public String deleteRestaurant(@RequestBody Restaurant restaurant) {
        return restaurantService.deleteRestaurant(restaurant);
    }
}
