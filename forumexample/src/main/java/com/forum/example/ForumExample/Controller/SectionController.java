package com.forum.example.ForumExample.Controller;

import com.forum.example.ForumExample.Controller.form.NewSectionForm;
import com.forum.example.ForumExample.Model.Role;
import com.forum.example.ForumExample.Model.Section;
import com.forum.example.ForumExample.Model.User;
import com.forum.example.ForumExample.Service.RoleService;
import com.forum.example.ForumExample.Service.SectionService;
import com.forum.example.ForumExample.Service.TopicService;
import com.forum.example.ForumExample.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/section/")
public class SectionController {
    @Autowired
    private SectionService sectionService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


    @RequestMapping("{id}")
    public String getTopicsFromSection(@PathVariable int id,
                                       Model model,Authentication authentication) {
        model.addAttribute("section", sectionService.findById(id));
        model.addAttribute("topics", topicService.findBySection(id));
        if (authentication != null)model.addAttribute("auth", authentication.isAuthenticated());
        if (authentication != null)model.addAttribute("admin", authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ADMIN")));
        return "section";
    }

    @GetMapping(value = "new")
    public String getNewSectionForm(Model model,Authentication authentication) {
        model.addAttribute("newSection", new NewSectionForm());
        if (authentication != null)model.addAttribute("auth", authentication.isAuthenticated());
        if (authentication != null)model.addAttribute("admin", authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ADMIN")));
        return "new_section_form";
    }

    @PostMapping(value = "new")
    public String processAndAddNewSection(
            @Valid @ModelAttribute("newSection") NewSectionForm newSection,
            BindingResult result) {

        if (result.hasErrors()) {
            return "new_section_form";
        }

        Section section = new Section();
        section.setName(newSection.getName());
        section.setDescription(newSection.getDescription());
        section = sectionService.save(section);
        return "redirect:/section/" + section.getId();
    }

    @GetMapping(value = "delete/{id}")
    public String delete(@PathVariable int id,
                         Authentication authentication,
                         RedirectAttributes model) {
        User user = userService.findByUsername(authentication.getName());
        Role adminRole = roleService.findByName("ADMIN");
        if (!user.getRoles().contains(adminRole)) {
            return "redirect:/section/" + id;
        }
        sectionService.delete(id);

        model.addFlashAttribute("message", "section.successfully.deleted");
        return "redirect:/home";
    }

}
