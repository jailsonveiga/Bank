package com.jay.bank.controllers;

import com.jay.bank.models.CheckingAccount;
import com.jay.bank.repositories.CheckingAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/checking")
public class CheckingAccountController {

    @Autowired
    private CheckingAccountRepository checkingAccountRepository;

    @PostMapping
    public ResponseEntity<?> addOneCheckingAccountToDB(@RequestBody CheckingAccount newCheckingAccountData){

        CheckingAccount addedCheckingAccount  = checkingAccountRepository.save(newCheckingAccountData);

        return new ResponseEntity<>(addedCheckingAccount, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllCheckingAccounts() {
        List<CheckingAccount> allCheckingAccounts = checkingAccountRepository.findAll();

        return new ResponseEntity<>(allCheckingAccounts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCheckingAccountById(@PathVariable Long id) {

        CheckingAccount requestedCheckingAccount = checkingAccountRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Checking Account not found"));

        return new ResponseEntity<>(requestedCheckingAccount, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateCheckingAccountById(@PathVariable Long id, @RequestBody CheckingAccount newCheckingAccountData) {

        CheckingAccount requestedCheckingAccount = checkingAccountRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Checking Account not found"));

        if(!newCheckingAccountData.getAlias().equals("")){
            requestedCheckingAccount.setAlias(newCheckingAccountData.getAlias());
        }

        return new ResponseEntity<>(checkingAccountRepository.save(requestedCheckingAccount), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCheckingAccountById(@PathVariable Long id) {

        CheckingAccount requestedCheckingAccount = checkingAccountRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Checking Account not found"));

        checkingAccountRepository.delete(requestedCheckingAccount);

        return ResponseEntity.ok(requestedCheckingAccount);

    }
}
