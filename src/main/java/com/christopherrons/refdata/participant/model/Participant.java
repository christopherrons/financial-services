package com.christopherrons.refdata.participant.model;


import java.util.Objects;

public class Participant {

    private final Member member;
    private final User user;

    public Participant(Member member, User user) {
        this.member = member;
        this.user = user;
    }

    public Member getMember() {
        return member;
    }

    public User getUser() {
        return user;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Participant)) return false;
        Participant that = (Participant) o;
        return member.equals(that.member) && user.equals(that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(member, user);
    }
}
