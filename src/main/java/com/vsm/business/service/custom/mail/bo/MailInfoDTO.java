package com.vsm.business.service.custom.mail.bo;

import java.io.File;
import java.nio.file.Path;
import java.util.*;

public class MailInfoDTO {
    private String emailAddressFrom;
    private Long requestDataId;
    private List<String> emailAddressTo = new ArrayList<>();
    private List<String> emailAddressCC = new ArrayList<>();
    private List<String> emailAddressBCC = new ArrayList<>();
    private String templateName;
    private String subject;
    private String content;

    private Boolean isAddFile;

    private Boolean isAddOTP;
    private List<File> attachmentFile = new ArrayList<>();
    private Map<String, Object> props = new HashMap<>();

    public MailInfoDTO() {
    }

    public MailInfoDTO(String emailAddressFrom, Long requestDataId, List<String> emailAddressTo, List<String> emailAddressCC, List<String> emailAddressBCC, String templateName, String subject, String content, Boolean isAddFile, Boolean isAddOTP, List<File> attachmentFile, Map<String, Object> props) {
        this.emailAddressFrom = emailAddressFrom;
        this.requestDataId = requestDataId;
        this.emailAddressTo = emailAddressTo;
        this.emailAddressCC = emailAddressCC;
        this.emailAddressBCC = emailAddressBCC;
        this.templateName = templateName;
        this.subject = subject;
        this.content = content;
        this.isAddFile = isAddFile;
        this.isAddOTP = isAddOTP;
        this.attachmentFile = attachmentFile;
        this.props = props;
    }

    public String getEmailAddressFrom() {
        return emailAddressFrom;
    }

    public void setEmailAddressFrom(String emailAddressFrom) {
        this.emailAddressFrom = emailAddressFrom;
    }

    public Long getRequestDataId() {
        return requestDataId;
    }

    public void setRequestDataId(Long requestDataId) {
        this.requestDataId = requestDataId;
    }

    public List<String> getEmailAddressTo() {
        return emailAddressTo;
    }

    public void setEmailAddressTo(List<String> emailAddressTo) {
        this.emailAddressTo = emailAddressTo;
    }

    public List<String> getEmailAddressCC() {
        return emailAddressCC;
    }

    public void setEmailAddressCC(List<String> emailAddressCC) {
        this.emailAddressCC = emailAddressCC;
    }

    public List<String> getEmailAddressBCC() {
        return emailAddressBCC;
    }

    public void setEmailAddressBCC(List<String> emailAddressBCC) {
        this.emailAddressBCC = emailAddressBCC;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getAddFile() {
        return isAddFile;
    }

    public void setAddFile(Boolean addFile) {
        isAddFile = addFile;
    }

    public Boolean getAddOTP() {
        return isAddOTP;
    }

    public void setAddOTP(Boolean addOTP) {
        isAddOTP = addOTP;
    }

    public List<File> getAttachmentFile() {
        return attachmentFile;
    }

    public void setAttachmentFile(List<File> attachmentFile) {
        this.attachmentFile = attachmentFile;
    }

    public Map<String, Object> getProps() {
        return props;
    }

    public void setProps(Map<String, Object> props) {
        this.props = props;
    }

    @Override
    public String toString() {
        return "MailInfoDTO{" +
            "emailAddressFrom='" + emailAddressFrom + '\'' +
            ", requestDataId=" + requestDataId +
            ", emailAddressTo=" + emailAddressTo +
            ", emailAddressCC=" + emailAddressCC +
            ", emailAddressBCC=" + emailAddressBCC +
            ", templateName='" + templateName + '\'' +
            ", subject='" + subject + '\'' +
            ", content='" + content + '\'' +
            ", isAddFile=" + isAddFile +
            ", isAddOTP=" + isAddOTP +
            ", attachmentFile=" + attachmentFile +
            ", props=" + props +
            '}';
    }
}
