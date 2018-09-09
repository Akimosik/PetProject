package com.forum.example.ForumExample.Service.impl;

import com.forum.example.ForumExample.Dao.PostDao;
import com.forum.example.ForumExample.Dao.TopicDao;
import com.forum.example.ForumExample.Dao.UserDao;
import com.forum.example.ForumExample.Model.Post;
import com.forum.example.ForumExample.Model.Topic;
import com.forum.example.ForumExample.Model.User;
import com.forum.example.ForumExample.Service.PostService;
import com.forum.example.ForumExample.Service.TopicService;
import com.forum.example.ForumExample.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserService userService;

    @Override
    public Post findById(int id) { return postDao.findById(id);   }

    @Override
    public List<Post> findAll() {
        return postDao.findAll();
    }

    @Override
    public Set<Post> findRecent() {
        return postDao.findTop5ByOrderByCreationDateDesc();
    }

    @Override
    public Set<Post> findAllByOrderByCreationDateDesc() {
        return postDao.findAllByOrderByCreationDateDesc();
    }

    @Override
    public Set<Post> findByUser(User user) {
        return postDao.findByUser(user);
    }

    @Override
    public Set<Post> findByTopic(int idTopic) {
        return findByTopic(topicService.findOne(idTopic));
    }

    @Override
    public Set<Post> findByTopic(Topic topic) {
        return postDao.findByTopic(topic);
    }

    @Override
    public void save(Post post) {
        postDao.save(post);
    }

    @Override
    public void delete(int id) {
        delete(findById(id));
    }

    @Override
    public void delete(Post post) {
        postDao.delete(post);
    }

    @Override
    public void save(String content,
                     String username,
                     int idTopic) {
        Post post = new Post();
        post.setTopic(topicService.findOne(idTopic));
        post.setUser(userService.findByUsername(username));
        post.setContent(content);
        save(post);
    }
}
