package com.example.demo.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_role") //define sebuah table didatabase
@Data //anotasi data, gantinya setter getter dari lombok
@AllArgsConstructor //constructor dengan parameter semua properti
@NoArgsConstructor //constructor tidak menggunakan parameter
public class Role {
  @Id // PK
  @GeneratedValue(strategy = GenerationType.IDENTITY) // AI
  private Integer id;
  private String name; // admin, user
  private Integer level; // contohnya: 1 = user, 2 = admin
  @OneToMany(mappedBy = "role") //role dari penamaan diclass Invoices Details
  private List<User> user;
}
