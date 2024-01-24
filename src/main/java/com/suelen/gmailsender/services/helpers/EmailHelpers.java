package com.suelen.gmailsender.services.helpers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.MimeType;

import com.suelen.gmailsender.request.EmailRequest;
import com.suelen.gmailsender.services.MailGmailService;
import com.suelen.gmailsender.validator.OriginConfirm;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import net.suuft.libretranslate.Language;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class EmailHelpers {

	private final static Logger LOGGER = LoggerFactory.getLogger(MailGmailService.class);

	@Autowired
	private EmailTranslateHelpers helperTranslate;

	@Autowired
	private JavaMailSender mailSender;

	public String EmailGet(EmailRequest emailRequest) {
		String titulo = emailRequest.getSubject();
		String corpo = emailRequest.getBody();
		List<String> imageUrls = emailRequest.getImageUrls();
		List<String> pdfPaths = emailRequest.getPdfPaths();
		List<String> imagePaths = emailRequest.getImagePaths();
		Boolean translate = emailRequest.getTranslate();
		return null;
	}

	@Async
	public String EmailSearchSetAttach(EmailRequest emailRequest) throws MessagingException, IOException {

		List<String> recipients = emailRequest.getRecipients();
		try {
			for (String recipient : recipients) {
				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message, true);
				
				
				//verificações de traduções
				if (helperTranslate.textWithOrigin(emailRequest)) {
					String textWithOrigin = com.suelen.gmailsender.services.TranslatorService
							.translate(Language.PORTUGUESE, Language.ENGLISH, "\n" + emailRequest.getBody() + "\n");
					helper.setText("<h1>Origin text:</h1> " + emailRequest.getBody() + " <h1>Translated text:</h1> "
							+ textWithOrigin, true); // set the body as HTML
							
							
				}else if (helperTranslate.textWithoutOrigin(emailRequest)) {
					String textWithOrigin = com.suelen.gmailsender.services.TranslatorService
							.translate(Language.PORTUGUESE, Language.ENGLISH, "\n" + emailRequest.getBody() + "\n");
					helper.setText("<h1>Translated text:</h1> " + textWithOrigin, true); // set the body as HTML
				} else {
					//seta o body do email
					helper.setText(emailRequest.getBody());
												
				}
				//para quem enviar
				helper.setTo(recipient);
				//Titulo do email
				helper.setSubject(emailRequest.getSubject());

			
				//ver mais em relembrar -> EmailHelpersDetalhado.txt
				for (String imageUrl : emailRequest.getImageUrls()) {
					URL url = new URL(imageUrl);
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setDoOutput(true);
					connection.connect();
					InputStream inputStream = connection.getInputStream();
					byte[] imageData = inputStream.readAllBytes();
					inputStream.close();
					String imageFileName = "image_" + System.currentTimeMillis() + ".png";
					MimeType mimeType = new MimeType("image", "png"); // set the correct mime type based on the URL
					FileSystemResource image = new FileSystemResource(new File(imageFileName));
					FileCopyUtils.copy(imageData, image.getFile());
					helper.addInline(image.getFilename(), image);
				}

				for (String imagePath : emailRequest.getImagePaths()) {
					FileSystemResource image = new FileSystemResource(new File(imagePath));
					helper.addInline(image.getFilename(), image);
				}

				for (String pdfPath : emailRequest.getPdfPaths()) {
					FileSystemResource pdf = new FileSystemResource(new File(pdfPath));
					helper.addAttachment(pdf.getFilename(), pdf);
				}
				mailSender.send(message);
				return "Email enviado com sucesso!";
			}
		} catch (MessagingException e) {
			LOGGER.error("falha ao enviar o email", e);
			throw new IllegalStateException("falha ao enviar email");

		}
		return "";

	}

}
