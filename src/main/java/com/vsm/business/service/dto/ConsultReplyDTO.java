package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vsm.business.domain.ConsultReply} entity.
 */
public class ConsultReplyDTO implements Serializable {

    private Long id;

    private String content;

    private String sendName;

    private String organizationName;

    private String rankName;

    private Instant sendDate;

    private String replyName;

    private Instant replyDate;

    private String attachFileList;

    private UserInfoDTO sender;

    private UserInfoDTO replier;

    private ConsultDTO consult;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public Instant getSendDate() {
        return sendDate;
    }

    public void setSendDate(Instant sendDate) {
        this.sendDate = sendDate;
    }

    public String getReplyName() {
        return replyName;
    }

    public void setReplyName(String replyName) {
        this.replyName = replyName;
    }

    public Instant getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Instant replyDate) {
        this.replyDate = replyDate;
    }

    public String getAttachFileList() {
        return attachFileList;
    }

    public void setAttachFileList(String attachFileList) {
        this.attachFileList = attachFileList;
    }

    public UserInfoDTO getSender() {
        return sender;
    }

    public void setSender(UserInfoDTO sender) {
        this.sender = sender;
    }

    public UserInfoDTO getReplier() {
        return replier;
    }

    public void setReplier(UserInfoDTO replier) {
        this.replier = replier;
    }

    public ConsultDTO getConsult() {
        return consult;
    }

    public void setConsult(ConsultDTO consult) {
        this.consult = consult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConsultReplyDTO)) {
            return false;
        }

        ConsultReplyDTO consultReplyDTO = (ConsultReplyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, consultReplyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConsultReplyDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", sendName='" + getSendName() + "'" +
            ", organizationName='" + getOrganizationName() + "'" +
            ", rankName='" + getRankName() + "'" +
            ", sendDate='" + getSendDate() + "'" +
            ", replyName='" + getReplyName() + "'" +
            ", replyDate='" + getReplyDate() + "'" +
            ", attachFileList='" + getAttachFileList() + "'" +
            ", sender=" + getSender() +
            ", replier=" + getReplier() +
            ", consult=" + getConsult() +
            "}";
    }
}
