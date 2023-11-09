package com.codefellowshipRyanJava401d18.codefellowshipRyanJava401d18.models.repos;

import com.codefellowshipRyanJava401d18.codefellowshipRyanJava401d18.models.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteUserRepo extends JpaRepository<SiteUser, Long> {

  public SiteUser findByUsername(String username);

}
