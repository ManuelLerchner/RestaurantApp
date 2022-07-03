package application.service;

import application.model.Reservation;
import application.model.User;
import application.repository.UserRepository;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    /**
     * This method checks whether a user with the given email already exists, and if not creates a new user with the passed on email, username and password
     * Notice that the password is set by using a chosen hashFunction to make it more secure
     * After creating the user, the user is saved in the database with calling userRepository.save
     *
     * @param email
     * @param password
     * @param userName
     * @return String-List of the saved user's email, password, userName if no user with same email already existed, otherwise null
     */

    @Transactional
    public List<String> signUp(String email, String password, String userName) {
        if (userRepository.findByEmail(email) == null) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setUsername(userName);
            newUser.setPassword(Hashing.sha256().hashString(email + password, StandardCharsets.UTF_8).toString());
            userRepository.save(newUser);
            return List.of(email, password, userName);
        }
        return null;
    }

    /**
     * This method checks whether a user with the given email exists, and whether the password corresponds to this user's password
     * When both is true, a token is being created and set for this user after creating this token as a random string in a while-loop
     *
     * @param email
     * @param password
     * @return If user exists and password is right returns String-List of the users email, username and created AuthToken, otherwise null
     */

    @Transactional
    public List<String> login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return null;
        }
        String loginHash = Hashing.sha256().hashString(user.getEmail() + password, StandardCharsets.UTF_8).toString();
        String userHash = user.getPassword();
        if (loginHash.equals(userHash)) {
            Random random = new Random();
            StringBuffer sb = new StringBuffer();
            while (sb.length() < 50) {
                sb.append(Integer.toHexString(random.nextInt()));
            }
            String token = sb.toString();
            user.setAuthToken(token);
            return List.of(email, user.getUsername(), token);
        }
        return null;
    }

    /**
     * This method finds the user to a given authToken
     *
     * @param authToken
     * @return If a user to the authToken exists, returns String-List of the users email, username and the AuthToken, otherwise null
     */

    @Transactional
    public List<String> loginWithAuthToken(String authToken) {
        User user = userRepository.findByAuthToken(authToken);
        if (user == null) {
            return null;
        }

        return List.of(user.getEmail(), user.getUsername(), authToken);
    }

    /**
     * This method finds the user to a given authToken
     *
     * @param authToken
     * @return If a user to the authToken exists, returns list of reservations of this user, otherwise null
     */

    @Transactional
    public List<Reservation> retrieveReservations(String authToken) {
        User user = userRepository.findByAuthToken(authToken);
        if (user == null) {
            return null;
        }
        return user.getReservations();
    }


    // **************************
    // Test purpose
    // **************************

    @Transactional
    public String createUser(User user) {
        if (user.getId() == null) {
            user.setPassword(Hashing.sha256().hashString(user.getUsername() + user.getPassword(), StandardCharsets.UTF_8).toString());
            userRepository.save(user);
            return "User created successfully";
        }
        return "User exists already";
    }

    @Transactional
    public String deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.delete(userRepository.getById(id));
            return "User deleted successfully";
        }
        return "User does not exist";
    }

    @Transactional
    public String updateUser(User updatedUser) {
        if (userRepository.existsById(updatedUser.getId())) {
            userRepository.save(updatedUser);
            return "User updated successfully";
        }
        return "User does not exist and cannot be updated";
    }

    @Transactional
    public List<User> readAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User readUser(Long id) {
        if (userRepository.existsById(id)) {
            return userRepository.getById(id);
        }
        return null;
    }

}
