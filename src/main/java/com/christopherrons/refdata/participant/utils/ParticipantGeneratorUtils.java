package com.christopherrons.refdata.participant.utils;

import com.christopherrons.refdata.participant.enums.ParticipantAggregationLevelEum;
import com.christopherrons.refdata.participant.model.Member;
import com.christopherrons.refdata.participant.model.Participant;
import com.christopherrons.refdata.participant.model.User;
import com.github.javafaker.Faker;

public class ParticipantGeneratorUtils {

    private static final Faker NAME_FAKER = new Faker();

    public static User generateUser() {
        return new User(NAME_FAKER.funnyName().name(), NAME_FAKER.name().lastName());
    }

    public static Participant createParticipant(final Participant participant, final ParticipantAggregationLevelEum level) {
        return switch (level) {
            case MEMBER_USER -> participant;
            case USER -> new Participant(new Member(""), participant.getUser());
            case MEMBER -> new Participant(participant.getMember(), new User("", ""));
        };
    }
}
