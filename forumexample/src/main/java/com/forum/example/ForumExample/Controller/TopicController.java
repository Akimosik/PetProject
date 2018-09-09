/**
 * Created by Dawid Stankiewicz on 19.07.2016
 */
package com.forum.example.ForumExample.Controller;

import javax.validation.Valid;

import com.forum.example.ForumExample.Controller.form.NewPostForm;
import com.forum.example.ForumExample.Controller.form.NewTopicForm;
import com.forum.example.ForumExample.Model.Post;
import com.forum.example.ForumExample.Model.Topic;
import com.forum.example.ForumExample.Model.User;
import com.forum.example.ForumExample.Service.PostService;
import com.forum.example.ForumExample.Service.SectionService;
import com.forum.example.ForumExample.Service.TopicService;
import com.forum.example.ForumExample.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;


@Controller
@RequestMapping("/topic/")
public class TopicController {
    
    @Autowired
    private PostService postService;
    
    @Autowired
    private TopicService topicService;
    
    @Autowired
    private SectionService sectionService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping(value = "{idTopic}")
    public String getTopicById(@PathVariable int idTopic,
                               Model model, Principal principal,Authentication authentication) {
        Topic topic = topicService.findById(idTopic);
        topic.setViews(topic.getViews() + 1);
        topicService.save(topic);
        model.addAttribute("topic", topic);
        model.addAttribute("posts", postService.findByTopic(idTopic));
        model.addAttribute("newPost", new NewPostForm());
        if (principal != null)model.addAttribute("topicowner", topic.getUser().getUsername().equals(principal.getName()));
        if (authentication != null)model.addAttribute("auth", authentication.isAuthenticated());
        if (authentication != null)model.addAttribute("admin", authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ADMIN")));
        return "topic";
    }

    @PostMapping(value = "{idTopic}")
    public String addPost(@Valid @ModelAttribute("newPost") NewPostForm newPost,
                          BindingResult result,
                          Authentication authentication,
                          @PathVariable int idTopic,
                          Model model) {
        
        if (result.hasErrors()) {
            model.addAttribute("topic", topicService.findOne(idTopic));
            model.addAttribute("posts", postService.findByTopic(idTopic));

            return "topic";
        }
        
        Post post = new Post();
        post.setContent(newPost.getContent());
        post.setTopic(topicService.findById(idTopic));
        post.setUser(userService.findByUsername(authentication.getName()));
        postService.save(post);
        
        model.asMap().clear();
        return "redirect:/topic/" + idTopic;
    }
    
    @GetMapping(value = "new")
    public String getNewTopictForm(Model model, Authentication authentication) {
        model.addAttribute("newTopic", new NewTopicForm());
        model.addAttribute("sections", sectionService.findAll());
        if (authentication != null)model.addAttribute("auth", authentication.isAuthenticated());
        if (authentication != null)model.addAttribute("admin", authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ADMIN")));
        return "new_topic_form";
    }
    
    @PostMapping(value = "new")
    public String processAndAddNewTopic(@Valid @ModelAttribute("newTopic") NewTopicForm newTopic,
                                        BindingResult result,
                                        Authentication authentication,
                                        Model model) {
        
        if (result.hasErrors()) {
            model.addAttribute("sections", sectionService.findAll());
            return "new_topic_form";
        }
        
        Topic topic = new Topic();
        topic.setUser(userService.findByUsername(authentication.getName()));
        topic.setTitle(newTopic.getTitle());
        topic.setContent(newTopic.getContent());
        topic.setSection(sectionService.findById(newTopic.getSectionId()));
        topicService.save(topic);
        
        return "redirect:/topic/" + topic.getId();
    }
    
    @GetMapping(value = "delete/{id}")
    public String delete(@PathVariable int id,
                         Authentication authentication,
                         RedirectAttributes model) {
        Topic topic = topicService.findById(id);
        
        if (topic == null) {
            return "redirect:/";
        }
        if (!authentication.getName().equals(topic.getUser().getUsername())) {
            return "redirect:/topic/" + id;
        }
        
        topicService.delete(topic);
        
        model.addFlashAttribute("message", "topic.successfully.deleted");

        return "redirect:/section/" + topic.getSection().getId();
    }
    
}
