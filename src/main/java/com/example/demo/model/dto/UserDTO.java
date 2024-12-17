package com.example.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
  private Integer id;
  private String name;
  private String nickname;
  private String role;
  private String password;
  @Override
  public String toString() {
    return "UserDTO [id=" + id + ", name=" + name + ", nickname=" + nickname + ", role="
        + role + ", password=" + password + "]";
  }
}
