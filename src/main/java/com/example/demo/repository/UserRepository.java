package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;
import com.example.demo.model.dto.ChangePasswordDTO;
import com.example.demo.model.dto.ForgotPasswordDTO;
import com.example.demo.model.dto.UserDTO;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  @Query("SELECT new com.example.demo.model.dto.UserDTO(u.id, p.name, p.nickname, r.name, u.password) FROM User u JOIN u.person p JOIN u.role r WHERE p.email = :userEmail")
  public UserDTO getUsers(@Param(value = "userEmail") String userEmail);

  @Query("SELECT new com.example.demo.model.dto.ChangePasswordDTO(u.id, p.email, u.password) FROM User u JOIN u.person p JOIN u.role r WHERE u.id = :id")
  public ChangePasswordDTO getUsersById(@Param("id") Integer id);

  // @Query("UPDATE User u SET u.password = :password WHERE u.id = :id")
  // public UserDTO updateUserUsingDTO(@Param("password") String password, @Param("id") Integer id);

  // @Query("SELECT new com.example.demo.model.dto.ForgotPasswordDTO(u.id, p.name, p.email) FROM User u JOIN u.person p WHERE p.email = :userEmail")
  // public ForgotPasswordDTO findByEmail(@Param(value = "userEmail") String userEmail);

  @Query("SELECT u FROM User u JOIN u.person p WHERE p.email = :userEmail")
  public User findByEmail(@Param(value = "userEmail") String userEmail);

  // public Optional<User> findTopByEmail(String email);
}
