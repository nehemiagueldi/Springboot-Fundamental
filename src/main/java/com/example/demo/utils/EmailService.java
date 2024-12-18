package com.example.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
  @Autowired
  private JavaMailSender mailSender;

  public String sendEmail(String to, String subject, String body){
    try {
      SimpleMailMessage message = new SimpleMailMessage();
  
      message.setFrom("nehemia.gueldi@student.umn.ac.id");
      message.setTo(to);
      message.setSubject(subject);
      message.setText(body);

      mailSender.send(message);
      return "Email sent successfully";  // Return pesan berhasil
    } catch (Exception e) {
      e.printStackTrace();
      return "Error sending email: " + e.getMessage();  // Return pesan gagal
    }
  }
}
