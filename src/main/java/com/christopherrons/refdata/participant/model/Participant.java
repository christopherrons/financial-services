package com.christopherrons.refdata.participant.model;


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

    public String getParticipantId() {
        return member.getMemberId() + "-" + user.getUserId();
    }
}
