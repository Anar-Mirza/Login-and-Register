package com.register.service;

import com.register.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class CheckSession {
    public User sessionCheck(HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getSession().getAttribute("User");
        if (user == null) {
            return null;
        }
        return user;
    }

}
