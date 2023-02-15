package com.vsm.business.common.Mail;

import com.vsm.business.domain.MailLog;

import javax.mail.internet.MimeMessage;

public class MailQueue{
    private MimeMessage mimeMessage;
    private MailLog mailLog;

    public MimeMessage getMimeMessage() {
        return mimeMessage;
    }

    public void setMimeMessage(MimeMessage mimeMessage) {
        this.mimeMessage = mimeMessage;
    }

    public MailLog getMailLog() {
        return mailLog;
    }

    public void setMailLog(MailLog mailLog) {
        this.mailLog = mailLog;
    }
}
