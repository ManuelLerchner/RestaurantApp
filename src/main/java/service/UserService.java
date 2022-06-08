package service;

import model.Reservation;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import repositories.RestaurantRepository;
import repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<Reservation> retrieveReservationsForUser(int userId) {
        return new ArrayList<>(); // TODO Nico
    }

    @Transactional
    public String createUser(User user) {
        try {
            if(!existsByUserName(user.getName())) { //TODO:1 decide whether name or email unique identifier
                userRepository.save(user);
                return "Restaurant saved successfully!";
            } else {
                return "Restaurant already exists in database";
            }
        } catch(Exception e) {
            throw e;
        }
    }

    public boolean isExistingUserId(int userID) {
        return true; // TODO:1 request to repository to check whether the userID exists
    }

    public boolean existsByUserName(String userName) {
        return true; // TODO:1 request to repository to check whether the userName exists
    }
}
