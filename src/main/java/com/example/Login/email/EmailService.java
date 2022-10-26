package com.example.Login.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender{
	
	private final JavaMailSender mailSender;
	private final static Logger logger = LoggerFactory.getLogger(EmailService.class);
	
	

	public EmailService(JavaMailSender mailSender) {
	
		this.mailSender = mailSender;
	}



	@Override
	public void send(String to, String email) {
		
		
	}

}
