package com.cdac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender sendMail;
	
	public void sendEmailForNewRegistration(String email) 
	{
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setFrom("info.response.goodluck@gmail.com");
		message.setTo(email);
		message.setSubject("Registration Mail !!  Successfully Registered");
		message.setText("Thank You for Registering With Us! Welcome to City Health Care Portal");
		sendMail.send(message);
		System.out.println("mail sent");
	}
	}
