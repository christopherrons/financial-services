package com.christopherrons.refdata.model.participant;


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
}
