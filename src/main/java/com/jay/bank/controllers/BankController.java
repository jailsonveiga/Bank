package com.jay.bank.controllers;

import com.jay.bank.models.Bank;
import com.jay.bank.repositories.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @PostMapping("/{id}")
    public ResponseEntity<?> postOneById(@PathVariable Long id, @RequestBody Bank newBankData) {

        Bank requestedBank = bankRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bank with id " + id + " not found"));

        if(!newBankData.getName().equals("")) {

            requestedBank.setName(newBankData.getName());

        }


        if(newBankData.getPhoneNumber() != null && newBankData.getPhoneNumber().length() >= 3) { // if the new bank data has a phone number and it's not empty

            requestedBank.setPhoneNumber(newBankData.getPhoneNumber());

        }

        return ResponseEntity.ok(bankRepository.save(requestedBank));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOneById(@PathVariable Long id) {

        // If return is unwanted, the bellow line is negligible - deleteById only fails is void is provided and that cannot be due to path
        Bank requestedBank = bankRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bank with id " + id + " not found"));

        bankRepository.deleteById(id);

        return ResponseEntity.ok(requestedBank);

    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> findOneByName(@PathVariable String name) {

            Bank requestedBank = bankRepository.findByName(name).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bank with name " + name + " not found")
            );

            return new ResponseEntity<>(requestedBank, HttpStatus.OK);

    }

    @GetMapping("/areacode/{areacode}")
    public ResponseEntity<?> findAllByAreaCode(@PathVariable String areacode) {

        return new ResponseEntity<>(bankRepository.getAllAreaCodes(areacode), HttpStatus.OK);

    }
}
