package com.christopherrons.refdata.participant.utils;

import com.christopherrons.refdata.participant.model.User;
import com.github.javafaker.Faker;

public class UserGeneratorUtils {

    private static final Faker NAME_FAKER = new Faker();

    public static User generateUser() {
        return new User(NAME_FAKER.funnyName().name(), NAME_FAKER.name().lastName());
    }
}
