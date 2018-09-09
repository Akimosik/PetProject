package com.forum.example.ForumExample.Service;

import com.forum.example.ForumExample.Model.Post;
import com.forum.example.ForumExample.Model.Topic;
import com.forum.example.ForumExample.Model.User;

import java.util.List;
import java.util.Set;

public interface PostService {

    List<Post> findAll();

    Set<Post> findRecent();

    Set<Post> findAllByOrderByCreationDateDesc();

    Set<Post> findByUser(User user);

    Set<Post> findByTopic(int idTopic);

    Post findById(int id);

    Set<Post> findByTopic(Topic topic);

    void save(Post post);

    void delete(int id);

    void delete(Post post);

    void save(String content,
              String username,
              int idTopic);
}
