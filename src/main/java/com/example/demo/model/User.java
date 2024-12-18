package com.example.demo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name = "tb_user") //define sebuah table didatabase
@Data //anotasi data, gantinya setter getter dari lombok
@AllArgsConstructor //constructor dengan parameter semua properti
@NoArgsConstructor //constructor tidak menggunakan parameter
public class User {
  @Id // PK
  private Integer id;
  @Column(length = 60)
  private String password;
  @ManyToOne
  @JoinColumn(name = "role_id", referencedColumnName = "id")
  private Role role;
  @OneToOne
  @JsonIgnore
  @JoinColumn(name = "id", referencedColumnName = "id")
  private Person person;
  @OneToMany(mappedBy = "user") //user dari penamaan diclass Invoices Details
  @JsonIgnore
  private List<ResetPasswordToken> resetPasswordTokens;
}
