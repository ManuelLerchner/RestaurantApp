package model;

import model.restaurant.Restaurant;

import java.time.LocalDate;

public class Comment {

    private int id;
    private LocalDate date;
    private String headline;
    private String text;
    private int rating;
    private Restaurant restaurant;
    private User author;

    public Comment(LocalDate date, String headline, String text, int rating, Restaurant restaurant, User author) {
        this.date = date;
        this.headline = headline;
        this.text = text;
        this.rating = rating;
        this.restaurant = restaurant;
        this.author = author;
    }

    // default constructor for testing purpose
    public Comment() {
        this.date = LocalDate.of(2000,1,1);
        this.headline = "headline";
        this.text = "text";
        this.rating = 3;
        this.restaurant = new Restaurant();
        this.author = new User();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
