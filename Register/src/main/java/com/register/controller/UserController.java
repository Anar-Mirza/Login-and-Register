package com.register.controller;

import com.register.entity.User;
import com.register.service.CheckSession;
import com.register.service.EmailChecking;
import com.register.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private EmailChecking emailChecking;
    @Autowired
    private CheckSession checkSession;

    @GetMapping("/register")
    public String registrationPage(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/register")
    public String register(User user) {
        Optional<User> user1 = emailChecking.checkEmail(user);
        if (user1.isPresent()) {
            return "redirect:register?same";
        }
        userService.save(user);
        return "redirect:register?success";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "/login";
    }

    @PostMapping("/login")
    public String loginToWebsite(@RequestParam String email, @RequestParam String password, Model model, HttpServletRequest httpServletRequest) {
        Optional<User> user = userService.login(email, password);
        if (!user.isPresent()) {
            model.addAttribute("error", "There isn't this account");
            return "login";

        }
        httpServletRequest.getSession().setAttribute("User", user.get());
        return "welcome";

    }

    @GetMapping("/welcome")
    public String welcome(HttpServletRequest httpServletRequest) {
        User user = checkSession.sessionCheck(httpServletRequest);
        if (user == null) {
            return "login";
        }
        return "welcome";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest, Model model) {
        httpServletRequest.getSession().invalidate();
        model.addAttribute("logout", "You are successfully logout..");
        return "login";
    }

}

