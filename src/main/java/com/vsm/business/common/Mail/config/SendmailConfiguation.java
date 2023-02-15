package com.vsm.business.common.Mail.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class SendmailConfiguation {


    @Value("${mailServer.host:smtp.office365.com}")
    private String host;

    @Value("${mailServer.port:587}")
    private Integer port;

    @Value("${mailServer.email:eoffice.vcr@2bsystem.com.vn}")
    private String email;
    
    @Value("${mailServer.username:eoffice.vcr@2bsystem.com.vn}")
    private String username;

    @Value("${mailServer.password:1q2w3e4r@@RR}")
    private String password;

    @Value("${mailServer.protocol:smtp}")
    private String protocol;

    @Value("${mailServer.isSSL:false}")
    private String isSSL;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();

        javaMailSenderImpl.setHost(this.host);
        javaMailSenderImpl.setPort(this.port);

        javaMailSenderImpl.setUsername(this.username);
        javaMailSenderImpl.setPassword(this.password);
        javaMailSenderImpl.setDefaultEncoding("UTF-8");

        Properties props = javaMailSenderImpl.getJavaMailProperties();
        props.put("mail.transport.protocol", this.protocol);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.enable", isSSL);
        props.put("mail.smtp.from", email);
        props.put("mail.debug", "true");

        return javaMailSenderImpl;
    }
}
