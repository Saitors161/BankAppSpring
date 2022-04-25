//package com.example.bank_app.service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.stereotype.Service;
//
//import com.example.bank_app.dto.PhoneDTO;
//import com.example.bank_app.model.Phone;
//import com.example.bank_app.model.User;
//import com.example.bank_app.repository.PhoneRepository;
//import com.example.bank_app.repository.UserRepository;
//
//@Service
//public class PhoneService {
//	private final PhoneRepository phoneRepository;
//	private final UserRepository userRepository;
//
//	public PhoneService(PhoneRepository phoneRepository, UserRepository userRepository) {
//		super();
//		this.phoneRepository = phoneRepository;
//		this.userRepository = userRepository;
//	}
//
//	public List<PhoneDTO> listPhonesDTO() {
//		List<Phone> phones = this.phoneRepository.findAll();
//		return getPhoneDTOFromPhone(phones);
//	}
//
//	public List<Phone> listPhones() {
//		return this.phoneRepository.findAll();
//	}
//
//	public Phone createPhone(Phone phone) {
//		return this.phoneRepository.save(phone);
//	}
//
//	public PhoneDTO createPhone(PhoneDTO phoneDTO) {
//		User user = userRepository.getById(phoneDTO.getUserId());
//		PhoneDTO returnPhoneDTO = null;
//		if (user != null) {
//			Phone phone = this.phoneRepository.save(new Phone(phoneDTO.getValue(), user));
//			returnPhoneDTO = new PhoneDTO(phone);
//		}
//		return returnPhoneDTO;
//	}
//
//	public List<PhoneDTO> getPhonesDTOByUserId(Long userId) {
//
//		List<Phone> phones = this.phoneRepository.findByUserId(userId);
//		return getPhoneDTOFromPhone(phones);
//	}
//
//	public List<Phone> getPhonesByUserId(Long userId) {
//
//		return this.phoneRepository.findByUserId(userId);
//	}
//
//	public Phone getPhoneById(Long id) {
//		Optional<Phone> phone = this.phoneRepository.findById(id);
//		if (phone.isPresent()) {
//			return phone.get();
//		}
//		return null;
//	}
//
//	public PhoneDTO getPhoneDTOById(Long id) {
//		Optional<Phone> phone = this.phoneRepository.findById(id);
//		if (phone.isPresent()) {
//			return new PhoneDTO(phone.get());
//		}
//		return null;
//	}
//
//	public Phone updatePhone(Phone phone) {
//		Phone phoneInDataBase = this.phoneRepository.findById(phone.getId()).get();
//		if (phoneInDataBase != null) {
//			boolean phoneRefreshed = refreshPhone(phone, phoneInDataBase);
//			if (phoneRefreshed) {
//				return phoneInDataBase;
//			}
//		}
//		return phone;
//	}
//
//	public PhoneDTO updatePhoneDTO(PhoneDTO phoneDTO) {
//		Phone phoneInDataBase = this.phoneRepository.findById(phoneDTO.getId()).get();
//		if (phoneInDataBase != null) {
//			boolean phoneRefreshed = refreshPhone(phoneDTO, phoneInDataBase);
//			if (phoneRefreshed) {
//				return new PhoneDTO(phoneInDataBase);
//			}
//		}
//		return phoneDTO;
//	}
//
//	private boolean refreshPhone(Phone inputPhone, Phone phoneInDataBase) {
//
//		boolean refreshed = false;
//		if (!inputPhone.equals(phoneInDataBase)) {
//			phoneInDataBase.setUser(inputPhone.getUser());
//			phoneInDataBase.setValue(inputPhone.getValue());
//			this.phoneRepository.save(phoneInDataBase);
//			refreshed = true;
//		}
//		return refreshed;
//	}
//
//	private boolean refreshPhone(PhoneDTO inputPhone, Phone phoneInDataBase) {
//		boolean refreshed = false;
//		boolean needRefresh = false;
//		if (inputPhone.getId() != phoneInDataBase.getId()) {
//			phoneInDataBase.setId(inputPhone.getId());
//			needRefresh = true;
//		}
//		if (inputPhone.getValue() != phoneInDataBase.getValue()) {
//			phoneInDataBase.setValue(inputPhone.getValue());
//			needRefresh = true;
//		}
//		if (inputPhone.getUserId() != phoneInDataBase.getUser().getId()) {
//			phoneInDataBase.setUser(this.userRepository.getById(inputPhone.getId()));
//			needRefresh = true;
//		}
//		if (needRefresh) {
//			this.phoneRepository.save(phoneInDataBase);
//			refreshed = true;
//		}
//		return refreshed;
//	}
//
//	public boolean deletePhone(Long id) {
//		this.phoneRepository.deleteById(id);
//		Phone phone = this.getPhoneById(id);
//		if (phone != null) {
//			return false;
//		} else {
//			return true;
//		}
//	}
//
//	private List<PhoneDTO> getPhoneDTOFromPhone(List<Phone> phones) {
//		List<PhoneDTO> phonesDTO = new ArrayList<>();
//		for (Phone phone : phones) {
//			PhoneDTO phoneDTO = new PhoneDTO(phone);
//			phonesDTO.add(phoneDTO);
//		}
//		return phonesDTO;
//	}
//
//}
