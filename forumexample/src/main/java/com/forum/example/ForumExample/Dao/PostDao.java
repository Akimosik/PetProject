package com.forum.example.ForumExample.Dao;

import com.forum.example.ForumExample.Model.Post;
import com.forum.example.ForumExample.Model.Topic;
import com.forum.example.ForumExample.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PostDao extends JpaRepository<Post,Integer> {
    Set<Post> findByUser(User user);

    Set<Post> findByTopic(Topic topic);

    Set<Post> findAllByOrderByCreationDateDesc();

    Set<Post> findTop5ByOrderByCreationDateDesc();

    Post findById(int id);

}
