package com.forum.example.ForumExample.Service;

import com.forum.example.ForumExample.Model.UserProfile;

public interface UserProfileService {

    public UserProfile findOne(int userId);

    public UserProfile findOne(String username);

}