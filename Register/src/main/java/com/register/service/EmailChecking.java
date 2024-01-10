package com.register.service;

import com.register.entity.User;
import com.register.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class EmailChecking {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> checkEmail(User user) {
        Optional<User> user1 = userRepository.findByEmail(user.getEmail());
        return user1;
    }
}
