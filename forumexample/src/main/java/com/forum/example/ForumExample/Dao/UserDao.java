package com.forum.example.ForumExample.Dao;

import com.forum.example.ForumExample.Model.Role;
import com.forum.example.ForumExample.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Integer>{
    User findByUsername(String username);

    User findByEmail(String email);

    User findById(int id);
}
