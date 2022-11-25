package com.multishop.serviceImples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.multishop.dtos.EmailDto;
import com.multishop.services.EmailService;

@Service
public class EmailServiceImple implements EmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public String sendEmail(EmailDto emailDto) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("ermaanish@gmail.com");
		message.setTo("ermaanish@gmail.com");
		message.setSubject(emailDto.getSubject());
		message.setReplyTo(emailDto.getFrom_email());
		message.setText(emailDto.getMessage());
		this.javaMailSender.send(message);
		return "Message sent successfully";
	}

}
