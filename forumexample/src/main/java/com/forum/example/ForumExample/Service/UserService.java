package com.forum.example.ForumExample.Service;

import com.forum.example.ForumExample.Model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(int id);

    User findByUsername(String username);

    User findByEmail(String email);

    User save(User user);

    void create(User user);

    void remove(int id);

    void remove(User user);

    void remove(User user,
                String password);
}
