package jpa.model;

import jpa.model.restaurant.Desk;
import jpa.model.restaurant.Restaurant;

import javax.persistence.*;

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "restaurant_table_id")
    private Desk desk;

    @Column(name = "number_of_reserved_seats")
    private Integer numberOfReservedSeats;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Integer getNumberOfReservedSeats() {
        return numberOfReservedSeats;
    }

    public void setNumberOfReservedSeats(Integer numberOfReservedSeats) {
        this.numberOfReservedSeats = numberOfReservedSeats;
    }

    public Desk getRestaurantTable() {
        return desk;
    }

    public void setRestaurantTable(Desk desk) {
        this.desk = desk;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}