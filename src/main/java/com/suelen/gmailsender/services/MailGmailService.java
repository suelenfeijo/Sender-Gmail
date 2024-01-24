package com.suelen.gmailsender.services;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suelen.gmailsender.interfaces.EmailInterface;
import com.suelen.gmailsender.request.EmailRequest;
import com.suelen.gmailsender.services.helpers.EmailHelpers;

@Service
//implementa um contrato (interface) respeitando um dos principios solid
public class MailGmailService implements EmailInterface{

	@Autowired
	EmailHelpers helper = new EmailHelpers() ;
	

	/*método principal que se comunica com a camada rest , é o serviço
	 * principal que envia um email para a camada rest , ele é auxiliado
	 * pelos helpers , microserviços que auxiliam na construção do fluxo de
	 * um envio de email de fato, a ideia é cada peça. ca método,
	 * juntar-se e formar uma engrenagem que colabore entre si sem afetar
	 * outras partes
	 * */
	@Override
	  public String sendEmail(EmailRequest emailRequest) throws MessagingException, IOException {
	  helper.EmailGet(emailRequest);
	  helper.EmailSearchSetAttach(emailRequest);
	  return "Email enviado com sucesso";
	}


}
