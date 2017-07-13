/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.financegeorgia.entities;

/**
 *
 * @author viplav
 */
//@Entity
public class ArmorAccount {

    private String company, user_name, user_email, user_phone, address, city, state, zip, country, email_confirmed, agreed_terms;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail_confirmed() {
        return email_confirmed;
    }

    public void setEmail_confirmed(String email_confirmed) {
        this.email_confirmed = email_confirmed;
    }

    public String getAgreed_terms() {
        return agreed_terms;
    }

    public void setAgreed_terms(String agreed_terms) {
        this.agreed_terms = agreed_terms;
    }
    
    

}
