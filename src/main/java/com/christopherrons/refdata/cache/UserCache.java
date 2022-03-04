package com.christopherrons.refdata.cache;

import com.christopherrons.refdata.model.participant.User;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserCache {

    private final Map<String, User> userIdToUser = new ConcurrentHashMap<>();

    public void addUser(final User user) {
        userIdToUser.putIfAbsent(user.getUserId(), user);
    }

}
