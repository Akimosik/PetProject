package com.forum.example.ForumExample.Service.impl;

import com.forum.example.ForumExample.Dao.TopicDao;
import com.forum.example.ForumExample.Model.Section;
import com.forum.example.ForumExample.Model.Topic;
import com.forum.example.ForumExample.Model.User;
import com.forum.example.ForumExample.Service.SectionService;
import com.forum.example.ForumExample.Service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicDao topicDao;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private TopicService topicService;

    @Override
    public List<Topic> findAll() {
        return topicDao.findAll();
    }

    @Override
    public Topic findOne(int id) {
        return topicDao.getOne(id);
    }

    @Override
    public Topic findById(int id) {
        return topicDao.findById(id);
    }

    @Override
    public Set<Topic> findRecent() {
        return topicService.findAllByOrderByCreationDateDesc();
    }

    @Override
    public Set<Topic> findAllByOrderByCreationDateDesc() {
        return topicDao.findAllByOrderByCreationDateDesc();
    }

    @Override
    public Set<Topic> findBySection(Section section) {
        return topicDao.findBySection(section);
    }

    @Override
    public Set<Topic> findBySection(String sectionName) {
        return findBySection(sectionService.findByName(sectionName));
    }

    @Override
    public Topic save(Topic topic) {
        return topicDao.save(topic);
    }

    @Override
    public Set<Topic> findBySection(int id) {
        return findBySection(sectionService.findById(id));
    }

    @Override
    public Set<Topic> findByUser(User user) {
        return topicDao.findByUser(user);
    }

    @Override
    public void delete(int id) {
        delete(findOne(id));
    }

    @Override
    public void delete(Topic topic) {
        topicDao.delete(topic);
    }

}
