package controller;

import model.Reservation;
import model.util.DateTimeSlot;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import service.ReservationService;

import java.util.List;

@RestController
public class ReservationController {

    private ReservationService reservationService = new ReservationService();

    @GetMapping("reservations/reservedTimeSlots/{restaurantId}")
    public ResponseEntity<List<DateTimeSlot>> retrieveReservedTimeSlots(@PathVariable Integer restaurantId) {
        if (!reservationService.isExistingRestaurantId(restaurantId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservationService.retrieveReservedTimeSlots(restaurantId));
    }

    @PostMapping("reservations/{restaurantId}/{tableId}")
    public ResponseEntity<Reservation> reserveTable(@PathVariable Integer restaurantId, @PathVariable Integer tableId, @RequestBody DateTimeSlot dateTimeSlot) {
        if (!reservationService.isExistingRestaurantId(restaurantId) || !reservationService.isExistingTableId(tableId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservationService.reserveTable(restaurantId, tableId, dateTimeSlot));
    }

    @PutMapping("reservations/confirm/{reservationId}")
    public ResponseEntity<Reservation> confirmReservation(@PathVariable Integer reservationId) {
        if (!reservationService.isExistingReservationId(reservationId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservationService.confirmReservation(reservationId));
    }

    @DeleteMapping("reservations/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Integer reservationId) {
        if (!reservationService.isExistingReservationId(reservationId)) {
            return ResponseEntity.notFound().build();
        }
        reservationService.deleteReservation(reservationId);
        return ResponseEntity.noContent().build();
    }




}
