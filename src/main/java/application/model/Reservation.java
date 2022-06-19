package application.model;

import application.model.util.DateTimeSlot;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer", "FieldHandler"})
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "date_time_slot_id")
    private DateTimeSlot dateTimeSlot;

    @JsonIgnoreProperties("reservations")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("reservations")
    @JoinColumn(name = "restaurant_table_id")
    private RestaurantTable restaurantTable;

    public RestaurantTable getRestaurantTable() {
        return restaurantTable;
    }

    public void setRestaurantTable(RestaurantTable restaurantTable) {
        this.restaurantTable = restaurantTable;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DateTimeSlot getDateTimeSlot() {
        return dateTimeSlot;
    }

    public void setDateTimeSlot(DateTimeSlot dateTimeSlot) {
        this.dateTimeSlot = dateTimeSlot;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
