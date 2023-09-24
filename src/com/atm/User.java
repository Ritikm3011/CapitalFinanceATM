package com.atm;

import java.math.BigInteger;

/**
 *
 * @author Ritik Mondal
 */
public class User {

    private BigInteger id;      // user's 12 digit auto generated card/account number by mysql .
    private String name;        // user's name
    private int avl_bal;        // user's available balance in account
    private String address;     // user's address.
    private String phone;       // user's phone number.
    private String date;        // activation date and time of the user's card/account
    private String password;    // user's PIN/password.

    public User(BigInteger id, String name, int avl_bal, String address, String phone, String date, String password) {
        this.id = id;
        this.name = name;
        this.avl_bal = avl_bal;
        this.address = address;
        this.phone = phone;
        this.date = date;
        this.password = password;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvl_bal() {
        return avl_bal;
    }

    public void setAvl_bal(int avl_bal) {
        this.avl_bal = avl_bal;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User() {
    }

    public User(BigInteger id, String name, int avl_bal, String address, String phone, String password) {
        this.id = id;
        this.name = name;
        this.avl_bal = avl_bal;
        this.address = address;
        this.phone = phone;
        this.password = password;
    }

}
