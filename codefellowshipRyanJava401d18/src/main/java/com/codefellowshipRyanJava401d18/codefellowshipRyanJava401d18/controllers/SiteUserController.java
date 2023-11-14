package com.codefellowshipRyanJava401d18.codefellowshipRyanJava401d18.controllers;

import com.codefellowshipRyanJava401d18.codefellowshipRyanJava401d18.models.SiteUser;
import com.codefellowshipRyanJava401d18.codefellowshipRyanJava401d18.models.Post;
import com.codefellowshipRyanJava401d18.codefellowshipRyanJava401d18.models.repos.SiteUserRepo;
import com.codefellowshipRyanJava401d18.codefellowshipRyanJava401d18.models.repos.PostRepo;

//import jakarta.persistence.JoinTable;
//import jakarta.persistence.ManyToMany;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
//import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
public class SiteUserController {
  @Autowired
  SiteUserRepo siteUserRepo;
  @Autowired
  PostRepo postRepo;
  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  private HttpServletRequest request;

  @GetMapping("/")
  public String getHomePage(Model m, Principal p) {
    if (p != null) {
      String username = p.getName();
      SiteUser user = siteUserRepo.findByUsername(username);

      m.addAttribute("username", username);
    }
    return "/index.html";
  }

  @GetMapping("/login")
  public String getLoginPage(Principal p) {
    if (p != null) {
      return "redirect:/";
    }
    return "login.html";
  }

  @GetMapping("/signup")
  public String getSignUpPage() {
    return "signup.html";
  }

  @PostMapping("/signup")
  public RedirectView createUser(String username, String password, String firstName, String lastName, LocalDate dateOfBirth, String bio, RedirectAttributes redir) {
    // Check if the username already exists
    if (siteUserRepo.findByUsername(username) != null) {
      redir.addFlashAttribute("errorMessage", "That username already exists!");
      return new RedirectView("/signup?error");
    }

    SiteUser newUser = new SiteUser(username, passwordEncoder.encode(password), firstName, lastName, dateOfBirth, bio);
    siteUserRepo.save(newUser);
    authWithHttpServletRequest(username, password);
    return new RedirectView("/");
  }

  public void authWithHttpServletRequest(String username, String password) {
    try {
      request.login(username, password);
    } catch (ServletException e) {
      System.out.println("Error while logging in!");
      e.printStackTrace();
    }
  }

  @GetMapping("/test")
  public String getTestPage(Model m, Principal p) {
    if (p != null) {
      String username = p.getName();
      SiteUser user = siteUserRepo.findByUsername(username);

      if (user != null) {
        m.addAttribute("username", user.getUsername());
      }
    }

    return "/test.html";
  }

  @GetMapping("/myProfile")
  public String getMyProfile(Model m, Principal p) {
    if (p != null) {
      SiteUser user = siteUserRepo.findByUsername(p.getName());
      m.addAttribute("user", user);
      m.addAttribute("username", user.getUsername());
      return "/myProfile.html";
    }
    return "/login.html";
  }

  @PutMapping("/myProfile")
  public RedirectView editProfile(Principal p, String username, String firstName, String lastName, LocalDate dateOfBirth, String bio, Long id, RedirectAttributes redir) {
    SiteUser user = siteUserRepo.findById(id).orElseThrow();
    if (p != null) {
      user.setUsername(username);
      user.setFirstName(firstName);
      user.setLastName(lastName);
      user.setDateOfBirth(dateOfBirth);
      user.setBio(bio);
      siteUserRepo.save(user);

      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(),
              user.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } else {
      redir.addFlashAttribute("errorMessage", "You are not permitted to edit this profile!");
    }

    return new RedirectView("/myProfile.html");
  }

  @PostMapping("/createPost")
  public RedirectView createPost(Principal p, String body, long id, RedirectAttributes redir) {
    SiteUser user = siteUserRepo.findById(id).orElseThrow();
    if (p != null) {
      Date date = new Date();
      Post post = new Post(body, date);
      user.addPost(post);
      postRepo.save(post);
      siteUserRepo.save(user);
    } else {
      redir.addFlashAttribute("errorMessage", "You are not permitted to add posts to this profile!");
    }
    return new RedirectView("/myProfile");
  }

  @GetMapping("/user-info/{id}")
  public String getUserInfoPage(Model m, Principal p, @PathVariable long id, RedirectAttributes redir) {
    if (p != null) {
      SiteUser user = siteUserRepo.findById(id).orElseThrow();
      m.addAttribute("user", user);

      SiteUser viewUser = siteUserRepo.findById(id).orElseThrow();

      m.addAttribute("viewUser", viewUser);
      m.addAttribute("usersIFollow", viewUser.getUsersIFollow());
      m.addAttribute("usersWhoFollowMe", viewUser.getUsersWhoFollowMe());
      return "/user-info.html";
    } else {
      redir.addFlashAttribute("errorMessage", "You must be logged in to view this profile");
      return "redirect:/login";
    }
  }

  @PutMapping("follow-user/{id}")
  public RedirectView followUser(Principal p, @PathVariable Long id) {
    SiteUser userToFollow = siteUserRepo.findById(id).orElseThrow(() -> new RuntimeException("Error reading user from database with id of: " + id));
    SiteUser currentAuthUser = siteUserRepo.findByUsername(p.getName());
    if (currentAuthUser.getUsername().equals(userToFollow.getUsername())) {
      throw new IllegalArgumentException("You cannot follow yourself");
    }
    currentAuthUser.getUsersIFollow().add(userToFollow);
    siteUserRepo.save(currentAuthUser);

//    return new RedirectView("/user/" + id);
    return new RedirectView("/users.html");

  }

  // TODO: finish new code

  @GetMapping("/users")
  public String getAllUsersPage(Model m, Principal p) {
    List<SiteUser> users = siteUserRepo.findAll();
    m.addAttribute("usersArray", users);

    if (p != null) {
      String username = p.getName();
      SiteUser siteUser = siteUserRepo.findByUsername(username);
      String imageURL = "https://images.unsplash.com/photo-1599508704512-2f19efd1e35f?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8cXVlc3Rpb24lMjBtYXJrfGVufDB8fDB8fHww&auto=format&fit=crop&w=500&q=60";
//      siteUser.setImageURL(imageURL);

      m.addAttribute("username", username);
      m.addAttribute("currentUser", siteUser);
    }
    return "/users.html";
  }

  @GetMapping("/feed")
  public String getFeedPage(Model m, Principal p) {
    if (p != null) {
      String username = p.getName();
      SiteUser currentUser = siteUserRepo.findByUsername(username);
      Set<SiteUser> following = currentUser.getUsersIFollow();

      // get all posts from users the current user is following
      List<Post> feedPosts = new ArrayList<>();
      for (SiteUser user : following) {
        feedPosts.addAll(user.getPosts());
      }

      m.addAttribute("username", username);
      m.addAttribute("feedPosts", feedPosts);
    }
    return "/feed.html";
  }

}
