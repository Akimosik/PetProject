package com.forum.example.ForumExample.Dao;

import com.forum.example.ForumExample.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role,Integer> {
    Role findByName(String name);
    Role findById(int id);
}
