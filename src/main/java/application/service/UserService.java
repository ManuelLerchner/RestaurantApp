package application.service;

import application.model.User;
import application.repository.UserRepository;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public String createUser(User user) {
        if (user.getId() == null) {
            user.setHashedPassword(Hashing.sha256().hashString(user.getName() + user.getHashedPassword(), StandardCharsets.UTF_8).toString());
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
