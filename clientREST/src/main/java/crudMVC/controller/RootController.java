package crudMVC.controller;

import crudMVC.model.Role;
import crudMVC.model.User;
import crudMVC.model.UserTo;
import crudMVC.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class RootController {

    private final UserService userService;

    public RootController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping
//    public View test() {
//        return new RedirectView("/login");
//    }

    @GetMapping(value = "login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping(value = "hello")
    public String printWelcome(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        messages.add("User");
        model.addAttribute("messages", messages);
        return "hello";
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserTo(@PathVariable("id") int id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> userUpdate(@Valid @RequestBody UserTo userTo) {
        if (isValidate(userTo.getLogin(), userTo.getPassword(), userTo.getPassword())) {
            userService.update(userTo);
            return ResponseEntity.ok(HttpStatus.CREATED);
        }
        return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> userAdd(@Valid @RequestBody UserTo userTo) {
        if (isValidate(userTo.getLogin(), userTo.getPassword(), userTo.getPassword())) {
            userService.add(userTo);
            return ResponseEntity.ok(HttpStatus.CREATED);
        }
        return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
    }

    boolean isValidate(String s1, String s2, String s3) {
        return !s1.isEmpty() && !s2.isEmpty() && !s3.isEmpty();
    }

}
