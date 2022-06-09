package jpa.model.restaurant;

import jpa.model.Comment;
import jpa.model.Reservation;
import jpa.model.util.Location;
import jpa.model.util.WeekTimeSlot;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Enumerated
    @Column(name = "restaurant_type")
    private RestaurantType restaurantType;



    @Enumerated
    @Column(name = "price_category")
    private PriceCategory priceCategory;

    @Column(name = "average_rating")
    private Double averageRating;

    @Column(name = "link_to_website")
    private String linkToWebsite;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "restaurant", orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "restaurant_opening_times", joinColumns = @JoinColumn(name = "owner_id"))
    private List<WeekTimeSlot> openingTimes = new ArrayList<>();

    @Embedded
    private Location location;

    @OneToMany(mappedBy = "restaurant", orphanRemoval = true)
    private List<Desk> desks = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Desk> getDesks() {
        return desks;
    }

    public void setDesks(List<Desk> desks) {
        this.desks = desks;
    }

    public Restaurant() {
    }

    public Restaurant(String name) {
        this.name = name;
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

    public void setOpeningTimes(List<WeekTimeSlot> weekTimeSlot) {
        this.openingTimes = weekTimeSlot;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
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

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
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





}