package com.jay.bank.models;

import javax.persistence.*;

@Entity
public class CheckingAccount {

    @Id() // primary key
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "accountId") // auto increment value for primary key (id) field in database table
    @SequenceGenerator(name = "accountId", initialValue = 100)
    private Long id;

    private String alias;
    private Long balance;
    private Long fee;

    public CheckingAccount() {
    }

    public CheckingAccount(String alias, Long balance, Long fee) {
        this.alias = alias;
        this.balance = balance;
        this.fee = fee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }
}
