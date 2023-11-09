package com.codefellowshipRyanJava401d18.codefellowshipRyanJava401d18.configs;

import com.codefellowshipRyanJava401d18.codefellowshipRyanJava401d18.models.repos.SiteUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
  @Autowired
  SiteUserRepo siteUserRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return (UserDetails) siteUserRepo.findByUsername(username);
  }
}
