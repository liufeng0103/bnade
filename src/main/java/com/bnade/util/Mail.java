package com.bnade.util;

import java.util.Properties;

import com.qq.connect.javabeans.tenpay.Address;

public class Mail {
	public static void main(String[] args) throws Exception {
		Properties props = new Properties();

        // 开启debug调试
        props.setProperty("mail.debug", "false");
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        // 设置邮件服务器主机名
        props.setProperty("mail.host", "smtp.163.com");
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");

        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);

        Session session = Session.getInstance(props);

        Message msg = new MimeMessage(session);
        msg.setSubject("test");
        StringBuilder builder = new StringBuilder();
        builder.append("test");
        msg.setText(builder.toString());
        msg.setFrom(new InternetAddress("liufeng0103@163.com"));

        Transport transport = session.getTransport();
        transport.connect("smtp.163.com", "liufeng0103@163.com", "");

        transport.sendMessage(msg, new Address[] { new InternetAddress("liufeng0103@163.com") });
        transport.close();
       
	}
}
