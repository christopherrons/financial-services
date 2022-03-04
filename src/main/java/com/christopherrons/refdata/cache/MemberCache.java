package com.christopherrons.refdata.cache;

import com.christopherrons.refdata.model.participant.Member;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MemberCache {
    private final Map<String, Member> memberIdToMember = new ConcurrentHashMap<>();

    public void addMember(Member member) {
        memberIdToMember.putIfAbsent(member.getMemberId(), member);
    }
}
