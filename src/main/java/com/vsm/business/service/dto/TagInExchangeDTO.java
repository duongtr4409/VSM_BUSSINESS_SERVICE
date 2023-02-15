package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vsm.business.domain.TagInExchange} entity.
 */
public class TagInExchangeDTO implements Serializable {

    private Long id;

    private String tagerName;

    private String tagerOrg;

    private String tagerRank;

    private String taggedName;

    private String taggedOrg;

    private String taggedRank;

    private String requestDataCode;

    private Instant tagDate;

    private UserInfoDTO tager;

    private UserInfoDTO tagged;

    private RequestDataDTO requestData;

    private InformationInExchangeDTO informationInExchange;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagerName() {
        return tagerName;
    }

    public void setTagerName(String tagerName) {
        this.tagerName = tagerName;
    }

    public String getTagerOrg() {
        return tagerOrg;
    }

    public void setTagerOrg(String tagerOrg) {
        this.tagerOrg = tagerOrg;
    }

    public String getTagerRank() {
        return tagerRank;
    }

    public void setTagerRank(String tagerRank) {
        this.tagerRank = tagerRank;
    }

    public String getTaggedName() {
        return taggedName;
    }

    public void setTaggedName(String taggedName) {
        this.taggedName = taggedName;
    }

    public String getTaggedOrg() {
        return taggedOrg;
    }

    public void setTaggedOrg(String taggedOrg) {
        this.taggedOrg = taggedOrg;
    }

    public String getTaggedRank() {
        return taggedRank;
    }

    public void setTaggedRank(String taggedRank) {
        this.taggedRank = taggedRank;
    }

    public String getRequestDataCode() {
        return requestDataCode;
    }

    public void setRequestDataCode(String requestDataCode) {
        this.requestDataCode = requestDataCode;
    }

    public Instant getTagDate() {
        return tagDate;
    }

    public void setTagDate(Instant tagDate) {
        this.tagDate = tagDate;
    }

    public UserInfoDTO getTager() {
        return tager;
    }

    public void setTager(UserInfoDTO tager) {
        this.tager = tager;
    }

    public UserInfoDTO getTagged() {
        return tagged;
    }

    public void setTagged(UserInfoDTO tagged) {
        this.tagged = tagged;
    }

    public RequestDataDTO getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestDataDTO requestData) {
        this.requestData = requestData;
    }

    public InformationInExchangeDTO getInformationInExchange() {
        return informationInExchange;
    }

    public void setInformationInExchange(InformationInExchangeDTO informationInExchange) {
        this.informationInExchange = informationInExchange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TagInExchangeDTO)) {
            return false;
        }

        TagInExchangeDTO tagInExchangeDTO = (TagInExchangeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tagInExchangeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TagInExchangeDTO{" +
            "id=" + getId() +
            ", tagerName='" + getTagerName() + "'" +
            ", tagerOrg='" + getTagerOrg() + "'" +
            ", tagerRank='" + getTagerRank() + "'" +
            ", taggedName='" + getTaggedName() + "'" +
            ", taggedOrg='" + getTaggedOrg() + "'" +
            ", taggedRank='" + getTaggedRank() + "'" +
            ", requestDataCode='" + getRequestDataCode() + "'" +
            ", tagDate='" + getTagDate() + "'" +
            ", tager=" + getTager() +
            ", tagged=" + getTagged() +
            ", requestData=" + getRequestData() +
            ", informationInExchange=" + getInformationInExchange() +
            "}";
    }
}
