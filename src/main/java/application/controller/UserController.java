package application.controller;

import application.model.Comment;
import application.model.User;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


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
