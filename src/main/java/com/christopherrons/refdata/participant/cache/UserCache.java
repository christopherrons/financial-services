package com.christopherrons.refdata.participant.cache;

import com.christopherrons.refdata.participant.model.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class UserCache {

    private final Map<String, User> userIdToUser = new ConcurrentHashMap<>();

    public void addUser(final User user) {
        userIdToUser.putIfAbsent(user.getUserId(), user);
    }

}
