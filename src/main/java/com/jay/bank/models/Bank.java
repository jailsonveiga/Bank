package com.jay.bank.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import javax.persistence.*;
import java.util.List;


@Entity
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String name;
    private String location;
    private String phoneNumber;

    @OneToMany(mappedBy = "bank", fetch = FetchType.LAZY)
    @JsonIncludeProperties({"firstName", "lastName", "id"})
    @JsonIgnoreProperties({"email", "age", "location", "bank"})
    private List<Customer> customers;

    // default constructor for JPA to use when creating an instance of this class from a database record (row)
    public Bank() {
    }

    public Bank(String name, String location, String phoneNumber) {
        this.name = name;
        this.location = location;
        this.phoneNumber = phoneNumber;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAreaCode() {
        return phoneNumber.substring(0, 3);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
