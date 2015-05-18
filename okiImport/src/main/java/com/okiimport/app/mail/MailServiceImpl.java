package com.okiimport.app.mail;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

public class MailServiceImpl implements MailService {

	private VelocityEngine velocityEngine;
	private JavaMailSender mailSender;
	
	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public MailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public void send(String to, String subject, String text) {
		// TODO Auto-generated method stub
		send(to);
	}

	@Override
	public void send(String to, String subject, String text,
			File... attachments) {
		// TODO Auto-generated method stub
		send(to);
	}

	@Override
	public void send(String to, String subject, String template,
			Map<String, Object> model) {
		// TODO Auto-generated method stub
		send(to);
	}

	@Override
	public void send(String to, String subject, String template,
			Map<String, Object> model, File... attachments) {
		// TODO Auto-generated method stub
		send(to);
	}
	
	private void send(final String to){
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(new InternetAddress(to));
                message.setFrom(new InternetAddress("sgccondominios.android2@gmail.com")); // could be parameterized...
                Map<String, Object> model = new HashMap<String, Object>();
//                model.put("user", user);
                String text = VelocityEngineUtils.mergeTemplateIntoString(
                        velocityEngine, "mail_template/prueba.html", "UTF-8", model);
                System.out.println("TEXTO: "+text);
                message.setText(text, true);
            }
        };
        this.mailSender.send(preparator);
	}
   
}
