package application.model;

import application.model.enums.PriceCategory;
import application.model.enums.RestaurantType;
import application.model.util.Location;

import java.util.ArrayList;
import java.util.List;

public class RestaurantSimple {
    private Long id;
    private String name;
    private RestaurantType restaurantType;
    private PriceCategory priceCategory;
    private Double averageRating;
    private Location location;
    private Double distanceToUser;
    private Double commentCount;

    public RestaurantSimple(Long id, String name, RestaurantType restaurantType, PriceCategory priceCategory, Double averageRating, Location location, Double distanceToUser, Double commentCount) {
        this.id = id;
        this.name = name;
        this.restaurantType = restaurantType;
        this.priceCategory = priceCategory;
        this.averageRating = averageRating;
        this.location = location;
        this.distanceToUser = distanceToUser;
        this.commentCount = commentCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RestaurantType getRestaurantType() {
        return restaurantType;
    }

    public void setRestaurantType(RestaurantType restaurantType) {
        this.restaurantType = restaurantType;
    }

    public PriceCategory getPriceCategory() {
        return priceCategory;
    }

    public void setPriceCategory(PriceCategory priceCategory) {
        this.priceCategory = priceCategory;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Double getDistanceToUser() {
        return distanceToUser;
    }

    public void setDistanceToUser(Double distanceToUser) {
        this.distanceToUser = distanceToUser;
    }

    public Double getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Double commentCount) {
        this.commentCount = commentCount;
    }

    public static RestaurantSimple restaurantToSimple(Restaurant r) {
        return new RestaurantSimple(
                r.getId(),
                r.getName(),
                r.getRestaurantType(),
                r.getPriceCategory(),
                r.getAverageRating(),
                r.getLocation(),
                r.getDistanceToUser(),
                r.getCommentCount()
        );
    }
}
