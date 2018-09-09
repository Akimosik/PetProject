package com.forum.example.ForumExample.Dao;

import com.forum.example.ForumExample.Model.Section;
import com.forum.example.ForumExample.Model.Topic;
import com.forum.example.ForumExample.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TopicDao extends JpaRepository<Topic,Integer>{
    Set<Topic> findBySection(Section section);

    Set<Topic> findByUser(User user);

    Set<Topic> findAllByOrderByCreationDateDesc();

    Set<Topic> findTop5ByOrderByCreationDateDesc();

    Topic findById(int id);
}
