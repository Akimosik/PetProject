package com.forum.example.ForumExample.Service.impl;

import com.forum.example.ForumExample.Dao.SectionDao;
import com.forum.example.ForumExample.Model.Section;
import com.forum.example.ForumExample.Service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionServiceImpl implements SectionService {

    @Autowired
    private SectionDao sectionDao;

    @Override
    public List<Section> findAll() {
        return sectionDao.findAll();
    }

    @Override
    public Section findById(int id) {
        return sectionDao.findById(id);
    }

    @Override
    public Section findByName(String name) {
        return sectionDao.findByName(name);
    }

    @Override
    public Section save(Section section) {
        return sectionDao.save(section);
    }

    @Override
    public void delete(int id) {
        delete(findById(id));
    }

    @Override
    public void delete(Section section) {
        sectionDao.delete(section);
    }
}
