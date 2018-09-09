package com.forum.example.ForumExample.Service.impl;


import com.forum.example.ForumExample.Dao.RoleDao;
import com.forum.example.ForumExample.Model.Role;
import com.forum.example.ForumExample.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Role findOne(int id) {
        return roleDao.findById(id);
    }

    @Override
    public Role findByName(String name) {
        return roleDao.findByName(name);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public void save(Role role) {
        roleDao.save(role);
    }

    @Override
    public void save(String name,
                     String description) {
        save(new Role(name, description));
    }

    @Override
    public void delete(Role role) {
        roleDao.delete(role);
    }

    @Override
    public void delete(String name) {
        delete(findByName(name));
    }

    @Override
    public void delete(int id) {
        delete(findOne(id));
    }

}
