package com.example.demo.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
  private String paymentId;
  private String invoiceId;
  private String paymentMethod;
  private String paymentStatus;
  private String bankDetails;
  private String personName;
}
