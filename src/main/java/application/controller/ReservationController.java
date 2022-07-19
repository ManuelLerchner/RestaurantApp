package application.controller;

import application.model.Reservation;
import application.model.User;
import application.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    /**
     * This method returns the reservations corresponding to the user, identified by the given authentication token.
     * If no user with the given token was found, "NotFound" is returned.
     *
     * @param authToken
     * @return ResponseEntity with a list of all reservations of the given user
     */
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

    /**
     * This method saves a reservation for the user determined by his authToken, in the defined restaurant,
     * at the defined table and during the defined timeslot.
     * If any of the given parameters are invalid, "BadRequest" is returned.
     *
     * @body All necessary information: authToken, restaurantId, tableNumber, date, timeSlot
     * @return ResponseEntity with the saved reservation
     */


    @PostMapping("reserveTable")
    public ResponseEntity<Reservation> reserveTable(
            @RequestBody Map<String, Object> body
    ) {
        String authToken = body.get("authToken").toString();
        Long restaurantId = Long.getLong(body.get("restaurantId").toString());
        Long tableNumber = Long.getLong(body.get("tableNumber").toString());
        String date = body.get("date").toString();
        List<String> items = Arrays.asList(body.get("timeSlot").toString().split("\\s*,\\s*"));
        List<Double> timeSlot = items.stream().map(Double::parseDouble).toList();
        User user = reservationService.isAuthorized(authToken);

        if (user == null) {
            System.out.println("test");
            return ResponseEntity.notFound().build();
        }
        if (restaurantId == null || tableNumber == null || date == null || timeSlot == null) {
            System.out.println("wrong shit");
            return ResponseEntity.badRequest().build();
        }

        Reservation returnEntity = reservationService.reserveTable(user, restaurantId, tableNumber, date, timeSlot);
        if (returnEntity == null) {
            System.out.println("somthing wrong");
            return ResponseEntity.badRequest().build();
        }

        System.out.println(authToken);
        return ResponseEntity.ok(null);
    }

    /**
     * This method cancels and deletes the reservation with the given id from the database.
     * To do this the authentication token of the user is needed.
     * If no user with the given token or no reservation was found, "NotFound" is returned.
     * If the cancellation process did not succeed, bad request is returned.
     *
     * @param authToken
     * @param id
     * @return Empty response entity on success, NotFound for missing user or reservation, BadRequest for faulty cancellation
     */
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

    /**
     * This method confirms the reservation with the given id
     * If no reservation with this id was found, "NotFound" is returned.
     *
     * @param id
     * @return Response entity with the reservation on success, NotFound for missing reservation, Bad request for null as id
     */
    @PutMapping("confirmReservation")
    public ResponseEntity<Reservation> confirmReservation(@RequestParam Long id) {
        if(id == null) {
            return ResponseEntity.badRequest().build();
        }
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
