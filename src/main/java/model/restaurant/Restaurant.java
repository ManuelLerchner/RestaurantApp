package model.restaurant;

import model.util.Location;
import model.Comment;
import model.util.WeekTimeSlot;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.annotation.processing.Generated;
import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@TypeDefs(@TypeDef(name = "week-time-slot-array", typeClass = WeekTimeSlot[].class))
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String linkToWebsite;
    private double averageRating;

    @ElementCollection
    private List<String> pictures;
    private PriceCategory priceCategory;
    private RestaurantType restaurantType;

    @Embedded
    private Location location;

    @ElementCollection
    private List<WeekTimeSlot> openingTimes;

    @ElementCollection
    private List<Comment> comments;


    public Restaurant(String name, String linkToWebsite, List<String> pictures, PriceCategory priceCategory, RestaurantType restaurantType, Location location, List<WeekTimeSlot> openingTimes, List<Comment> comments) {
        this.name = name;
        this.linkToWebsite = linkToWebsite;
        this.pictures = pictures;
        this.priceCategory = priceCategory;
        this.restaurantType = restaurantType;
        this.location = location;
        this.openingTimes = openingTimes;
        this.comments = comments;
        this.averageRating = Math.random() * 5; // TODO delete
    }

    // default constructor for testing purpose
    public Restaurant() {
        this.name = "DefaultName";
        this.linkToWebsite = "linkToWebsite";
        this.averageRating = 4;
        this.pictures = List.of("/pathToPicture1", "/pathToPicture2");
        this.priceCategory = PriceCategory.NORMAL;
        this.restaurantType = RestaurantType.ITALIAN;
        this.openingTimes = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            openingTimes.add(new WeekTimeSlot(LocalTime.of(12, 0), LocalTime.of(23, 0), DayOfWeek.of(i + 1)));
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

    public List<WeekTimeSlot> getOpeningTimes() {
        return openingTimes;
    }

    public void setOpeningTimes(List<WeekTimeSlot> openingTimes) {
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
        for (Comment comment : comments) {
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
                ", \nopeningTimes=" + openingTimes.toString() +
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
