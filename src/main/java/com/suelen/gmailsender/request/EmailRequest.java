package com.suelen.gmailsender.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {
//modelo de requisição de email
	private List<String> recipients;
	  private String Subject;
	  private List<String> imageUrls;
	  private String body;
	  private List<String>  imagePaths;
	  private List<String>  pdfPaths;
	  private Boolean translate;
	  private Boolean originTranslate;

	   
	
}
