package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.ResetPasswordToken;
import com.example.demo.model.User;

@Repository
public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, Integer> {
  @Query("SELECT r FROM ResetPasswordToken r WHERE r.token = :token")
  ResetPasswordToken findByToken(@Param("token") String token);

  @Query("SELECT r FROM ResetPasswordToken r WHERE r.user = :user")
  ResetPasswordToken findByUser(@Param("user") User user);
}
