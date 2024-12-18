package com.example.demo.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepository;
import com.example.demo.utils.CustomResponse;

@RestController
@RequestMapping("api")
public class RoleRestController {
  private RoleRepository roleRepository;

  @Autowired
  public RoleRestController(RoleRepository roleRepository){
    this.roleRepository = roleRepository;
  }

  @GetMapping("role")
  public ResponseEntity<Object> get(){
    return CustomResponse.generate(HttpStatus.OK, "Data Berhasil Didapatkan", roleRepository.findAll());
    // return roleRepository.findAll()
  }

  @GetMapping("role/{id}")
  public ResponseEntity<Object> get(@PathVariable Integer id){
    Role role = roleRepository.findById(id).orElse(null);
    if (role != null) {
      return CustomResponse.generate(HttpStatus.OK, "Data Berhasil Didapatkan", role);
    } else {
      return CustomResponse.generate(HttpStatus.OK, "Data Tidak Ditemukan");
    }
    // return roleRepository.findById(id).orElse(null);
  }

  @PostMapping("role")
  public ResponseEntity<Object> post(@RequestBody Role role){
    return CustomResponse.generate(HttpStatus.OK, "Data Berhasil Disimpan", roleRepository.save(role));
    // return roleRepository.save(role);
  }

  @DeleteMapping("role/{id}")
  public ResponseEntity<Object> delete(@PathVariable Integer id){
    Role role = roleRepository.findById(id).orElse(null);
    if (role != null) {
      roleRepository.deleteById(id);
      return CustomResponse.generate(HttpStatus.OK, "Data Berhasil Dihapus", null);
    } else {
      return CustomResponse.generate(HttpStatus.OK, "Data Tidak Ditemukan");
    }
    // return roleRepository.findById(id).isEmpty();
  }
}
