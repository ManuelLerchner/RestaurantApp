package application.controller;

import application.model.Reservation;
import application.model.User;
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

    @GetMapping("getReservations")
    public ResponseEntity<List<Reservation>> getReservations(
            @RequestParam(name = "authToken") String authToken // User
    ) {
        User user = reservationService.isAuthorized(authToken);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user.getReservations());
    }

    @PostMapping("reserveTable")
    public ResponseEntity<Reservation> reserveTable(
            @RequestParam(name = "authToken") String authToken, // User
            @RequestParam(name = "tableId") Long tableId, // Table & Restaurant implizit
            @RequestParam(name = "date", defaultValue = "null") String date, // Date
            @RequestParam(name = "timeSlot", defaultValue = "10.0, 24.0") List<Double> timeSlot // TimeSlot
    ) {
        User user = reservationService.isAuthorized(authToken);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        if (tableId == null || date == null || timeSlot == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(reservationService.reserveTable(user, tableId, date, timeSlot));
    }


    @DeleteMapping("cancelReservation")
    public ResponseEntity<String> cancelReservation(
            @RequestParam(name = "authToken") String authToken,
            @RequestParam Long id) {
        User user = reservationService.isAuthorized(authToken);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        if (!reservationService.isExistingReservation(id)) {
            return ResponseEntity.notFound().build();
        }
        boolean canceled = reservationService.cancelReservation(user, id);
        if (!canceled) {
            return ResponseEntity.badRequest().build();
        }
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
