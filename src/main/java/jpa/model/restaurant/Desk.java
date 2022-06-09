package jpa.model.restaurant;

import jpa.model.util.DateTimeSlot;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "desk")
public class Desk { // Table als Name nicht möglich
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "reserved_time_slots", joinColumns = @JoinColumn(name = "owner_id"))
    private List<DateTimeSlot> reservedTimeSlots = new ArrayList<>();

    @Column(name = "number_of_seats")
    private Integer numberOfSeats;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public List<DateTimeSlot> getReservedTimeSlots() {
        return reservedTimeSlots;
    }

    public void setReservedTimeSlots(List<DateTimeSlot> reservedTimeSlots) {
        this.reservedTimeSlots = reservedTimeSlots;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



}