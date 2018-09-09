
package com.forum.example.ForumExample.Controller;

import com.forum.example.ForumExample.Controller.form.NewUserForm;
import com.forum.example.ForumExample.Controller.form.UserEditForm;
import com.forum.example.ForumExample.Exception.UserNotFoundException;
import com.forum.example.ForumExample.Model.User;
import com.forum.example.ForumExample.Model.UserProfile;
import com.forum.example.ForumExample.Service.UserProfileService;
import com.forum.example.ForumExample.Service.UserService;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserProfileService userProfileService;

    @RequestMapping(value = "/user/{username}")
    public String findUserByUsernameAndViewProfilePage(@PathVariable String username,
                                                       Model model,Authentication authentication) {
        UserProfile userProfile;
        try {
            userProfile = userProfileService.findOne(username);
        } catch (NullPointerException e) {
            throw new UserNotFoundException();
        }
        model.addAttribute("userProfile", userProfile);
        if (authentication != null)model.addAttribute("auth", authentication.isAuthenticated());
        if (authentication != null)model.addAttribute("admin", authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ADMIN")));
        return "user";
    }

    @RequestMapping(value = "/user/id/{id}")
    public String findUserByIdAndViewProfilePage(@PathVariable int id,
        Model model) {
        return "redirect:/user/" + userService.findById(id).getUsername();
    }

    @RequestMapping(value = "/users")
    public String listOfAllUser(Model model, Authentication authentication) {
        model.addAttribute("users", userService.findAll());
        if (authentication != null)model.addAttribute("auth", authentication.isAuthenticated());
        if (authentication != null)model.addAttribute("admin", authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ADMIN")));
        return "users";
    }


    @GetMapping(value = "/registration")
    public String regiristrationPage(Model model) {
        model.addAttribute("newUser", new NewUserForm());
        return "new_user_form";
    }

    @PostMapping(value = "/registration")
    public String processAndSaveNewUser(@Valid @ModelAttribute("newUser") NewUserForm newUser,
        BindingResult result,
        RedirectAttributes model) {

        if (result.hasErrors()) {
            return "new_user_form";
        }

        User user = new User();
        user.setEmail(newUser.getEmail());
        user.setUsername(newUser.getUsername());
        user.setPassword(newUser.getPassword());
        user.setActive(true);
        userService.create(user);

        model.addFlashAttribute("message", "user.successfully.added");

        return "redirect:/login";
    }

    @GetMapping(value = "/login")
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Вы ввели неверное имя пользователя или пароль.");
        }

        if (logout != null) {
            model.addAttribute("message", "Вы успешно вышли.");
        }

        return "login";
    }

    @RequestMapping(value = "/logout")
    public String logOutAndRedirectToLoginPage(Authentication authentication,
        HttpServletRequest request,
        HttpServletResponse response) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login?logout=true";
    }


    @RequestMapping(value = "/myprofile")
    public String myProfile(Authentication authentication,
        Model model, Principal principal) {
        String username = authentication.getName();
        UserProfile userProfile;
        User user = userService.findByUsername(authentication.getName());
        try {
            userProfile = userProfileService.findOne(username);
        } catch (NullPointerException e) {
            throw new UserNotFoundException();
        }
        model.addAttribute("userProfile", userProfile);
        model.addAttribute("user",user);
        if (principal != null)model.addAttribute("profileOwner", user.getUsername().equals(principal.getName()));
        if (authentication != null)model.addAttribute("auth", authentication.isAuthenticated());
        if (authentication != null)model.addAttribute("admin", authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ADMIN")));
        return "user";
    }


    @GetMapping(value = "/myprofile/delete")
    public String deleteProfile(Authentication authentication,Model model) {
        String password = new String();
        if (authentication.getName() == null){
            return "redirect:/";
        }else {
            model.addAttribute("password",password);
            return "user_confirm_delete";
        }
//        return authentication.getName() == null ? "redirect:/" : "user_confirm_delete";
    }

    @PostMapping(value = "/myprofile/delete")
    public String processAndDeleteProfileAndLogout(@ModelAttribute("password") String password,
        Authentication authentication,
        HttpServletRequest request,
        HttpServletResponse response,
        RedirectAttributes model) {
        if (authentication.getName() == null) {
            return "redirect:/";
        }
        User user = userService.findByUsername(authentication.getName());
        userService.remove(user, password);
        new SecurityContextLogoutHandler().logout(request, response, authentication);
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            cookie.setValue(null);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        model.addFlashAttribute("message", "user.account.deleted");
        return "redirect:/";
    }


    @GetMapping(value = "/myprofile/edit")
    public String editMode(Authentication authentication,
        Model model) {
        UserProfile userProfile;
        String username = authentication.getName();
        User user = userService.findByUsername(authentication.getName());
        if (username == null) {
            return "redirect:/";
        }
        try {
            userProfile = userProfileService.findOne(username);
        } catch (NullPointerException e) {
            throw new UserNotFoundException();
        }

        model.addAttribute("userProfile", userProfile);
        model.addAttribute("userEditForm", new UserEditForm());
        model.addAttribute("user", user);
        if (authentication != null)model.addAttribute("admin", authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ADMIN")));
        if (authentication != null)model.addAttribute("auth", authentication.isAuthenticated());
        return "user_edit_form";
    }

}
