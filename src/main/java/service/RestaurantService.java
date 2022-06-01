package service;

import model.restaurant.RestaurantType;
import model.util.Location;
import model.Comment;
import model.restaurant.PriceCategory;
import model.restaurant.Restaurant;
import model.util.WeekTimeSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.RestaurantRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    private List<Restaurant> restaurants;

    // TODO replace default data by connection to data base
    /*
    public RestaurantService() {
        restaurants = new ArrayList<Restaurant>();

        List<Comment> commentstest = List.of(new Comment(), new Comment(), new Comment());

        // Initialize Data
        Restaurant res1 = new Restaurant("name1", "linkToRestaurant1", List.of("/pathPicture1", "/pathPicture2"), PriceCategory.NORMAL, RestaurantType.INDIAN, new Location(), new WeekTimeSlot[7], commentstest);
        Restaurant res2 = new Restaurant("name2", "linkToRestaurant2", List.of("/pathPicture1", "/pathPicture2"), PriceCategory.CHEAP, RestaurantType.ITALIAN, new Location(), new WeekTimeSlot[7], new ArrayList<Comment>());
        Restaurant res3 = new Restaurant("name3", "linkToRestaurant3", List.of("/pathPicture1", "/pathPicture2"), PriceCategory.COSTLY, RestaurantType.CHINESE, new Location(), new WeekTimeSlot[7], new ArrayList<Comment>());
        Restaurant res4 = new Restaurant("name4", "linkToRestaurant4", List.of("/pathPicture1", "/pathPicture2"), PriceCategory.NORMAL, RestaurantType.ITALIAN, new Location(), new WeekTimeSlot[7], new ArrayList<Comment>());
        Restaurant res5 = new Restaurant("name5", "linkToRestaurant5", List.of("/pathPicture1", "/pathPicture2"), PriceCategory.NORMAL, RestaurantType.INDIAN, new Location(), new WeekTimeSlot[7], new ArrayList<Comment>());
        Restaurant res6 = new Restaurant("name6", "linkToRestaurant6", List.of("/pathPicture1", "/pathPicture2"), PriceCategory.COSTLY, RestaurantType.ITALIAN, new Location(), new WeekTimeSlot[7], new ArrayList<Comment>());
        Restaurant res7 = new Restaurant("name7", "linkToRestaurant7", List.of("/pathPicture1", "/pathPicture2"), PriceCategory.NORMAL, RestaurantType.CHINESE, new Location(), new WeekTimeSlot[7], new ArrayList<Comment>());
        Restaurant res8 = new Restaurant("name8", "linkToRestaurant8", List.of("/pathPicture1", "/pathPicture2"), PriceCategory.CHEAP, RestaurantType.ITALIAN, new Location(), new WeekTimeSlot[7], new ArrayList<Comment>());
        Restaurant res9 = new Restaurant("name9", "linkToRestaurant9", List.of("/pathPicture1", "/pathPicture2"), PriceCategory.CHEAP, RestaurantType.INDIAN, new Location(), new WeekTimeSlot[7], new ArrayList<Comment>());
        Restaurant res10 = new Restaurant("name10", "linkToRestaurant10", List.of("/pathPicture1", "/pathPicture2"), PriceCategory.NORMAL, RestaurantType.ITALIAN, new Location(), new WeekTimeSlot[7], new ArrayList<Comment>());
        restaurants.add(res1);
        restaurants.add(res2);
        restaurants.add(res3);
        restaurants.add(res4);
        restaurants.add(res5);
        restaurants.add(res6);
        restaurants.add(res7);
        restaurants.add(res8);
        restaurants.add(res9);
        restaurants.add(res10);
    }
    */

    @Transactional
    public String createRestaurant(Restaurant restaurant) {
        try {
            if(!restaurantRepository.existsByName(restaurant.getName())) {
                restaurantRepository.save(restaurant);
                return "Restaurant saved successfully!";
            } else {
                return "Restaurant already exists in database";
            }
        } catch(Exception e) {
            throw e;
        }
    }


    public List<Restaurant> retrieveAllRestaurants() {
        return restaurants;
    }

    public Restaurant retrieveRestaurant(int restaurantId) {
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getId() == restaurantId) {
                return restaurant;
            }
        }
        return null;
    }

    public List<Restaurant> filterRestaurant(int number, RestaurantType restaurantType, PriceCategory priceCategory, Location userLocation, double maxDistance, int minRating) {
        //TODO: Update list  z.B. restaurants = restaurantRepository.findByName("abc");
        Stream<Restaurant> restaurantStream = restaurants.stream();

        if(restaurantType != RestaurantType.DEFAULT) {
            restaurantStream = filterByType(restaurantStream, restaurantType);
        }

        if(priceCategory != PriceCategory.DEFAULT) {
            restaurantStream = filterByPriceCategory(restaurantStream, priceCategory);
        }

        if(maxDistance > 0 && userLocation != null) {
            restaurantStream = filterByMaxDistance(restaurantStream, userLocation, maxDistance);
        }

        if(minRating > 1) {
            restaurantStream = filterByMinRating(restaurantStream, minRating);
        }

        restaurantStream = restaurantStream.limit(Math.min(50, number));

        return restaurantStream.toList();
    }

    private Stream<Restaurant> filterByType(Stream<Restaurant> restaurantStream, RestaurantType restaurantType) {
        return restaurantStream.filter(restaurant -> restaurant.getRestaurantType() == restaurantType);
    }

    private Stream<Restaurant> filterByPriceCategory(Stream<Restaurant> restaurantStream, PriceCategory priceCategory) {
        return restaurantStream.filter(restaurant -> restaurant.getPriceCategory() == priceCategory);
    }

    private Stream<Restaurant> filterByMaxDistance(Stream<Restaurant> restaurantStream, Location userLocation, double maxDistance) {
        return restaurantStream.filter(restaurant -> restaurant.getLocation().getDistanceTo(userLocation) < maxDistance);
    }

    private Stream<Restaurant> filterByMinRating(Stream<Restaurant> restaurantStream, int minRating) {
        return restaurantStream.filter(restaurant -> restaurant.getAverageRating() >= minRating);
    }

    public List<Comment> retrieveComments(int restaurantId) {
        Restaurant restaurant = retrieveRestaurant(restaurantId);
        if (restaurant == null) {
            return null;
        }
        return restaurant.getComments();
    }

    public void addComment(int restaurantId, Comment comment) {
        Restaurant restaurant = retrieveRestaurant(restaurantId);
        if (restaurant == null) {
            return;
        }
        restaurant.addComment(comment);
    }


}
