package com.example.bank_app.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Phone {

	private Long id;

	private String value;

	private User user;

}
