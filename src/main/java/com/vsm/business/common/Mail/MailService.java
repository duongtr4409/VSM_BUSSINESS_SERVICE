package com.vsm.business.common.Mail;

import com.vsm.business.domain.MailLog;
import com.vsm.business.repository.MailLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class MailService {

    private Logger log = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private JavaMailSender mailSender;

    private final String ENCODING = "utf-8";

    @Value("${graph.mail.sendNum:5}")
    private Long sendNum = 5L;

    @Value("${graph.mail.delimiter:\\$}")
    private String mailDelimiter = "\\$";

    @Value("${mailServer.email:eoffice.vcr@2bsystem.com.vn}")
    private String MAIL_FROM;

    private Queue<MailQueue> mailQueues = new LinkedList<>();

    @Autowired
    private MailLogRepository mailLogRepository;

    public MailService() {

    }

    @EventListener(ApplicationReadyEvent.class)
    private void init() {
        List<MailLog> mailLogs = mailLogRepository.findAllByIsSucessNotAndSendCountLessThan(true, sendNum);
        log.info("init: total={}", mailLogs.size());
        for (MailLog mailLog :
            mailLogs) {
            try {
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, ENCODING);
                MailQueue mailQueue = new MailQueue();
                helper.setText(mailLog.getMailContent(), true);
                helper.setSubject(mailLog.getMailTitle());
                helper.setFrom(MAIL_FROM);
                helper.setReplyTo(MAIL_FROM);
                String[] emailTo = Arrays.stream(mailLog.getReceiveEmail().split(mailDelimiter)).filter(e -> {
                    if (org.elasticsearch.common.Strings.isNullOrEmpty(e)) {
                        return false;
                    }
                    try {
                        InternetAddress internetAddress = new InternetAddress(e);
                        internetAddress.validate();
                        return true;
                    } catch (AddressException exception) {
                        log.error("sendMail: error={}", exception);
                        return false;
                    }
                }).toArray(String[]::new);
                helper.setTo(emailTo);
                String[] emailCC = Arrays.stream(mailLog.getCcEmail().split(mailDelimiter)).filter(e -> {
                    if (org.elasticsearch.common.Strings.isNullOrEmpty(e)) {
                        return false;
                    }
                    try {
                        InternetAddress internetAddress = new InternetAddress(e);
                        internetAddress.validate();
                        return true;
                    } catch (AddressException exception) {
                        log.error("sendMail: error={}", exception);
                        return false;
                    }
                }).toArray(String[]::new);
                helper.setCc(emailCC);
                mailQueue.setMailLog(mailLog);
                mailQueue.setMimeMessage(mimeMessage);
                mailQueues.offer(mailQueue);
            } catch (MessagingException e) {
                log.error("MailService(): mailLog={}, error={}", mailLogs, e);
            }
        }
    }

    public void sendmail() throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, ENCODING);

        helper.setText("DuowngToraaaaaaaaaaaa", true);

        helper.setSubject("SENDMAIL DUOWNGTORA SUBJECT");

        helper.setFrom("eoffice.vcr@2bsystem.com.vn");

        helper.setReplyTo("eoffice.vcr@2bsystem.com.vn");

        String[] emailTo = {"vanduong.nguyen@10dd23.onmicrosoft.com"};
        helper.setTo(emailTo);

        mailSender.send(message);
    }

    public void sendMailV2() throws MessagingException, UnsupportedEncodingException {

        Properties props = new Properties();
        props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.host", "m.outlook.com");
        props.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("eoffice.vcr@2bsystem.com.vn",
                    "1q2w3e4r@@RR");
            }
        });

        MimeMessage msg = new MimeMessage(session);
        msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
        msg.addHeader("format", "flowed");
        msg.addHeader("Content-Transfer-Encoding", "8bit");

        msg.setFrom(new InternetAddress("eoffice.vcr@2bsystem.com.vn", "NoReply-JD"));
        msg.setReplyTo(InternetAddress.parse("vanduong.nguyen@10dd23.onmicrosoft.com", false));
        msg.setSubject("DuowngTora Subject", "UTF-8");

        msg.setText("DuowngTora Bodyyyyyyyyyyyyyy", "UTF-8");

        msg.setSentDate(new Date());

        String toEmail = "vanduong.nguyen@10dd23.onmicrosoft.com";
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
        System.out.println("Message is ready");
        Transport.send(msg);

        System.out.println("EMail Sent Successfully!!");
    }

    @Scheduled(fixedDelay = 60000) // 60s
    public void sendMailSchedule() {
        log.info("sendMailSchedule total mail in queue: {}", this.mailQueues.size());
        if (mailQueues.isEmpty()) {
            try {
                System.gc();        // dọn garbage collectors
            }catch (Exception e){
                log.error("{}", e);
            }
            return;
        }
        MailQueue mailQueue = null;
        int totalSend = 0;
        int success = 0;
        int fail = 0;
        while (!mailQueues.isEmpty() && totalSend < sendNum) {
            mailQueue = mailQueues.poll();
            totalSend++;
            try {
                if(mailQueue.getMailLog().getSendCount() > sendNum){
                    continue;
                }
                mailSender.send(mailQueue.getMimeMessage());
                saveSuccessMail(mailQueue.getMailLog());
                success++;
            } catch (MailAuthenticationException e) {
                log.error("sendMailSchedule: {}", e);
                mailQueues.offer(mailQueue);
                saveErrorMail(mailQueue.getMailLog());
                fail++;
            } catch (MailSendException e) {
                log.error("sendMailSchedule: {}", e);
                mailQueues.offer(mailQueue);
                saveErrorMail(mailQueue.getMailLog());
                fail++;
            } catch (MailException e) {
                log.error("sendMailSchedule: {}", e);
                mailQueues.offer(mailQueue);
                saveErrorMail(mailQueue.getMailLog());
                fail++;
            }
        }
        log.info("sendMailSchedule total : {}, success: {}, fail: {}", totalSend, success, fail);

        try {
            System.gc();        // dọn garbage collectors
        }catch (Exception e){
            log.error("{}", e);
        }
    }

    private void saveSuccessMail(MailLog mailLog) {
        saveMailLog(mailLog, true);
    }

    private void saveErrorMail(MailLog mailLog) {
        saveMailLog(mailLog, false);
    }

    private void saveMailLog(MailLog mailLog, boolean success) {
        Instant now = Instant.now();
        mailLog.setIsSucess(success);
        mailLog.setSendDate(now);
        mailLog.setSendCount(mailLog.getSendCount() + 1);
        mailLogRepository.save(mailLog);
    }

    public JavaMailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String getENCODING() {
        return ENCODING;
    }

    public Long getSendNum() {
        return sendNum;
    }

    public void setSendNum(Long sendNum) {
        this.sendNum = sendNum;
    }

    public String getMailDelimiter() {
        return mailDelimiter;
    }

    public void setMailDelimiter(String mailDelimiter) {
        this.mailDelimiter = mailDelimiter;
    }

    public Queue<MailQueue> getMailQueues() {
        return mailQueues;
    }

    public void setMailQueues(Queue<MailQueue> mailQueues) {
        this.mailQueues = mailQueues;
    }

    public MailLogRepository getMailLogRepository() {
        return mailLogRepository;
    }

    public void setMailLogRepository(MailLogRepository mailLogRepository) {
        this.mailLogRepository = mailLogRepository;
    }
}
