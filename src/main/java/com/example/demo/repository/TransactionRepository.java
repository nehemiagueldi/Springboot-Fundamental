package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Payment;

public interface TransactionRepository extends JpaRepository<Payment, String> {
  @Query(value = """
    SELECT 
      tb_payment.id AS payment_id, 
      tb_invoices.id AS invoice_id,
      tb_payment.transaction_date AS "transaction date", 
      tb_payment_method.name AS payment_method, 
      tb_payment_status.name AS payment_status, 
      tb_bank_details.name AS bank_details,
      CONCAT('Rp ', FORMAT(SUM(tb_invoices_details.quantity * tb_book.price), 2, 'id_iD')) AS "total price",
      tb_person.name AS person_name
    FROM tb_payment
    LEFT JOIN tb_payment_method ON tb_payment_method.id = tb_payment.payment_method_id
    LEFT JOIN tb_payment_status ON tb_payment_status.id = tb_payment.payment_status_id
    LEFT JOIN tb_bank_details ON tb_bank_details.id = tb_payment.bank_details_id
    LEFT JOIN tb_invoices ON tb_invoices.id = tb_payment.invoices_id
    LEFT JOIN tb_invoices_details ON tb_invoices.id = tb_invoices_details.invoices_id
    LEFT JOIN tb_book ON tb_book.id = tb_invoices_details.book_id
    LEFT JOIN tb_person ON tb_person.id = tb_invoices_details.person_id
    WHERE 
      tb_payment.id LIKE CONCAT('%', :search, '%')
      OR tb_invoices.id LIKE CONCAT('%', :search, '%')
      OR tb_payment_method.name LIKE CONCAT('%', :search, '%')
      OR tb_payment_status.name LIKE CONCAT('%', :search, '%')
      OR tb_bank_details.name LIKE CONCAT('%', :search, '%')
      OR tb_person.name LIKE CONCAT('%', :search, '%')
    GROUP BY tb_payment.id, tb_person.name
    """, nativeQuery = true)
  Page<Object[]> searchTransactions(@Param(value = "search") String search, Pageable pageable);

  @Query(value = """
    SELECT 
      COUNT(*)
    FROM tb_payment
    LEFT JOIN tb_payment_method ON tb_payment_method.id = tb_payment.payment_method_id
    LEFT JOIN tb_payment_status ON tb_payment_status.id = tb_payment.payment_status_id
    LEFT JOIN tb_bank_details ON tb_bank_details.id = tb_payment.bank_details_id
    LEFT JOIN tb_invoices ON tb_invoices.id = tb_payment.invoices_id
    LEFT JOIN tb_invoices_details ON tb_invoices.id = tb_invoices_details.invoices_id
    LEFT JOIN tb_person ON tb_person.id = tb_invoices_details.person_id
    WHERE 
      tb_payment.id LIKE CONCAT('%', :search, '%')
      OR tb_invoices.id LIKE CONCAT('%', :search, '%')
      OR tb_payment_method.name LIKE CONCAT('%', :search, '%')
      OR tb_payment_status.name LIKE CONCAT('%', :search, '%')
      OR tb_bank_details.name LIKE CONCAT('%', :search, '%')
      OR tb_person.name LIKE CONCAT('%', :search, '%')
    """, nativeQuery = true)
  long countFiltered(String search);
}
