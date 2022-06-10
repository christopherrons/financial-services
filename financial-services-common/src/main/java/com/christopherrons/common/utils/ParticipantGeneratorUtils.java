package com.christopherrons.common.utils;

import com.christopherrons.common.enums.ParticipantAggregationLevelEum;
import com.christopherrons.common.model.refdata.Member;
import com.christopherrons.common.model.refdata.Participant;
import com.christopherrons.common.model.refdata.User;
import com.github.javafaker.Faker;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class ParticipantGeneratorUtils {

    private static final Faker NAME_FAKER = new Faker();
    private static final Random RANDOM_UNIFORM = new Random();
    private static final List<User> USER_POOL = IntStream.of(0, 2000).mapToObj(k -> generateUser()).toList();

    public static User generateUser() {
        return new User(NAME_FAKER.funnyName().name(), NAME_FAKER.name().lastName());
    }

    public static User getUserFromPool() {
        return USER_POOL.get(RANDOM_UNIFORM.nextInt(0, USER_POOL.size()));
    }

    public static Participant createParticipant(final Participant participant, final ParticipantAggregationLevelEum level) {
        return switch (level) {
            case MEMBER_USER -> participant;
            case USER -> new Participant(new Member(""), participant.getUser());
            case MEMBER -> new Participant(participant.getMember(), new User("", ""));
        };
    }
}
