package application.model;

import application.model.enums.PriceCategory;
import application.model.enums.RestaurantType;
import application.model.util.Location;
import application.model.util.WeekTimeSlot;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer", "FieldHandler"})
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

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



    @JsonIgnoreProperties("restaurant")
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Embedded
    private Location location;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "restaurant_id")
    private List<WeekTimeSlot> openingTimes = new ArrayList<>();

    @ElementCollection
    @Column(name = "picture")
    @CollectionTable(name = "restaurant_pictures", joinColumns = @JoinColumn(name = "owner_id"))
    private List<String> pictures = new ArrayList<>();

    @Column(name = "distance_to_user")
    private Double distanceToUser;

    @JsonIgnoreProperties("restaurant")
    @OneToMany(mappedBy = "restaurant", orphanRemoval = true)
    private List<RestaurantTable> restaurantTables = new ArrayList<>();

    @Column(name = "layout_id")
    private Integer layoutId;

    @Column(name = "comment_count")
    private Double commentCount;

    public Double getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Double commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(Integer layoutId) {
        this.layoutId = layoutId;
    }

    public List<RestaurantTable> getRestaurantTables() {
        return restaurantTables;
    }

    public void setRestaurantTables(List<RestaurantTable> restaurantTables) {
        this.restaurantTables = restaurantTables;
    }

    public Double getDistanceToUser() {
        return distanceToUser;
    }


    public void setDistanceToUser(Double distanceToUser) {
        this.distanceToUser = distanceToUser;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }


    public List<WeekTimeSlot> getOpeningTimes() {
        return openingTimes;
    }

    public void setOpeningTimes(List<WeekTimeSlot> openingTimes) {
        this.openingTimes = openingTimes;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
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

    public void addComment(Comment comment) {
        comments.add(comment);
    }
    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
