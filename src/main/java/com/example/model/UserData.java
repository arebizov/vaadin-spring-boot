package com.example.model;

import javax.persistence.*;

 public class UserData {
    private String firstName;
    private String lastName;
    private String email;

    private UserData(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    private String getFirstName() {
        return firstName;
    }

    private void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private String getLastName() {
        return lastName;
    }

    private void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private String getEmail() {
        return email;
    }

    private void setEmail(String email) {
        this.email = email;
    }
}