package com.forum.example.ForumExample.Controller;

import com.forum.example.ForumExample.Service.PostService;
import com.forum.example.ForumExample.Service.SectionService;
import com.forum.example.ForumExample.Service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Autowired
    private SectionService sectionService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private PostService postService;


    @GetMapping(value = { "/",
            "/home" })
    public String home(Model model,Authentication authentication) {
        model.addAttribute("sections", sectionService.findAll());
        model.addAttribute("topics", topicService.findRecent());
        model.addAttribute("posts", postService.findRecent());
        if (authentication != null)model.addAttribute("auth", authentication.isAuthenticated());
        if (authentication != null)model.addAttribute("admin", authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ADMIN")));

        return "home";
    }

}
