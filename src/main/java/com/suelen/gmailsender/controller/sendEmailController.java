package com.suelen.gmailsender.controller;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suelen.gmailsender.request.EmailRequest;
import com.suelen.gmailsender.services.MailGmailService;

import lombok.AllArgsConstructor;


@RestController
@AllArgsConstructor
@RequestMapping(path = "api/v1/registration")
public class sendEmailController {

	
/*Camada rest da api*/	
	
	@Autowired
	private MailGmailService service;
	
	//método que se comunica com o client -> envia um email
	@PostMapping(value = "/sendWithUrl")
	public String emailSendUrlImg (@RequestBody EmailRequest emailRequest) throws MessagingException, IOException {
	return service.sendEmail(emailRequest);
	}
	
		  
		   
	
	
	
}
