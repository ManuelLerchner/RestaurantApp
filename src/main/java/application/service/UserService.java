package application.service;

import application.model.User;
import application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public String createUser(User user) {
        if (user.getId() == null) {
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
