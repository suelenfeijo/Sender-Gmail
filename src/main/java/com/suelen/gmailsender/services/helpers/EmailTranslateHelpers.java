package com.suelen.gmailsender.services.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.suelen.gmailsender.request.EmailRequest;
import com.suelen.gmailsender.validator.OriginConfirm;
import com.suelen.gmailsender.validator.TranslatorValidor;

@Component
//classe de métodos auxiliares para tradução de email
public class EmailTranslateHelpers {

	@Autowired
	private EmailHelpers helper;
	@Autowired
	private TranslatorValidor validator;
	@Autowired
	private OriginConfirm originConfirm;

	
	//validador para retornar tradução do email com texto original 
	public Boolean textWithOrigin(EmailRequest emailRequest)  {
		Boolean valid = validator.translateConfirm(emailRequest);
		Boolean confirm = originConfirm.originConfirm(emailRequest);

		if (valid) {
			if(confirm) {
				return true;
			}else {
				return false;
			}
		}
		return false;
	}
	//validador para retornar tradução do email sem texto original 
	public Boolean textWithoutOrigin(EmailRequest emailRequest) {
		Boolean valid = validator.translateConfirm(emailRequest);
		Boolean confirm = originConfirm.originConfirm(emailRequest);

		if (valid) {
			if(confirm) {
				return false;
			}else {
				return true;
			}
		}
		return false;
	}

}
