package com.example.demo.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_shipping") //define sebuah table didatabase
@Data //anotasi data, gantinya setter getter dari lombok
@AllArgsConstructor //constructor dengan parameter semua properti
@NoArgsConstructor //constructor tidak menggunakan parameter
public class Shipping {
  @Id // PK
  private String id;
  // private String address;
  private LocalDate estimatedArrival;
  @OneToOne
  @JsonIgnore
  @JoinColumn(name = "invoices_id", referencedColumnName = "id")
  private Invoices invoices;
  @ManyToOne
  @JoinColumn(name = "courier_id", referencedColumnName = "id")
  private Courier courier;
}
