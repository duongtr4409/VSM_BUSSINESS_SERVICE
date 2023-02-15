package com.vsm.business.service.custom.file.bo;

import java.util.Date;

public class SignatureInfo {
    private Actor actor;
    private Date signDate;
    private String note;

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Actor getActor() {
        return actor;
    }

    public Date getSignDate() {
        return signDate;
    }

    public String getNote() {
        return note;
    }

    public SignatureInfo() {
    }

    public SignatureInfo(Actor actor, Date signDate, String note) {
        this.actor = actor;
        this.signDate = signDate;
        this.note = note;
    }
}
