package com.forum.example.ForumExample.Service;

import com.forum.example.ForumExample.Model.Section;
import com.forum.example.ForumExample.Model.Topic;
import com.forum.example.ForumExample.Model.User;

import java.util.List;
import java.util.Set;

public interface TopicService {
    List<Topic> findAll();

    Topic findOne(int id);

    Set<Topic> findRecent();

    Topic findById(int id);

    Set<Topic> findAllByOrderByCreationDateDesc();

    Set<Topic> findBySection(Section section);

    Set<Topic> findBySection(String sectionName);

    Topic save(Topic topic);

    Set<Topic> findBySection(int id);

    Set<Topic> findByUser(User user);

    void delete(int id);

    void delete(Topic topic);

}
