package com.okiimport.app.mail;

import java.io.File;  
import java.util.Map;
  
public interface MailService {

	public void send(String to, String subject, String text);  

	public void send(String to, String subject, String text, File... attachments);  

	public void send(String to, String subject, String template, Map<String, Object> model);

	public void send(String to, String subject, String template, Map<String, Object> model, 
			File... attachments);

}  