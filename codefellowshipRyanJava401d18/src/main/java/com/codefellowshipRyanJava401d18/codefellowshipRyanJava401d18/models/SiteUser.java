package com.codefellowshipRyanJava401d18.codefellowshipRyanJava401d18.models;

import jakarta.persistence.*;
import org.hibernate.usertype.UserType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class SiteUser implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;
  @Column(unique = true)
  private String username;
  private String password;
  private String firstName;
  private String lastName;
  private LocalDate dateOfBirth;
  private String bio;
//  String imageURL;

  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
  private List<Post> posts;

  // Constructors
  public SiteUser() {
  }

  public SiteUser(String username, String password, String firstName, String lastName, LocalDate dateOfBirth, String bio) {
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
    this.bio = bio;
  }

  @ManyToMany
  @JoinTable(
          name = "followers_to_following",
          joinColumns = {@JoinColumn(name = "userWhoIsFollowing")},
          inverseJoinColumns = {@JoinColumn(name = "Followed_user")}
  )
  private Set<SiteUser> usersIFollow = new HashSet<>();

  @ManyToMany(mappedBy = "usersIFollow")
  private Set<SiteUser> usersWhoFollowMe = new HashSet<>();


  // getters and setters

  public Set<SiteUser> getUsersIFollow() {
    return usersIFollow;
  }

  public void setUsersIFollow(Set<SiteUser> usersIFollow) {
    this.usersIFollow = usersIFollow;
  }

  public Set<SiteUser> getUsersWhoFollowMe() {
    return usersWhoFollowMe;
  }

  public void setUsersWhoFollowMe(Set<SiteUser> usersToFollowMe) {
    this.usersWhoFollowMe = usersToFollowMe;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  // Methods
  public void addPost(Post post) {
    post.setApplicationUser(this);
    posts.add(post);
  }

  // Getters and Setters
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @Override
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public List<Post> getPosts() {
    return posts;
  }

  public void setPosts(List<Post> posts) {
    this.posts = posts;
  }


}