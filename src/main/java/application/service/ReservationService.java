package application.service;

import application.model.Reservation;
import application.model.Restaurant;
import application.model.RestaurantTable;
import application.model.User;
import application.model.util.DateTimeSlot;
import application.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

@Service
public class ReservationService {

    private static final String EMAIL_OUTLOOK = "four0food@outlook.com";

    private static final String EMAIL_PWD = "F0F2022+";

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RestaurantTableRepository tableRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DateTimeSlotRepository dateTimeSlotRepository;


    @Transactional
    public User isAuthorized(String authToken) {
        return userRepository.findByAuthToken(authToken);
    }

    /**
     * Reserves a table with the given information about user, restaurant, etc.
     * @param user
     * @param restaurantId
     * @param tableNumber
     * @param date
     * @param timeSlot
     * @return the Reservation if successful or null else
     */
    @Transactional
    public Reservation reserveTable(User user, long restaurantId, long tableNumber, String date, List<Double> timeSlot) {
        Reservation reservation = new Reservation();

        if (!restaurantRepository.existsById(restaurantId)) {
            System.out.println("restaurant does not exist");
            return null;
        }
        Restaurant restaurant = restaurantRepository.getById(restaurantId);

        Optional<RestaurantTable> tableObject = restaurant.getRestaurantTables().stream().filter(t -> t.getTableNumber() == tableNumber).findFirst();
        if (tableObject.isEmpty()) {
            return null;
        }

        RestaurantTable table = tableRepository.getById(tableObject.get().getId());
        if (table == null) {
            return null;
        }
        reservation.setRestaurantTable(table);
        DateTimeSlot dateTimeSlot = DateTimeSlot.convertToDateTimeSlot(date, timeSlot.get(0), timeSlot.get(1));
        if (dateTimeSlot == null) {
            return null;
        }
        dateTimeSlotRepository.save(dateTimeSlot);
        reservation.setDateTimeSlot(dateTimeSlot);
        reservation.setUser(user);
        reservation.setConfirmed(false);
        if (!dateTimeSlot.isContainedInOpeningTimes(restaurant) || !hasFreeTimeSlot(table, dateTimeSlot)) {
            return null;
        }
        return reservationRepository.save(reservation);
    }


    /**
     * Checks if the given table has a free timeslot
     * @param table
     * @param freeTimeSlot
     * @return boolean if the timeslot is free on the given table
     */
    private boolean hasFreeTimeSlot(RestaurantTable table, DateTimeSlot freeTimeSlot) {
        List<Reservation> reservationsForSpecifiedDate = table.getReservations().stream().filter(reservation -> reservation.getDateTimeSlot().getDate().equals(freeTimeSlot.getDate())).toList();
        // detect potential collision
        for (Reservation reservation : reservationsForSpecifiedDate) {
            if (freeTimeSlot.isContainedInOpeningTimes(table.getRestaurant()) && reservation.getDateTimeSlot().isCollision(freeTimeSlot)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method checks if a reservation with the given id exists and sets its status to confirmed if all conditions are met
     *
     * @param id
     * @return Confirmed reservation or null if something went wrong
     */
    @Transactional
    public Reservation confirmReservation(Long id) {
        if(!reservationRepository.existsById(id)) {
            return null;
        }
        Reservation reservation = reservationRepository.getById(id);
        LocalDateTime currentTime = LocalDateTime.now();
        LocalTime reservationStartTime = reservation.getDateTimeSlot().getStartTime();
        LocalDate reservationDate = reservation.getDateTimeSlot().getDate();
        if (currentTime.plusHours(12).compareTo(LocalDateTime.of(reservationDate, reservationStartTime)) > 0) {
            return null;
        }
        reservationRepository.updateConfirmedById(true, id);
        return reservationRepository.getById(id);
    }

    /**
     * This method checks if a reservation with the given id exists and deletes it if all conditions are met
     *
     * @param id
     * @return Boolean if the cancel was successful
     */
    @Transactional
    public boolean cancelReservation(Long id) {
        if(!reservationRepository.existsById(id)) {
            return false;
        }
        Reservation reservation = reservationRepository.getById(id);
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

    /**
     * Repeated task, sends a reminding email to every user with an upcoming reservation
     * Repeats every day at 0 o'clock
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void reminder() {
        List<Reservation> reservations = reservationRepository.findAll();
        LocalDate currentDate = LocalDate.now();
        for (Reservation reservation : reservations) {
            LocalDate reservationDate = reservation.getDateTimeSlot().getDate();
            if (currentDate.plusDays(1).equals(reservationDate)) {
                String body = String.format("""
                        Please confirm your reservation!
                        
                        How to confirm?
                        Visit our website, login and confirm at "My Reservations"
                        
                        Your reservation:
                        Restaurant: %s
                        Date and Time: %s
                        maximum number of people: %d
                        
                        You must confirm the reservation at least 12 hours before the appointment, 
                        otherwise it will be canceled
                        
                        We wish you a great meal!
                        
                        """, reservation.getRestaurantTable().getRestaurant().getName(), reservation.getDateTimeSlot().emailRepresentation(), reservation.getRestaurantTable().getCapacity());
                sendMail(new String[]{reservation.getUser().getEmail()}, body);
            }
        }
    }

    // based on https://stackoverflow.com/questions/46663/how-can-i-send-an-email-by-java-application-using-gmail-yahoo-or-hotmail

    /**
     *  @param to String-list with recipients
     *  @param body Content of the mail
     */
    private void sendMail(String[] to, String body) {
        String from = EMAIL_OUTLOOK;
        String pass = EMAIL_PWD;
        String subject = "Confirm your Reservation";

        Properties props = System.getProperties();
        String host = "smtp.office365.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", "smtp.office365.com");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for (int i = 0; i < to.length; i++) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for (int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (AddressException ae) {
            ae.printStackTrace();
        } catch (MessagingException me) {
            me.printStackTrace();
        }
    }

    /**
     * This method is a repeating task that deletes all not confirmed reservations with less than 12 hours left until the
     * start-time arrives from the repository.
     * Repeats every 15 minutes
    */
    @Scheduled(cron = "0 */15 * * * *")
    public void cancelNotConfirmedReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        LocalDateTime currentTime = LocalDateTime.now();
        for (Reservation reservation : reservations) {
            LocalTime reservationStartTime = reservation.getDateTimeSlot().getStartTime();
            LocalDate reservationDate = reservation.getDateTimeSlot().getDate();
            if (currentTime.plusHours(12).compareTo(LocalDateTime.of(reservationDate, reservationStartTime)) > 0 && !reservation.getConfirmed()) {
                reservationRepository.deleteById(reservation.getId());
            }
        }
    }

    // **************************
    // Test purpose
    // **************************

    @Transactional
    public String createReservation(Reservation reservation) {
        if (reservation.getId() == null) {
            dateTimeSlotRepository.save(reservation.getDateTimeSlot());
            reservationRepository.save(reservation);
            userRepository.save(reservation.getUser());
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
