package org.punna.methodsecurity.repository;


import org.punna.methodsecurity.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {
    private final Map<String, User> users = new HashMap<>();

    public UserRepository() {

    }

    public UserRepository(List<User> users) {
        for (User user : users) {
            this.users.put(user.getUsername(), user);
        }
    }

    public User getUser(String username) {
        return users.get(username);
    }
}
