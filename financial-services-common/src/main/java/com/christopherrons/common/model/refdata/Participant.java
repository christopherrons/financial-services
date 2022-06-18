package com.christopherrons.common.model.refdata;


public record Participant(Member member, User user) {
    public String getParticipantId() {
        return member.getMemberId() + "-" + user.getUserId();
    }
}
