package crudMVC.controller;

import crudMVC.service.UserService;
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
        return new RedirectView("/admin");
    }

    @GetMapping(value = "login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/admin")
    public ModelAndView admin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("users", userService.getAllUsers());
        modelAndView.setViewName("admin");
        return modelAndView;
    }

    @GetMapping(value = "hello")
    public String printWelcome(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        messages.add("User");
        model.addAttribute("messages", messages);
        return "hello";
    }
}
