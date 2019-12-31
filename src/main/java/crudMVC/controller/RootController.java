package crudMVC.controller;

import crudMVC.model.Role;
import crudMVC.model.User;
import crudMVC.service.UserService;
import javafx.print.Collation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@Controller
@RequestMapping("/")
public class RootController {

    private final UserService userService;

    public RootController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public View test() {
        return new RedirectView("/users");
    }

    @GetMapping(value = "login")
    public String loginPage() {
        return "login";
    }

    @GetMapping(value = "hello")
    public String printWelcome(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        messages.add("User");
        model.addAttribute("messages", messages);
        return "hello";
    }

    @GetMapping(value = "add")
    public String getAddUser() {
        return "addUser";
    }

    @GetMapping(value = "users")
    public ModelAndView getUsers() {
        List<User> users = userService.getAllUsers();
        ModelAndView modelAndView = new ModelAndView("users");
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @GetMapping(value = "users/update/{id}")
    public ModelAndView getUserUpdate(@PathVariable("id") long userId) {
        User user = userService.getUserById(userId);
        ModelAndView modelAndView = new ModelAndView("userEdit");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping(value = "users/add")
    public View addUser(@RequestParam(value = "login") String login,
                        @RequestParam(value = "password") String password,
                        @RequestParam(value = "role") String role,
                        @RequestParam(value = "email") String email) {
        if (isValidate(login, password, email)) {
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(new Role(role));
            userService.add(new User(login, password, email,roleSet));
        }
        return new RedirectView("/users");
    }

    @PostMapping(value = "users/update/{id}")
    public View updateUser(@PathVariable("id") long userId,
                           @RequestParam(value = "login") String login,
                           @RequestParam(value = "password") String password,
                           @RequestParam(value = "role") String role,
                           @RequestParam(value = "email") String email) {
        if (isValidate(login, password, email)) {
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(new Role(role));
            userService.update(new User(userId, login, password, email, roleSet));
        }
        return new RedirectView("/users");
    }

//    @PostMapping(value = "users/update/")
//    public View updateUser(@RequestParam(value = "id") long userId,
//                           @RequestParam(value = "login") String login,
//                           @RequestParam(value = "password") String password,
//                           @RequestParam(value = "role") String role,
//                           @RequestParam(value = "email") String email) {
//        if (isValidate(login, password, email)) {
//            Set<Role> roleSet = new HashSet<>();
//            roleSet.add(new Role(role));
//            userService.update(new User(userId, login, password, email, roleSet));
//        }
//        return new RedirectView("/users");
//    }

    @PostMapping(value = "users/delete/{id}")
    public View deleteUser(@PathVariable("id") long userId) {
        userService.delete(userId);
        return new RedirectView("/users");
    }

    boolean isValidate(String s1, String s2, String s3) {
        return !s1.isEmpty() && !s2.isEmpty() && !s3.isEmpty();
    }
}
