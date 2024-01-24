package com.suelen.gmailsender.interfaces;

import java.io.IOException;

import javax.mail.MessagingException;

import com.suelen.gmailsender.request.EmailRequest;

public interface EmailInterface {

	
	//ler mais sobre interfaces na pasta relembrar -> interfaces.txt
	  default String sendEmail(EmailRequest mail)throws MessagingException, IOException {
		  return "";
		  }
	  
	  
	  	
}
