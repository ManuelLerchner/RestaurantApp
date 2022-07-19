package application.model;

import java.util.List;

public class ReservationInformation{
    public String authToken;
    public Long restaurantId;
    public Long tableNumber;

    public String date;
    public List<Double> timeSlot;

    public ReservationInformation() {

    }
    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Long tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Double> getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(List<Double> timeSlot) {
        this.timeSlot = timeSlot;
    }

    @Override
    public String toString() {
        return "ReservationInformation{" +
                "authToken='" + authToken + '\'' +
                ", restaurantId=" + restaurantId +
                ", tableNumber=" + tableNumber +
                ", date='" + date + '\'' +
                ", timeSlot=" + timeSlot +
                '}';
    }
}