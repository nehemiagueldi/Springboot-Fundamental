package com.example.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
  private String name;
  private String nickname;
  private String address;
  private Integer phone;
  private String email;
  private String password;
}
