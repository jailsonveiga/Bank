package com.jay.bank.controllers;

import com.jay.bank.models.CheckingAccount;
import com.jay.bank.models.Customer;
import com.jay.bank.repositories.CheckingAccountRepository;
import com.jay.bank.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/api/checking")
public class CheckingAccountController {

    @Autowired
    private CheckingAccountRepository checkingAccountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/customer/{id}")
    public ResponseEntity<?> addOneCheckingAccountToDB(@RequestBody CheckingAccount newCheckingAccountData, @PathVariable Long id) {

       // find the customer in the customer database
        // return bad request if no customer
        // add the customer record to the newCheckingAccountData object
        // save it to the database
        // return the newCheckingAccountData object

        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer not found"));

        newCheckingAccountData.getCustomers().add(customer);

        CheckingAccount addedCheckingAccount  = checkingAccountRepository.save(newCheckingAccountData);



        return new ResponseEntity<>(addedCheckingAccount, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllCheckingAccounts() {
        List<CheckingAccount> allCheckingAccounts = checkingAccountRepository.findAll();

        return new ResponseEntity<>(allCheckingAccounts, HttpStatus.OK);
    }

    @GetMapping("/update/{id}")
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

    @GetMapping("/bank/{id}")
    public ResponseEntity<?> getAccountsByBankId(@PathVariable Long id) {

            Set<CheckingAccount> requestedCheckingAccounts = checkingAccountRepository.findAllByCustomers_Bank_Id(id);

            return new ResponseEntity<>(requestedCheckingAccounts, HttpStatus.OK);
    }

    @PutMapping("/addCustomer/{cid}/{aid}")
    public ResponseEntity<?> addCustomerToAccount(@PathVariable Long cid, @PathVariable Long aid) {

        // find

        CheckingAccount requestedCheckingAccount = checkingAccountRepository.findById(aid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Checking Account not found"));

        Customer requestedCustomer = customerRepository.findById(cid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        requestedCheckingAccount.getCustomers().add(requestedCustomer);

        return new ResponseEntity<>(checkingAccountRepository.save(requestedCheckingAccount), HttpStatus.OK);

    }
}
