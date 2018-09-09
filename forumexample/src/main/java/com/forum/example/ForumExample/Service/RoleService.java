package com.forum.example.ForumExample.Service;

import com.forum.example.ForumExample.Model.Role;

import java.util.List;

public interface RoleService {

    Role findOne(int id);

    Role findByName(String name);

    List<Role> findAll();

    void save(Role role);

    void save(String name,
              String description);

    void delete(Role role);

    void delete(String name);

    void delete(int id);

}
