package com.example.demo.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Component
public class MyUserDetailsService implements UserDetailsService {
  @Autowired 
  private UserRepository userRepo;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User userRes = userRepo.findByEmail(email);
    if (userRes == null)
        throw new UsernameNotFoundException("Could not find user with email = " + email);
    // if(userRes.isEmpty())
    //     throw new UsernameNotFoundException("Could not findUser with email = " + email);
    // User user = userRes.get();
    return new org.springframework.security.core.userdetails.User(
            email,
            userRes.getPassword(),
            Collections.singletonList(new SimpleGrantedAuthority("admin")));
  }
}
