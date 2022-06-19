package application.controller;

import application.model.Reservation;
import application.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

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
