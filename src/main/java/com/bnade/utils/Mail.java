package com.bnade.utils;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mail {

	private static final Logger log = LoggerFactory.getLogger(Mail.class);

	private final static String EMAIL_HOSTNAME = BnadeProperties
			.getValue("email_hostname");// 邮箱服务器，如：smtp.qq.com
	private final static String EMAIL_USERNAME = BnadeProperties
			.getValue("email_username");// 邮箱账户，如：xxx@qq.com
	private final static String EMAIL_PASSWORD = BnadeProperties
			.getValue("email_password");// 邮箱密码
	private final static String EMAIL_NAME = BnadeProperties
			.getValue("email_name");// 发件人名称（昵称）
	private final static String EMAIL_FROM = BnadeProperties
			.getValue("email_from");
	private final static boolean EMAIL_IS_SSL = "true"
			.equalsIgnoreCase(BnadeProperties.getValue("email_is_ssl"));// 是否支持SSL链接

	public static void sendHtmlEmail(String subject, String msg, String to) {
		try {
			HtmlEmail email = new HtmlEmail();
			email.setHostName(EMAIL_HOSTNAME);
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator(EMAIL_USERNAME,
					EMAIL_PASSWORD));
			email.setSSLOnConnect(EMAIL_IS_SSL);
			email.setFrom(EMAIL_FROM, EMAIL_NAME);
			email.setSubject(subject);
			email.setHtmlMsg(msg);
			email.setCharset("utf-8");
			email.setTextMsg("您的邮箱不支持html邮件");
			email.addTo(to);
			email.send();
			log.info("email sent success!");
		} catch (EmailException e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	}
	
	public static void sendSimpleEmail(String subject, String msg, String to) {
		try {
			Email email = new SimpleEmail();
			email.setHostName(EMAIL_HOSTNAME);
//			email.setSmtpPort(25);
			email.setAuthenticator(new DefaultAuthenticator(EMAIL_USERNAME, EMAIL_PASSWORD));
			email.setSSLOnConnect(EMAIL_IS_SSL);
			email.setFrom(EMAIL_FROM, EMAIL_NAME);
			email.setSubject(subject);
			email.setMsg(msg);
			email.addTo(to);
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	}

	public static void main(String[] args) {
		Mail.sendSimpleEmail("test", "test", "liufeng0103@163.com");
		Mail.sendHtmlEmail("test", "<a href='http://www.bnade.com'>test1</a>", "liufeng0103@163.com");
	}

}
