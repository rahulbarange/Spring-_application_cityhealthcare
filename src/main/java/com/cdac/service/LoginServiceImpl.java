package com.cdac.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.cdac.dto.Loginres;
import com.cdac.entity.Admin;
import com.cdac.entity.Hospital;
import com.cdac.entity.User;
import com.cdac.repository.AdminRepository;
import com.cdac.repository.HospitalRepository;
import com.cdac.repository.UserRepository;

@Service
public class LoginServiceImpl implements LoginServiceIntf {
	
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	HospitalRepository hospitalRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	EncryptService encryptService;
	
	@Override
	public Loginres authenticateUser(String email, String password) {
			try {
				Admin admin = adminRepository.findByEmail(email)
						.orElseThrow(() -> new RuntimeException("User with email: " + email + " doesn't exist."));
				if (password.equals(admin.getPassword())) {
					return new Loginres(admin.getId(), admin.getName(), "admin");
				}else {
					throw new Exception();
				}
			} catch (Exception e) {
				System.out.println("msg : " + e.getMessage());
			}

			try {
				Hospital hospital = hospitalRepository.findByEmail(email)
						.orElseThrow(() -> new RuntimeException("User with email: " + email + " doesn't exist."));
				
				String sp = hospital.getPassword();
				System.out.println(sp);
				String encryptPassword=null;
				try {
				encryptPassword = encryptService.toHexString(encryptService.getSHA(password));
				//user.setPassword(encryptPassword);
				}catch(NoSuchAlgorithmException e) {
					 System.out.println("Exception thrown for incorrect algorithm: " + e);
				}
				if(encryptPassword.equals(sp)) {
					return new Loginres(hospital.getHospid(), hospital.getHospitalname(), "hospital");
				}

			} catch (Exception e) {
				System.out.println("msg : " + e.getMessage());
			}

			try {
				User user = userRepository.findByEmail(email)
						.orElseThrow(() -> new RuntimeException("User with email: " + email + " doesn't exist."));
				
				String sp = user.getPassword();
				System.out.println(sp);
				String encryptPassword=null;
				try {
				encryptPassword = encryptService.toHexString(encryptService.getSHA(password));
				System.out.println(encryptPassword);
				}catch(NoSuchAlgorithmException e) {
					 System.out.println("Exception thrown for incorrect algorithm: " + e);
				}
				if(encryptPassword.equals(sp)) {
					return new Loginres(user.getUserid(), user.getName(), "user");
				}
				

			} catch (Exception e) {
				System.out.println("msg : " + e.getMessage());
			}
	
			//try {
			throw new RuntimeException("Enter correct Email or password!!!");
			//}catch(RuntimeException RE) {
			//	return new Loginres("Enter correct Email or password!!!");
			//}
		}

	}

