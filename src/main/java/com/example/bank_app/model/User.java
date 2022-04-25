package com.example.bank_app.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = true)

public class User extends BaseEntity {

	@Column(name = "username")
	private String username;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", 
			joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id") },
			inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id") })
	
	@ToString.Exclude
	private List<Role> roles;
	
	
}
