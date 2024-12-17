package com.example.demo.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "tb_shipping") //define sebuah table didatabase
@Data //anotasi data, gantinya setter getter dari lombok
@AllArgsConstructor //constructor dengan parameter semua properti
@NoArgsConstructor //constructor tidak menggunakan parameter
public class Shipping {
  @Id // PK
  private String id;
  private String address;
  private LocalDate estimatedArrival;
  // private Invoices invoices;
  @ManyToOne
  @JoinColumn(name = "courier_id", referencedColumnName = "id")
  private Courier courier;
}
