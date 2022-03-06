package com.christopherrons.refdata.participant.cache;

import com.christopherrons.refdata.participant.model.Member;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class MemberCache {
    private final Map<String, Member> memberIdToMember = new ConcurrentHashMap<>();

    public void addMember(Member member) {
        memberIdToMember.putIfAbsent(member.getMemberId(), member);
    }
}
