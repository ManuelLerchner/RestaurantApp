package application.service;

import application.model.Reservation;
import application.model.RestaurantTable;
import application.model.User;
import application.model.util.DateTimeSlot;
import application.repository.DateTimeSlotRepository;
import application.repository.ReservationRepository;
import application.repository.RestaurantTableRepository;
import application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RestaurantTableRepository tableRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DateTimeSlotRepository dateTimeSlotRepository;


    @Transactional
    public User isAuthorized(String authToken) {
        return userRepository.findByAuthToken(authToken);
    }

    @Transactional
    public Reservation reserveTable(User user, long tableId, String date, List<Double> timeSlot) {
        Reservation reservation = new Reservation();
        RestaurantTable table = tableRepository.getById(tableId);
        if (table == null) {
            return null;
        }
        reservation.setRestaurantTable(table);
        DateTimeSlot dateTimeSlot = DateTimeSlot.convertToDateTimeSlot(date, timeSlot.get(0), timeSlot.get(1));
        if (dateTimeSlot == null) {
            return null;
        }
        reservation.setDateTimeSlot(dateTimeSlot);
        reservation.setUser(user);
        reservation.setConfirmed(false);
        if (!hasFreeTimeSlot(table, dateTimeSlot)) {
            return null;
        }
        return reservationRepository.save(reservation);
    }

    private boolean hasFreeTimeSlot(RestaurantTable table, DateTimeSlot freeTimeSlot) {
        List<Reservation> reservationsForSpecifiedDate = table.getReservations().stream().filter(reservation -> reservation.getDateTimeSlot().getDate().equals(freeTimeSlot.getDate())).toList();
        // detect potential collision
        for (Reservation reservation : reservationsForSpecifiedDate) {
            if (reservation.getDateTimeSlot().isCollision(freeTimeSlot)) {
                return false;
            }
        }
        return true;
    }

    @Transactional
    public Reservation confirmReservation(Long id) {
        // TODO
        // probably add attribute "confirmed" to reservation
        return null;
    }

    @Transactional
    public boolean cancelReservation(User user, Long id) {
        Reservation reservation = reservationRepository.getById(id);
        if (!user.getReservations().contains(reservation)) {
            return false;
        }
        if (reservation.getConfirmed()) {
            return false;
        }
        LocalDateTime currentTime = LocalDateTime.now();
        LocalTime reservationStartTime = reservation.getDateTimeSlot().getStartTime();
        LocalDate reservationDate = reservation.getDateTimeSlot().getDate();
        if (currentTime.plusHours(12).compareTo(LocalDateTime.of(reservationDate, reservationStartTime)) > 0) {
            return false;
        }
        reservationRepository.deleteById(id);
        return true;
    }

    @Transactional
    public boolean isExistingReservation(Long id) {
        return reservationRepository.existsById(id);
    }

    // **************************
    // Test purpose
    // **************************

    @Transactional
    public String createReservation(Reservation reservation) {
        if (reservation.getId() == null) {
            dateTimeSlotRepository.save(reservation.getDateTimeSlot());
            reservationRepository.save(reservation);
            return "Reservation created successfully";
        }
        return "Reservation exists already";
    }

    @Transactional
    public String deleteReservation(Long id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.delete(reservationRepository.getById(id));
            return "Reservation deleted successfully";
        }
        return "Reservation does not exist";
    }

    @Transactional
    public List<Reservation> readAllReservations() {
        return reservationRepository.findAll();
    }

    @Transactional
    public Reservation readReservation(Long id) {
        if (reservationRepository.existsById(id)) {
            return reservationRepository.getById(id);
        }
        return null;
    }
}
