package controller;

import model.util.DateTimeSlot;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import service.ReservationService;

import java.util.List;

@RestController
public class ReservationController {

    private ReservationService reservationService;

    @GetMapping("reservations/reservedTimeSlots/{restaurantId}")
    public ResponseEntity<List<DateTimeSlot>> retrieveReservedTimeSlots(@PathVariable Integer restaurantId) {

        return ResponseEntity.ok(reservationService.retrieveReservedTimeSlots(restaurantId));
    }

}
