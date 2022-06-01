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

        public Reservation deleteReservation(int reservationId) {
                return new Reservation(); // TODO
        }

        public List<Reservation> retrieveReservationsForUser(int userId) {
                return new ArrayList<>(); // TODO
        }
}
