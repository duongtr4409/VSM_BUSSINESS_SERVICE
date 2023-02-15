package com.vsm.business.service.mule;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.time.Instant;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VibdroDTO implements Serializable {
    private String idocNumber;
    private String serial;
    private String roKey;
    private String roObjectNumber;
    private String companyCode;
    private String businessEntity;
    private String building;
    private String floor;
    private String locationOnFloor;
    private String locationOnFloorDescription;
    private String rentalObject;
    private String rentalObjectName;
    private String pooledSpace;
    private String usageTypeOfRentalUnit;
    private Instant objectValidFrom;
    private Instant objectValidTo;
    private String rentalObjectTypeName;
    private Double rentalUnitPrice;
    private Double budgetServiceFeeUnitPrice;
    private String measureType;
    private Double measureValue;
    private String bankName;
    private String bankBranch;

    private String objectId;

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

    public String getRoKey() {
        return roKey;
    }

    public void setRoKey(String roKey) {
        this.roKey = roKey;
    }

    public String getRoObjectNumber() {
        return roObjectNumber;
    }

    public void setRoObjectNumber(String roObjectNumber) {
        this.roObjectNumber = roObjectNumber;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getBusinessEntity() {
        return businessEntity;
    }

    public void setBusinessEntity(String businessEntity) {
        this.businessEntity = businessEntity;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getLocationOnFloor() {
        return locationOnFloor;
    }

    public void setLocationOnFloor(String locationOnFloor) {
        this.locationOnFloor = locationOnFloor;
    }

    public String getLocationOnFloorDescription() {
        return locationOnFloorDescription;
    }

    public void setLocationOnFloorDescription(String locationOnFloorDescription) {
        this.locationOnFloorDescription = locationOnFloorDescription;
    }

    public String getRentalObject() {
        return rentalObject;
    }

    public void setRentalObject(String rentalObject) {
        this.rentalObject = rentalObject;
    }

    public String getRentalObjectName() {
        return rentalObjectName;
    }

    public void setRentalObjectName(String rentalObjectName) {
        this.rentalObjectName = rentalObjectName;
    }

    public String getPooledSpace() {
        return pooledSpace;
    }

    public void setPooledSpace(String pooledSpace) {
        this.pooledSpace = pooledSpace;
    }

    public String getUsageTypeOfRentalUnit() {
        return usageTypeOfRentalUnit;
    }

    public void setUsageTypeOfRentalUnit(String usageTypeOfRentalUnit) {
        this.usageTypeOfRentalUnit = usageTypeOfRentalUnit;
    }

    public Instant getObjectValidFrom() {
        return objectValidFrom;
    }

    public void setObjectValidFrom(Instant objectValidFrom) {
        this.objectValidFrom = objectValidFrom;
    }

    public Instant getObjectValidTo() {
        return objectValidTo;
    }

    public void setObjectValidTo(Instant objectValidTo) {
        this.objectValidTo = objectValidTo;
    }

    public String getRentalObjectTypeName() {
        return rentalObjectTypeName;
    }

    public void setRentalObjectTypeName(String rentalObjectTypeName) {
        this.rentalObjectTypeName = rentalObjectTypeName;
    }

    public Double getRentalUnitPrice() {
        return rentalUnitPrice;
    }

    public void setRentalUnitPrice(Double rentalUnitPrice) {
        this.rentalUnitPrice = rentalUnitPrice;
    }

    public Double getBudgetServiceFeeUnitPrice() {
        return budgetServiceFeeUnitPrice;
    }

    public void setBudgetServiceFeeUnitPrice(Double budgetServiceFeeUnitPrice) {
        this.budgetServiceFeeUnitPrice = budgetServiceFeeUnitPrice;
    }

    public String getMeasureType() {
        return measureType;
    }

    public void setMeasureType(String measureType) {
        this.measureType = measureType;
    }

    public Double getMeasureValue() {
        return measureValue;
    }

    public void setMeasureValue(Double measureValue) {
        this.measureValue = measureValue;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
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
