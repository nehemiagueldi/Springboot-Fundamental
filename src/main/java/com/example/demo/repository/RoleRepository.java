package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
  @Query("SELECT r FROM Role r WHERE r.id = :roleId")
  public Role getRoleById(@Param(value = "roleId") Integer roleId);

  @Query("SELECT r FROM Role r WHERE r.level = :roleLevel")
  public Role getRoleByLevel(@Param(value = "roleLevel") Integer roleLevel);

  @Query("SELECT r FROM Role r WHERE r.level = (SELECT MIN(r.level) FROM Role r)")
  public Role getRoleWithMinLevel();
}
