package com.jay.bank.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import javax.persistence.*;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private String location;

    @ManyToOne
    @JoinColumn(name = "bank_id", referencedColumnName = "id")
    @JsonIncludeProperties("id")
    private Bank bank;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "username")
    @JsonIncludeProperties("username")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }


    public Customer() {
    }

    public Customer(String firstName, String lastName, String email, Integer age, String location) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
