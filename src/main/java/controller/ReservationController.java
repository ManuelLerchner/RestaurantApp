package controller;

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
        return ResponseEntity.ok(reservationService.retrieveReservedTimeSlots(restaurantId));
    }

    @PostMapping("reservations/reserve/{restaurantId}/{tableId}")
    public ResponseEntity reserveTable(@PathVariable Integer restaurantId, @PathVariable Integer tableId, @RequestBody DateTimeSlot dateTimeSlot) {
        return ResponseEntity.ok(reservationService.reserveTable(restaurantId, tableId, dateTimeSlot));
    }

}
