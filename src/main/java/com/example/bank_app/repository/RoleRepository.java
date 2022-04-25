package com.example.bank_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.bank_app.model.Role;


public interface RoleRepository extends JpaRepository<Role, Long>{
	Role findByName(String name);

}
