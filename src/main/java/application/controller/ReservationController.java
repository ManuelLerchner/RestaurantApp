package application.controller;

import application.model.Reservation;
import application.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("reserveTable")
    public ResponseEntity<Reservation> reserveTable(@RequestBody Reservation reservation) {
        // TODO add necessary parameters (table, startTime, user, ...)
        if(!isValidReservation(reservation)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(reservationService.reserveTable(reservation));
    }

    private boolean isValidReservation(Reservation reservation) {
        if (reservation.getUser() == null || reservation.getDateTimeSlot() == null || reservation.getRestaurantTable() == null) {
            return false;
        }
        return true;
    }

    @DeleteMapping("cancelReservation")
    public ResponseEntity<String> cancelReservation(@RequestParam Long id) {
        if (!reservationService.isExistingReservation(id)) {
            return ResponseEntity.notFound().build();
        }
        reservationService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("confirmReservation")
    public ResponseEntity<Reservation> confirmReservation(@RequestParam Long id) {
        if (!reservationService.isExistingReservation(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservationService.confirmReservation(id));
    }

    // **************************
    // Test purpose
    // **************************

    @RequestMapping(value = "createReservation", method = RequestMethod.POST)
    public String createReservation(@RequestBody Reservation reservation) {
        return reservationService.createReservation(reservation);
    }

    @RequestMapping(value = "readReservations", method = RequestMethod.GET)
    public List<Reservation> readReservations() {
        return reservationService.readAllReservations();
    }

    @RequestMapping(value = "deleteReservation", method = RequestMethod.DELETE)
    public String deleteReservation(@RequestParam Long id) {
        return reservationService.deleteReservation(id);
    }

    @RequestMapping(value = "readReservation", method = RequestMethod.GET)
    public ResponseEntity<Reservation> readReservation(@RequestParam Long id) {
        Reservation reservation = reservationService.readReservation(id);
        if (reservation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservation);
    }


}
