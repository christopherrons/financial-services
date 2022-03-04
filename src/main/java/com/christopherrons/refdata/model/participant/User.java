package com.christopherrons.refdata.model.participant;

public class User {

    private final String userId;
    private final String firstName;
    private final String lastName;

    public User(String firstName, String lastName) {
        this.userId = firstName + lastName;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
