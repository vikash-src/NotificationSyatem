package com.trgt.casestdy.ns.util;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:smtpConfig.properties")
public class SMTPConfig {
	
	@Autowired
    private Environment env;

	Properties props = new Properties();
	
    public Properties getDefaultSMTPProperties() {
        props.put(Constants.MAIL_SMTP_FROM, env.getProperty(Constants.MAIL_SMTP_SSL_TRUST));
        props.put(Constants.MAIL_SMTP_USER, env.getProperty(Constants.MAIL_SMTP_USER));
        props.put(Constants.MAIL_SMTP_PASSWORD, env.getProperty(Constants.MAIL_SMTP_PASSWORD));
        props.put(Constants.MAIL_SMTP_HOST, env.getProperty(Constants.MAIL_SMTP_HOST));
        props.put(Constants.MAIL_SMTP_PORT, env.getProperty(Constants.MAIL_SMTP_PORT));
        props.put(Constants.MAIL_SMTP_STARTTLS_ENABLE, env.getProperty(Constants.MAIL_SMTP_STARTTLS_ENABLE));
        props.put(Constants.MAIL_SMTP_AUTH, env.getProperty(Constants.MAIL_SMTP_AUTH));
        props.put(Constants.MAIL_SMTP_SSL_TRUST, env.getProperty(Constants.MAIL_SMTP_SSL_TRUST)); 
        return props;
    }
}
