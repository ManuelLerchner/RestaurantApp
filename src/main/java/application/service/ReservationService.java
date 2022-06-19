package application.service;

import application.model.Reservation;
import application.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Transactional
    public Reservation reserveTable() {
        // TODO
        return null;
    }

    @Transactional
    public Reservation confirmReservation(Long id) {
        // TODO
        // probably add attribute "confirmed" to reservation
        return null;
    }

    @Transactional
    public void cancelReservation(Long id) {
        reservationRepository.deleteById(id);
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
