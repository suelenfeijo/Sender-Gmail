package com.suelen.gmailsender.validator;

import org.springframework.stereotype.Component;

import com.suelen.gmailsender.request.EmailRequest;


@Component
public class OriginConfirm {

	
	//validador de confirmação enviar tradução + texto original
	public Boolean originConfirm(EmailRequest translate) {
		if (translate.getOriginTranslate()) {
			return true;
		}
		return false;
	}
	

	
}
