package crudMVC.controller;

import crudMVC.model.User;
import crudMVC.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class RootController {
    private final UserService userService;

    public RootController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        messages.add("User");
        model.addAttribute("messages", messages);
        return "hello";
    }

    @RequestMapping(value = "users", method = RequestMethod.GET)
    public ModelAndView getUsers(@RequestParam(value = "action", required = false) String action, @RequestParam(value = "id", required = false) Long id) {
        if (action == null) {
            List<User> users = userService.getAllUsers();
            ModelAndView modelAndView = new ModelAndView("users");
            modelAndView.addObject("users", users);
            return modelAndView;
        } else if (action.equals("update")) {
            User user = userService.getUserById(id);
            ModelAndView modelAndView = new ModelAndView("userEdit");
            modelAndView.addObject("user", user);
            return modelAndView;
        }
        return null;
    }

    @RequestMapping(value = "users", method = RequestMethod.POST)
    public View greetingSubmit(@RequestParam("action") String action, @RequestParam(value = "id", required = false) Long id, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "surname", required = false) String surname, @RequestParam(value = "age", required = false) Integer age) {
        switch (action) {
            case "add":
                if (isValidate(name, surname, age)) {
                    userService.add(new User(name, surname, age));
                }
                break;
            case "update":
                userService.update(new User(id, name, surname, age));
                break;
            case "delete":
                userService.delete(id);
                break;
        }
        return new RedirectView("users");
    }

    boolean isValidate(String s1, String s2, int i) {
        return !s1.isEmpty() && !s2.isEmpty() && i >= 0;
    }
}
