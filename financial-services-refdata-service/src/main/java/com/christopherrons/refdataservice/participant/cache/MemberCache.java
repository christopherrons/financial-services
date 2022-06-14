package com.christopherrons.refdataservice.participant.cache;

import com.christopherrons.common.model.refdata.Member;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class MemberCache {
    private final Map<String, Member> memberIdToMember = new ConcurrentHashMap<>();

    public void addMember(Member member) {
        memberIdToMember.putIfAbsent(member.getMemberId(), member);
    }
}
