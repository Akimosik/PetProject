package com.forum.example.ForumExample.Service.impl;

import com.forum.example.ForumExample.Model.User;
import com.forum.example.ForumExample.Model.UserProfile;
import com.forum.example.ForumExample.Service.PostService;
import com.forum.example.ForumExample.Service.TopicService;
import com.forum.example.ForumExample.Service.UserProfileService;
import com.forum.example.ForumExample.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private TopicService topicService;

    @Override
    public UserProfile findOne(int userId) {
        UserProfile userProfile = new UserProfile();
        User user = userService.findById(userId);
        userProfile.setUser(user);
        userProfile.setPosts(postService.findByUser(user));
        userProfile.setTopics(topicService.findByUser(user));
        return userProfile;
    }

    @Override
    public UserProfile findOne(String username) {
        return findOne(userService.findByUsername(username).getId());
    }
}

