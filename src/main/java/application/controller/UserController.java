package application.controller;

import application.model.Comment;
import application.model.User;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("signUp")
    public ResponseEntity<User> signUp(User user) {
        if (isValidUser(user)) {
            return ResponseEntity.ok(userService.signUp(user));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("login")
    public ResponseEntity<String> login(@RequestParam(name = "email") String email, @RequestParam(name = "password") String password) {
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(userService.login(email, password));
    }


    private boolean isValidUser(User user) {
        if (user.getId() != null) {
            return false;
        }
        if (user.getName() == null || user.getEmail() == null || user.getReservations() != null) {
            return false;
        }
        return true;
    }


    // **************************
    // Test purpose
    // **************************

    @RequestMapping(value = "createUser", method = RequestMethod.POST)
    public String createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @RequestMapping(value = "readUsers", method = RequestMethod.GET)
    public List<User> readUsers() {
        return userService.readAllUsers();
    }

    @RequestMapping(value = "updateUser", method = RequestMethod.PUT)
    public String updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @RequestMapping(value = "deleteUser", method = RequestMethod.DELETE)
    public String deleteUser(@RequestParam Long id) {
        return userService.deleteUser(id);
    }

    @RequestMapping(value = "readUser", method = RequestMethod.GET)
    public ResponseEntity<User> readUser(@RequestParam Long id) {
        User user = userService.readUser(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
}
