package com.trgt.casestdy.ns.commons.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Notification {
	
	/**
	 * 
	 */
	public Notification() {

	}

	public Notification(String body, String to, String cc, String subject) {

		this.subject = subject;
		this.body = body;
		if (to != null) {
			if (to.contains(",")) {
				String[] receivers = to.split(",");
				for (String r : receivers) {
					this.to.add(r);
				}
			} else {
				this.to.add(to);
			}
		}
		if (cc != null) {
			if (cc.contains(",")) {
				String[] receivers = cc.split(",");
				for (String r : receivers) {
					this.cc.add(r);
				}
			} else {
				this.cc.add(cc);
			}
		}
	}

	public Notification(String body, ArrayList<String> to, ArrayList<String> cc, String subject) {
		this.to = to;
		this.subject = subject;
		this.body = body;
		this.cc = cc;
	}

	private static final long serialVersionUID = 1L;

	private String from;

	private List<String> to = new ArrayList<String>();

	private List<String> cc = new ArrayList<String>();

	private List<String> bcc = new ArrayList<String>();

	private String subject;

	private String body;

	private NotificationType notificationMessageType;
	
	private String smtpProfileId;


	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public List<String> getCc() {
		return cc;
	}

	public void setCc(List<String> cc) {
		this.cc = cc;
	}

	public List<String> getBcc() {
		return bcc;
	}

	public void setBcc(List<String> bcc) {
		this.bcc = bcc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public NotificationType getNotificationType() {
		return notificationMessageType;
	}

	public void setNotificationType(NotificationType notificationMessageType) {
		this.notificationMessageType = notificationMessageType;
	}

	
	public String getSmtpProfileId() {
		return smtpProfileId;
	}

	public void setSmtpProfileId(String smtpProfileId) {
		this.smtpProfileId = smtpProfileId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Notification [from=");
		builder.append(from);
		builder.append(", to=");
		builder.append(to);
		builder.append(", cc=");
		builder.append(cc);
		builder.append(", bcc=");
		builder.append(bcc);
		builder.append(", subject=");
		builder.append(subject);
		builder.append(", notificationMessageType=");
		builder.append(notificationMessageType);
		builder.append("]");
		return builder.toString();
	}

	/*
	 * Similar to toString() includes "body"
	 */
	public String printNotificationVO() {
		StringBuilder builder = new StringBuilder();
		builder.append("Notification [from=");
		builder.append(from);
		builder.append(", to=");
		builder.append(to);
		builder.append(", cc=");
		builder.append(cc);
		builder.append(", bcc=");
		builder.append(bcc);
		builder.append(", subject=");
		builder.append(subject);
		builder.append(", body=");
		builder.append(body);
		builder.append(", notificationMessageType=");
		builder.append(notificationMessageType);
		builder.append("]");
		return builder.toString();
	}
	

}
