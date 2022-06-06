package model.restaurant;

import model.Comment;
import model.util.Location;
import model.util.WeekTimeSlot;

import java.util.Arrays;
import java.util.List;

public class DetailedRestaurant extends SimpleRestaurant {

    private List<String> pictures;
    private WeekTimeSlot[] openingTimes;
    private List<Comment> comments;

    public DetailedRestaurant(int id, String name, String linkToWebsite, double averageRating, PriceCategory priceCategory, RestaurantType restaurantType, Location location, List<String> pictures, WeekTimeSlot[] openingTimes, List<Comment> comments) {
        super(id, name, linkToWebsite, averageRating, priceCategory, restaurantType, location);
        this.pictures = pictures;
        this.openingTimes = openingTimes;
        this.comments = comments;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
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

    @Override
    public String toString() {
        return "DetailedRestaurant{" +
                "pictures=" + pictures +
                ", openingTimes=" + Arrays.toString(openingTimes) +
                ", comments=" + comments +
                '}';
    }
}
