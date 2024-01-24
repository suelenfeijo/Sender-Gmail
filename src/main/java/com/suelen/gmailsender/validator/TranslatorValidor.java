package com.suelen.gmailsender.validator;

import org.springframework.stereotype.Component;

import com.suelen.gmailsender.request.EmailRequest;

@Component
public class TranslatorValidor {


	//Verifica se o campo translate está ativado == true (é igual a= (desejo a tradução))
	public Boolean translateConfirm(EmailRequest translate) {
		if (!translate.getTranslate()) {
			return false;
		}
		
		return true;
	}

	
	

}
