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

    /**
     * This method adds the user to the user database by calling signUp in userService if the attributes of parameter user are not null
     * Depending on the returned List from userService.signUp() it returns ok, or if return was null, badRequest
     *
     * @param user
     * @return ResponseEntity with List of Strings if Signup successful or else BadRequest
     */
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

    /**
     * This method logs the user in by calling login in userService if the parameters email and password are not null
     * If userService.login() returns null, the return will be badRequest.build(), otherwise ok
     *
     * @param email
     * @param password
     * @return ResponseEntity with List of Strings when Login was successful or else BadRequest
     */

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

    /**
     * This method logs the user in by calling loginWithAuthToken in userService if the parameter authToken is not null
     * If userService.loginWithAuthToken returns null, the return will be badRequest.build(), otherwise ok
     *
     * @param authToken
     * @return ResponseEntity with List of Strings when Login was successful or else BadRequest
     */

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
