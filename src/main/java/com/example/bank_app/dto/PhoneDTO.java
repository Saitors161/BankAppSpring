//package com.example.bank_app.dto;
//
//import javax.validation.Valid;
//import javax.validation.constraints.Pattern;
//
//import org.springframework.validation.annotation.Validated;
//
//import com.example.bank_app.model.Phone;
//import com.fasterxml.jackson.annotation.JsonAlias;
//
//
//public class PhoneDTO {
//	private Long id;
//	
//	@Valid
//	@Pattern(regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$")
//	private String value;
//	private Long userId;
//	
//	
//	public PhoneDTO() {
//
//	}
//	
//	public PhoneDTO(Long id, String value, Long userId) {
//		this.id = id;
//		this.value = value;
//		this.userId = userId;
//	}
//	
//	public PhoneDTO(Phone phone) {
//		this.id = phone.getId();
//		this.value = phone.getValue();
//		this.userId = phone.getUser().getId();
//	}
//
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//	public String getValue() {
//		return value;
//	}
//	public void setValue(String value) {
//		this.value = value;
//	}
//	public Long getUserId() {
//		return userId;
//	}
//	public void setUserId(Long userId) {
//		this.userId = userId;
//	}
//	
//	
//	
//}
