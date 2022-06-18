package com.christopherrons.common.model.refdata;

public record Member(String memberId) {
    public String getMemberId() {
        return memberId;
    }
}
