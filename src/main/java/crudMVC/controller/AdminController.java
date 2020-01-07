package crudMVC.controller;

import crudMVC.model.Role;
import crudMVC.model.User;
import crudMVC.model.UserTo;
import crudMVC.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserTo(@PathVariable("id") int id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }


    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> userAdd(@Valid @RequestBody UserTo userTo) {
        if (isValidate(userTo.getLogin(), userTo.getPassword(), userTo.getPassword())) {
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(new Role(userTo.getRoles()));
            User user = new User(userTo.getLogin(), userTo.getPassword(), userTo.getEmail(), roleSet);
            userService.add(user);
            return ResponseEntity.ok(HttpStatus.CREATED);
        }
        return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> userUpdate(@Valid @RequestBody UserTo userTo) {
        if (isValidate(userTo.getLogin(), userTo.getPassword(), userTo.getPassword())) {
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(new Role(userTo.getRoles()));
            User user = new User(userTo.getId(), userTo.getLogin(), userTo.getPassword(), userTo.getEmail(), roleSet);
            userService.update(user);
            return ResponseEntity.ok(HttpStatus.CREATED);
        }
        return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
    }

    boolean isValidate(String s1, String s2, String s3) {
        return !s1.isEmpty() && !s2.isEmpty() && !s3.isEmpty();
    }
}
