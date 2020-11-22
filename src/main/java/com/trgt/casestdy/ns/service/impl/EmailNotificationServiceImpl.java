package com.trgt.casestdy.ns.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.trgt.casestdy.ns.commons.model.Notification;
import com.trgt.casestdy.ns.commons.model.NotificationType;
import com.trgt.casestdy.ns.exception.NotSentException;
import com.trgt.casestdy.ns.service.NotificationService;
import com.trgt.casestdy.ns.util.Constants;
import com.trgt.casestdy.ns.util.RetryPolicyFactory;
import com.trgt.casestdy.ns.util.SMTPConfig;

import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.RetryPolicy;

@Service
public class EmailNotificationServiceImpl implements NotificationService{

	@Autowired
	SMTPConfig config;
	
	@Value("${email.reciepient.limit}")
	Integer emailreciepientLimit;
	
	@Value("${email.mock.sender}")
	boolean mockSender;
	
	private static Executor executor = Executors.newFixedThreadPool(10);
	
	private static final Logger log = LoggerFactory.getLogger(EmailNotificationServiceImpl.class);
	
	@Override
	public void send(Notification notification) {
		RetryPolicy<Object> retryPolicy = RetryPolicyFactory.getRetryPolicy(NotificationType.EMAIL);
		CompletableFuture.supplyAsync(() -> Failsafe.with(retryPolicy).get(() -> sendEmail(notification)),executor);
	}

	
	private boolean sendEmail(Notification notification) {
		if(mockSender) {
			log.info("Mock send is enabled. Not sending email.");
			try {
				Thread.sleep(100L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			return true;
		}
		// get smtp properties
		Properties smtpProps = getSMTPProperties(notification);
		// get mailSession
		Session session = getSession(smtpProps);
		// build message
		Message  message = getNewMessage(session, notification, smtpProps);
		//send message based on limit
		sendMessage(message,notification,session, smtpProps);
		return true;
	}


	private void sendMessage(Message message, Notification notification, Session session, Properties props) {
		try {
			if (notification.getTo() != null) {
				message = createMessageSendEmailIfLimitReached(notification.getTo(), RecipientType.TO, message,
						notification, session, props);
			}
			if (notification.getCc() != null) {
				message = createMessageSendEmailIfLimitReached(notification.getCc(), RecipientType.CC, message,
						notification, session, props);
			}
			if (notification.getBcc() != null) {
				message = createMessageSendEmailIfLimitReached(notification.getBcc(), RecipientType.BCC, message,
						notification, session, props);
			}
			if (message.getAllRecipients().length > 0) {
				// Means there are still recipients for whom the mail has not been sent
				Transport.send(message);
				log.info("Mail sent successfully");
			}
		} catch (MessagingException e) {
			log.error("Exception occurred while sending the	 mail: ", e);
			throw new NotSentException("Exception occurred while sending the mail");
		}
		
	}

	public Message createMessageSendEmailIfLimitReached(List<String> addresses, RecipientType type, Message message,
			Notification notificationVO, Session session, Properties props) throws MessagingException {
		List<InternetAddress> internetAddressList = new ArrayList<>();
		int count = (message.getAllRecipients() != null)? message.getAllRecipients().length: 0;
		for (String address : addresses) {
			internetAddressList.add(new InternetAddress(address));
			count++;
			if (count == emailreciepientLimit) {
				message.setRecipients(type,
						internetAddressList.toArray(new InternetAddress[internetAddressList.size()]));
				Transport.send(message);
				log.info("Mail sent successfully as limit reached");
				message = getNewMessage(session, notificationVO, props);
				count = 0;
				internetAddressList.clear();
			}
		}

		if (count > 0) {
			message.setRecipients(type, internetAddressList.toArray(new InternetAddress[internetAddressList.size()]));
		}

		return message;

	}
	private Session getSession(Properties smtpProps) {
		return Session.getInstance(smtpProps, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(smtpProps.getProperty(Constants.MAIL_SMTP_USER), smtpProps.getProperty(Constants.MAIL_SMTP_PASSWORD));
			}
		});
	}


	private Properties getSMTPProperties(Notification notification) {
		Properties props = new Properties();
		if(null==notification.getSmtpProfileId()||notification.getSmtpProfileId().trim().equals("")) {
			props.putAll(config.getDefaultSMTPProperties());
		}else {
			//getProfileFromDb
		}
		return props;
	}

	private Message getNewMessage(Session session, Notification notification, Properties props) {
		Message msg = new MimeMessage(session);
		InternetAddress fromAddress = null;
		try {
			fromAddress = new InternetAddress(notification.getFrom());
		} catch (AddressException e) {
			throw new RuntimeException(e);
		}
		
		if (StringUtils.isNotBlank(props.get(Constants.MAIL_SMTP_FROM).toString())) {
			try {
				fromAddress.setPersonal(props.get(Constants.MAIL_SMTP_FROM).toString());
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		try {
			msg.setFrom(fromAddress);
			msg.setSubject(notification.getSubject());
			msg.setContent(notification.getBody(), Constants.EMAIL_CONTENTTYPE_HTML);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return msg;
	}
}
