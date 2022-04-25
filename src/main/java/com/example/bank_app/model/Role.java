package com.example.bank_app.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name="roles")
@Data
@EqualsAndHashCode(callSuper=true)
public class Role extends BaseEntity {
	@Column(name = "name")
	private String name;
	
	@ToString.Exclude
	@ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
	private List<User> users;	
	
}
