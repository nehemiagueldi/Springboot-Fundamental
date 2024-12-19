package com.example.demo.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.LoginCredentials;
import com.example.demo.model.Person;
import com.example.demo.model.User;
import com.example.demo.model.dto.PersonDTO;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JWTUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired private UserRepository userRepo;
  @Autowired private PersonRepository personRepository;
  @Autowired private RoleRepository roleRepository;
  @Autowired private JWTUtil jwtUtil;
  @Autowired private AuthenticationManager authManager;
  @Autowired private PasswordEncoder passwordEncoder;

  @PostMapping("/register")
  public Map<String, Object> registerHandler(@RequestBody PersonDTO personDTO){
    Person person = new Person();
    person.setName(personDTO.getName());
    person.setNickname(personDTO.getNickname());
    person.setAddress(personDTO.getAddress());
    person.setPhone(personDTO.getPhone());
    person.setEmail(personDTO.getEmail());

    person = personRepository.save(person);
    Integer personId = person.getId();

    User user = new User();
    user.setId(personId);
    user.setRole(roleRepository.getRoleWithMinLevel());
    user.setPassword(passwordEncoder.encode(personDTO.getPassword()));
    user = userRepo.save(user);
    String token = jwtUtil.generateToken(person.getEmail());
    return Collections.singletonMap("jwt-token", token);
  }

  @PostMapping("/login")
  public Map<String, Object> loginHandler(@RequestBody LoginCredentials body){
    try {
        UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());

        authManager.authenticate(authInputToken);

        String token = jwtUtil.generateToken(body.getEmail());

        return Collections.singletonMap("jwt-token", token);
    }catch (AuthenticationException authExc){
        throw new RuntimeException("Invalid Login Credentials");
    }
  }
}
