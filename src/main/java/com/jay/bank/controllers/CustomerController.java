package com.jay.bank.controllers;

import com.jay.bank.models.Bank;
import com.jay.bank.models.Customer;
import com.jay.bank.repositories.BankRepository;
import com.jay.bank.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin()
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BankRepository bankRepository;


    @PostMapping("/{bankId}")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer newCustomerData , @PathVariable Long bankId) {
        // find the bank ID in the repository
        // if bank doesn't exist return bad request
        // if bank exist add to newCustomerData and save

        Bank requestBank = bankRepository.findById(bankId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bank not found"));

        newCustomerData.setBank(requestBank);

        Customer newCustomer = customerRepository.save(newCustomerData);

        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);

    }

    @GetMapping("/")
    public ResponseEntity<List<Customer>> getAllCustomers(){

        List<Customer> allCustomer = customerRepository.findAll();

        return new ResponseEntity<>(allCustomer, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteById(@PathVariable("id") Long id) {

        customerRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PutMapping("/update") //
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
        Customer updateCustomer = customerRepository.save(customer);

        return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> customerById (@PathVariable("id") Long id) {
        Optional<Customer> customer = customerRepository.findById(id);

        return new ResponseEntity(customer, HttpStatus.OK);
    }

    @GetMapping("/lastname/{lastName}")
    public ResponseEntity<List<Customer>> getLastName(@PathVariable("lastName") String lastName) {

        List<Customer> foundCustomer = customerRepository.findAllByLastName(lastName);

        return new ResponseEntity<>(foundCustomer, HttpStatus.OK);
    }


    @GetMapping("/bank/{bankId}")
    public ResponseEntity<List<Customer>> getAllByBankId(@PathVariable("bankId") Long bankId) {
        List<Customer> allCustomers = customerRepository.findAllByBank_id(bankId);

        return new ResponseEntity<>(allCustomers, HttpStatus.OK);
    }

}
