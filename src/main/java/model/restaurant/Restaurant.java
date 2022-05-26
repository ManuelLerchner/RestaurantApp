package model.restaurant;

import model.util.Location;
import model.Comment;
import model.util.WeekTimeSlot;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Restaurant {
    private int id;
    private static int idCounter = 0;
    private String name;
    private String linkToWebsite;
    private double averageRating;
    private List<String> pictures;

    private PriceCategory priceCategory;
    private RestaurantType restaurantType;
    private Location location;

    private WeekTimeSlot[] openingTimes;
    private List<Comment> comments;


    public Restaurant(String name, String linkToWebsite, List<String> pictures, PriceCategory priceCategory, RestaurantType restaurantType, Location location, WeekTimeSlot[] openingTimes, List<Comment> comments) {
        this.id = idCounter++;
        this.name = name;
        this.linkToWebsite = linkToWebsite;
        this.pictures = pictures;
        this.priceCategory = priceCategory;
        this.restaurantType = restaurantType;
        this.location = location;
        this.openingTimes = openingTimes;
        this.comments = comments;
    }

    // default constructor for testing purpose
    public Restaurant() {
        this.id = idCounter++;
        this.name = "DefaultName";
        this.linkToWebsite = "linkToWebsite";
        this.averageRating = 4;
        this.pictures = List.of("/pathToPicture1", "/pathToPicture2");
        this.priceCategory = PriceCategory.NORMAL;
        this.restaurantType = RestaurantType.ITALIAN;
        this.openingTimes = new WeekTimeSlot[7];
        for(int i = 0; i < openingTimes.length; i++){
            openingTimes[i] = new WeekTimeSlot(LocalTime.of(12, 00), LocalTime.of(23,00), DayOfWeek.of(i+1));
        }
        this.comments = new ArrayList<>();
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

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
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

    public WeekTimeSlot[] getOpeningTimes() {
        return openingTimes;
    }

    public void setOpeningTimes(WeekTimeSlot[] openingTimes) {
        this.openingTimes = openingTimes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        updateRating();
    }

    private void updateRating() {
        int sum = 0;
        for(Comment comment : comments) {
            sum += comment.getRating();
        }
        this.averageRating = (double) sum / comments.size();
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "\nname='" + name + '\'' +
                ", \nlinkToWebsite='" + linkToWebsite + '\'' +
                ", \naverageRating=" + averageRating +
                ", \npictures=" + pictures +
                ", \npriceCategory=" + priceCategory +
                ", \nrestaurantType=" + restaurantType +
                ", \nlocation=" + location +
                ", \nopeningTimes=" + Arrays.toString(openingTimes) +
                ", \ncomments=" + comments +
                "\n}";
    }

    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant();
        Comment com1 = new Comment();
        Comment com2 = new Comment();
        Comment com3 = new Comment();
        com1.setRating(2);
        com2.setRating(5);
        com3.setRating(4);
        restaurant.addComment(com1);
        restaurant.addComment(com2);
        restaurant.addComment(com3);

        System.out.println(restaurant);
    }
}
