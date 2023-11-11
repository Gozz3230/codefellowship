package com.codefellowshipRyanJava401d18.codefellowshipRyanJava401d18.models.repos;

import com.codefellowshipRyanJava401d18.codefellowshipRyanJava401d18.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepo extends JpaRepository<Post, Long> {
}
