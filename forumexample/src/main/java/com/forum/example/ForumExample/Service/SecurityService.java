package com.forum.example.ForumExample.Service;


public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);

}
