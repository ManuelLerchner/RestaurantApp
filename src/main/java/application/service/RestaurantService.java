package application.service;

import application.model.Comment;
import application.model.Reservation;
import application.model.Restaurant;
import application.model.RestaurantTable;
import application.model.enums.PriceCategory;
import application.model.enums.RestaurantType;
import application.model.util.DateTimeSlot;
import application.model.util.Location;
import application.model.util.WeekTimeSlot;
import application.repository.CommentRepository;
import application.repository.RestaurantRepository;
import application.repository.RestaurantTableRepository;
import application.repository.WeekTimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    private static final String PATH_TO_LAYOUTS = "src/main/resources/restaurantLayouts";

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RestaurantTableRepository tableRepository;

    @Autowired
    private WeekTimeSlotRepository weekTimeSlotRepository;


    /**
     * Filters all restaurants in the database by the given parameters
     *
     * If you don't want to filter for a parameter you have to set a corresponding value, these are:
     * restaurantType: RestaurantType.DEFAULT
     * priceCategory: PriceCategory.DEFAULT
     * minRating: <= 1
     * maxDistance and userLocation: maxDistance <= 0 || userLocation == null
     * number: Integer.MAX_VALUE
     * freeTimeSlot and requiredCapacity: freeTimeSlot == null || requiredCapacity <= 0
     *
     * @param restaurantType
     * @param priceCategory
     * @param minRating
     * @param maxDistance
     * @param userLocation
     * @param number
     * @param freeTimeSlot
     * @param requiredCapacity
     * @return a List containing the filtered restaurants
     */
    @Transactional
    public List<Restaurant> readFilteredRestaurants(RestaurantType restaurantType, PriceCategory priceCategory, int minRating, double maxDistance, Location userLocation, int number, DateTimeSlot freeTimeSlot, int requiredCapacity) {
        List<Restaurant> restaurants;
        // filter by restaurantType, priceCategory and minRating with database query
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

        // sorting by average Rating
        restaurants = restaurants.stream().sorted(Comparator.comparingDouble(Restaurant::getAverageRating).reversed()).toList();

        // filter by maxDistance and Location
        if (maxDistance > 0 && userLocation != null && userLocation.getLongitude() != null && userLocation.getLatitude() != null) {
            restaurants = restaurants.stream().filter(r -> {
                if (r.getLocation() != null && r.getLocation().getLatitude() != null && r.getLocation().getLongitude() != null) {
                    double distance = r.getLocation().getDistanceTo(userLocation);
                    if (r.getLocation().getDistanceTo(userLocation) <= maxDistance) {
                        // value exists only in response -> not stored in database
                        r.setDistanceToUser(distance);
                        return true;
                    }
                }
                return false;
            }).toList();
            // sorting by distanceToUser
            restaurants = restaurants.stream().sorted(Comparator.comparingDouble(Restaurant::getDistanceToUser)).toList();
        }

        // filter by freeTimeSlot and capacity
        if (requiredCapacity > 0 && freeTimeSlot != null) {
            // set endTime if not already specified
            if (freeTimeSlot.getEndTime() == null) {
                freeTimeSlot.setEndTime(freeTimeSlot.getStartTime().plusHours(2));
                if (freeTimeSlot.getEndTime().compareTo(freeTimeSlot.getStartTime()) < 0) {
                    freeTimeSlot.setEndTime(LocalTime.of(23, 59));
                }
            }
            restaurants = restaurants.stream().filter(restaurant -> {
                List<RestaurantTable> tables = restaurant.getRestaurantTables();
                for (RestaurantTable table : tables) {
                    if (requiredCapacity > table.getCapacity()) {
                        return false;
                    }
                    if (hasFreeTimeSlot(table, freeTimeSlot)) {
                        return true;
                    }
                }
                return false;
            }).toList();
        }

        // Limit list of restaurants to number
        return restaurants.subList(0, Math.min(restaurants.size(), number));
    }


    /**
     * This method checks whether the given RestaurantTable is free at the specified DateTimeSlot
     *
     * @param table
     * @param freeTimeSlot
     * @return True if the RestaurantTable is free on the specified DateTimeSlot, false otherwise
     */
    private boolean hasFreeTimeSlot(RestaurantTable table, DateTimeSlot freeTimeSlot) {
        List<Reservation> reservationsForSpecifiedDate = table.getReservations().stream().filter(reservation -> reservation.getDateTimeSlot().getDate().equals(freeTimeSlot.getDate())).toList();
        // detect potential collision
        for (Reservation reservation : reservationsForSpecifiedDate) {
            if (reservation.getDateTimeSlot().isCollision(freeTimeSlot)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method searches for a restaurant with the given id and return the found object
     *
     * @param id
     * @return Restaurant object with the specified id or null if the id does not exist
     */
    @Transactional
    public Restaurant retrieveRestaurant(Long id) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
        return optionalRestaurant.orElse(null);
    }


    /**
     * This method adds the given comment to the restaurant
     * Notice that the specification of the restaurant is part of the comment object itself
     *
     * @param comment must not be null
     * @return String with information about success or reason for failing
     */
    @Transactional
    public String addCommentToRestaurant(Comment comment) {
        Long restaurantId = comment.getRestaurant().getId();
        if (restaurantId == null) {
            return "ID of restaurant must not be null";
        }
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            comment.setRestaurant(restaurant);
            commentRepository.save(comment);
            restaurant.addComment(comment);
            restaurantRepository.save(restaurant);
            updateRating(restaurantId);
            updateCommentCount(restaurantId);
            return "Comment added successfully to restaurant with ID: " + restaurantId;
        } else {
            return "Restaurant doesn't exist";
        }
    }


    /**
     * This method updates the averageRating for the given restaurant, identified by its ID
     *
     * @param restaurantId must not be null
     */
    private void updateRating(Long restaurantId) {
        if (restaurantId == null) {
            return;
        }
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            double ratingSum = 0.0;
            for (Comment comment : restaurant.getComments()) {
                ratingSum += comment.getRating();
            }
            double averageRating = ratingSum / restaurant.getComments().size();
            // System.out.println("averageRating: " + averageRating + ", commentsSize: " + restaurant.getComments().size());
            restaurantRepository.updateAverageRatingById(averageRating, restaurantId);
        }
    }

    /**
     * This method updates the commentCount for the given restaurant, identified by its ID
     *
     * @param restaurantId must not be null
     */
    private void updateCommentCount(Long restaurantId) {
        if (restaurantId == null) {
            return;
        }
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            restaurantRepository.updateCommentCountById((double) restaurant.getComments().size(), restaurantId);
        }
    }

    // **************************
    // Test purpose
    // **************************

    @Transactional
    public Restaurant createRestaurant(Restaurant restaurant) {
        return addRestaurantToDatabase(restaurant);
    }

    @Transactional
    public String createRestaurants(List<Restaurant> restaurants) {
        for (Restaurant restaurant : restaurants) {
            addRestaurantToDatabase(restaurant);
        }
        return "created restaurants";
    }

    private Restaurant addRestaurantToDatabase(Restaurant restaurant) {
        if (restaurant.getId() == null) {
            for (WeekTimeSlot weekTimeSlot : restaurant.getOpeningTimes()) {
                weekTimeSlotRepository.save(weekTimeSlot);
            }
            Restaurant restaurantEntity = restaurantRepository.save(restaurant);
            for (Comment comment : restaurant.getComments()) {
                comment.setRestaurant(restaurantEntity);
                commentRepository.save(comment);
            }
            if (restaurantEntity.getLayoutId() != null) {
                String layoutPath = PATH_TO_LAYOUTS + "/layout" + restaurant.getLayoutId() + ".svg";
                if (Paths.get(layoutPath).toFile().isFile()) {
                    createTables(restaurantEntity, layoutPath);
                } else {
                    System.out.println("Layout" + restaurantEntity.getLayoutId() + " does not exist");
                }
            }
            updateRating(restaurantEntity.getId());
            return restaurantEntity;
        }
        return null;
    }

    void createTables(Restaurant restaurant, String layoutPath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(layoutPath))) {
            for (String line; (line = reader.readLine()) != null; ) {
                if (line.contains("rect")) {
                    line = line.replace("</svg>", "");
                    for (String tableString : line.split("<rect")) {
                        if (tableString.contains("#339E00")) {
                            RestaurantTable table = createTable(tableString);
                            table.setRestaurant(restaurant);
                            tableRepository.save(table);
                        }
                    }

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private RestaurantTable createTable(String tableString) {
        int tableNumber = 0;
        int capacity = 0;


        // get 0 and 8 out of string id="table_0_8" from tableString
        String[] splitted = tableString.split("_");
        String tableNumberString = splitted[1];
        tableNumber = Integer.parseInt(tableNumberString);
        String capacityString = splitted[2].split("\"")[0];
        capacity = Integer.parseInt(capacityString);

        return new RestaurantTable(tableNumber, capacity);
    }

    @Transactional
    public String updateRestaurant(Restaurant restaurant) {
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
    public String deleteAllRestaurants() {
        restaurantRepository.deleteAll();
        return "deleted restaurants";
    }

    @Transactional
    public List<Restaurant> readRestaurants() {
        return restaurantRepository.findAll();
    }

    @Transactional
    public void updateAverageRating() {
        for (Restaurant restaurant : restaurantRepository.findAll()) {
            updateRating(restaurant.getId());
        }
    }

    @Transactional
    public void updateCommentCount() {
        for (Restaurant restaurant : restaurantRepository.findAll()) {
            updateCommentCount(restaurant.getId());
        }
    }

}
