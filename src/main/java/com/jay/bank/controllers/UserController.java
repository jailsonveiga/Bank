package com.jay.bank.controllers;

import com.jay.bank.models.User;
import com.jay.bank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> addOneUser(@RequestBody User newUserData) {

        try {
            User addedUser = userRepository.save(newUserData);
            return new ResponseEntity<>(addedUser, HttpStatus.CREATED);

        } catch (Exception e) {

            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/token/{loginToken}")
    public ResponseEntity<?> getAllUsersByToken(@PathVariable String loginToken) {

        User foundUser = userRepository.findByLoginToken(loginToken).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with login token " + loginToken + " not found"));

        return new ResponseEntity<>(foundUser, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User authUser) {
        // Find the user in the database
        // Compare password provided with password of user account
        // Create random token and save record
        // Return login token to client

        Optional<User> foundUser = userRepository.findById(authUser.getUsername());

        if(!authUser.getPassword().equals(foundUser.get().getPassword())) {
            return new ResponseEntity<>("Invalid password", HttpStatus.BAD_REQUEST);
        }

        int randomNum = ThreadLocalRandom.current().nextInt();
        authUser.setLoginToken(Integer.toString(randomNum) + foundUser.get().getUsername());
        userRepository.save(authUser);
        return new ResponseEntity<>(authUser.getLoginToken(), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }
}
