package com.vsm.business.service.mule;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerDTO implements Serializable {
    private String idocNumber;
    private String serial;
    private String partnerCode;
    private String bpClassificationGroup;
    private String partnerName;
    private String taxNumber;
    private String representIDNumber;
    private String phoneNumber;
    private String email;
    private String institute;
    private String bankCountry;
    private String bankKey;
    private String bankAccountNumber;
    private String bankAccountHolder;
    private String street;
    private String street4;
    private String street5;
    private String district;
    private String city;

    // duowngTora: trường tự thêm để thực hiện custom dữ liệu địa chỉ
    private String address;

    private String bankName;

    private String bankBranch;

    public String getIdocNumber() {
        return idocNumber;
    }

    public void setIdocNumber(String idocNumber) {
        this.idocNumber = idocNumber;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getBpClassificationGroup() {
        return bpClassificationGroup;
    }

    public void setBpClassificationGroup(String bpClassificationGroup) {
        this.bpClassificationGroup = bpClassificationGroup;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getRepresentIDNumber() {
        return representIDNumber;
    }

    public void setRepresentIDNumber(String representIDNumber) {
        this.representIDNumber = representIDNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getBankCountry() {
        return bankCountry;
    }

    public void setBankCountry(String bankCountry) {
        this.bankCountry = bankCountry;
    }

    public String getBankKey() {
        return bankKey;
    }

    public void setBankKey(String bankKey) {
        this.bankKey = bankKey;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankAccountHolder() {
        return bankAccountHolder;
    }

    public void setBankAccountHolder(String bankAccountHolder) {
        this.bankAccountHolder = bankAccountHolder;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet4() {
        return street4;
    }

    public void setStreet4(String street4) {
        this.street4 = street4;
    }

    public String getStreet5() {
        return street5;
    }

    public void setStreet5(String street5) {
        this.street5 = street5;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String address() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }
}
