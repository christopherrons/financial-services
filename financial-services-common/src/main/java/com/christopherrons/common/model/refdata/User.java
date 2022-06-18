package com.christopherrons.common.model.refdata;

public record User(String firstName, String lastName) {
    public String getUserId() {
        return firstName + lastName;
    }
}
