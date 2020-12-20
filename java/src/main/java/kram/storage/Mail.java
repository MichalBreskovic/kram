package kram.storage;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {
	
	public static String codeGenerator(int codeLength) {
		char[] znaky = new char[61];
		String code = "";
		int u = 0;
		for(int i = 48; i < 58; i++) {
			znaky[u] = (char)i;
			u++;
		}
		for(int i = 65; i < 91; i++) {
			znaky[u] = (char)i;
			u++;
		}	
		for(int i = 97; i < 122; i++) {
			znaky[u] = (char)i;
			u++;
		}
		for (int i = 0; i < codeLength; i++) {
			code += znaky[(int)(Math.random() * 61)];
		}
		return code;
	}
	
	
	// https://jaxenter.com/java-app-emails-smtp-server-164144.html
	public static String send(String email) {	
		System.out.println("Sending registration email to " + email);
		
		String from = "kram.paz.app@gmail.com";
		final String username = "kram.paz.app@gmail.com";
		final String password = "m9TBqahvjE";
	
		String host = "smtp.gmail.com";
	
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true"); 
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");
	
		Session session = Session.getInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
	
		try {
			Message message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress(from));
			
			message.setRecipients(Message.RecipientType.TO,
			InternetAddress.parse(email));
			
			message.setSubject("Registration code");
			
			String registrationCode = codeGenerator(6);
			
			message.setText("Hi there,\nyour registration code is\n" + registrationCode + "\n\n"
					+ "This is automaticaly generated email, please do not respond.\n"
					+ "\n"
					+ "Developers from Kram\n");
			
			Transport.send(message);
		
			System.out.println("Sent email successfully....");
			return registrationCode;
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
}
