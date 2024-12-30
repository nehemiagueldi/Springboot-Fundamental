package com.example.demo.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity //define sebuah table didatabase
@Table(name = "tb_asset")
@Data //anotasi data, gantinya setter getter dari lombok
@AllArgsConstructor //constructor dengan parameter semua properti
@NoArgsConstructor //constructor tidak menggunakan parameter
public class Asset {
  @Id // PK
  @GeneratedValue(strategy = GenerationType.IDENTITY) // AI
  private Integer id;
  private String path;
  @OneToMany(mappedBy = "asset") //asset dari penamaan dimodel book
  @JsonIgnore
  private List<Book> books;
}
