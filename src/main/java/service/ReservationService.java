package service;

import model.Reservation;
import model.util.DateTimeSlot;

import java.util.ArrayList;
import java.util.List;

public class ReservationService {

        public List<DateTimeSlot> retrieveReservedTimeSlots(int restaurantId) {
                return new ArrayList<>(); // TODO
        }

        public Reservation reserveTable(int restaurantId, int tableId, DateTimeSlot dateTimeSlot) {
                return new Reservation(); // TODO
        }

        public Reservation confirmReservation(int reservationId) {
                return new Reservation(); // TODO
        }

        public void deleteReservation(int reservationId) {
                // TODO delete
        }

        public boolean isExistingReservationId(int reservationId) {
                return true; // TODO request to repository to check whether the reservationId exists
        }

        public boolean isExistingRestaurantId(int restaurantId) {
                return true; // TODO request to repository to check whether the restaurantId exists
        }

        public boolean isExistingTableId(int tableId) {
                return true; // TODO request to repository to check whether the tableId exists
        }

}
