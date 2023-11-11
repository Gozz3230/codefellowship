package com.codefellowshipRyanJava401d18.codefellowshipRyanJava401d18.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;
  @Column(columnDefinition = "text")
  String body;
  Date createdAt;

  @ManyToOne
  @JoinColumn(name = "applicationUser_id")
  SiteUser author;

  public Post() {
    //empty
  }

  public Post(String postContent, Date createdAt) {
    this.body = postContent;
    this.createdAt = createdAt;
  }

  public long getId() {
    return id;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String postContent) {
    this.body = postContent;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public SiteUser getApplicationUser() {
    return author;
  }

  public void setApplicationUser(SiteUser applicationUser) {
    this.author = applicationUser;
  }
}
