package com.jay.bank.controllers;

import com.jay.bank.models.Bank;
import com.jay.bank.repositories.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/banks") // good practice for controllers that return data, not webpages
public class BankController {

    @Autowired
    private BankRepository bankRepository;

    @PostMapping
    public ResponseEntity<?> addOneBankToDB(@RequestBody Bank newBankData) {
        try {
            // validation occurs here
            // save the new bank to the database
            Bank addedBank = bankRepository.save(newBankData);

            // return the new bank to the client
            // return ResponseEntity.ok(addedBank); // Status 200 Ok

            return new ResponseEntity<>(addedBank, HttpStatus.CREATED); // Status 201 Created

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage()); // 500 error code
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllBanksFromDB() {
        // get all the banks from the database
        List<Bank> allBanks = bankRepository.findAll();

        // return the list of banks to the client
        return new ResponseEntity<>(allBanks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBankById(@PathVariable Long id) {

        Optional<Bank> requestedBank = bankRepository.findById(id);

        if (requestedBank.isEmpty()) {
            return new ResponseEntity<>("Bank with id " + id + " not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(requestedBank.get(), HttpStatus.OK);
    }
}
