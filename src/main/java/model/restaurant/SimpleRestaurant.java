package model.restaurant;

import model.util.Location;

public class SimpleRestaurant {

    private int id;
    private String name;
    private String linkToWebsite;
    private double averageRating;
    private PriceCategory priceCategory;
    private RestaurantType restaurantType;
    private Location location;

    public SimpleRestaurant(int id, String name, String linkToWebsite, double averageRating, PriceCategory priceCategory, RestaurantType restaurantType, Location location) {
        this.id = id;
        this.name = name;
        this.linkToWebsite = linkToWebsite;
        this.averageRating = averageRating;
        this.priceCategory = priceCategory;
        this.restaurantType = restaurantType;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinkToWebsite() {
        return linkToWebsite;
    }

    public void setLinkToWebsite(String linkToWebsite) {
        this.linkToWebsite = linkToWebsite;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public PriceCategory getPriceCategory() {
        return priceCategory;
    }

    public void setPriceCategory(PriceCategory priceCategory) {
        this.priceCategory = priceCategory;
    }

    public RestaurantType getRestaurantType() {
        return restaurantType;
    }

    public void setRestaurantType(RestaurantType restaurantType) {
        this.restaurantType = restaurantType;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "SimpleRestaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", linkToWebsite='" + linkToWebsite + '\'' +
                ", averageRating=" + averageRating +
                ", priceCategory=" + priceCategory +
                ", restaurantType=" + restaurantType +
                ", location=" + location +
                '}';
    }
}
