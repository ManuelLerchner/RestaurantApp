package application.controller;

import application.model.Reservation;
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
    public ResponseEntity<List<String>> signUp(@RequestBody User user) {
        if (user.getEmail() == null || user.getPassword() == null || user.getUsername() == null) {
            return ResponseEntity.status(401).build();
        }

        List<String> returnEntity = userService.signUp(user.getEmail(), user.getPassword(), user.getUsername());
        if (returnEntity != null) {
            return ResponseEntity.ok(returnEntity);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("login")
    public ResponseEntity<List<String>> login(@RequestParam(name = "email") String email, @RequestParam(name = "password") String password) {
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            return ResponseEntity.status(401).build();
        }
        List<String> returnEntity = userService.login(email, password);
        if (returnEntity == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(returnEntity);
    }

    @GetMapping("loginWithAuthToken")
    public ResponseEntity<List<String>> loginWithAuthToken(@RequestParam(name = "authtoken") String authToken) {
        if (authToken == null) {
            return ResponseEntity.status(401).build();
        }
        List<String> returnEntity = userService.loginWithAuthToken(authToken);
        if (returnEntity == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(returnEntity);
    }

    @GetMapping("reservations")
    public ResponseEntity<List<Reservation>> retrieveReservations(@RequestParam(name = "authtoken") String authToken) {
        if (authToken == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(userService.retrieveReservations(authToken));
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
