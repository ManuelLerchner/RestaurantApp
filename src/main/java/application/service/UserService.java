package application.service;

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

    @Transactional
    public List<String> loginWithAuthToken(String authToken) {
        User user = userRepository.findByAuthToken(authToken);
        if (user == null) {
            return null;
        }

        return List.of(user.getEmail(), user.getUsername());
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
