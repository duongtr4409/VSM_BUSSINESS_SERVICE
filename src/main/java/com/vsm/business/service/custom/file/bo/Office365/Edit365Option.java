package com.vsm.business.service.custom.file.bo.Office365;

public class Edit365Option {
    private Long sourceId;
    private Long folderTargetId;
    private Long userId;
    private String fileName;

    public Edit365Option() {
    }

    public Edit365Option(Long sourceId, Long folderTargetId, Long userId, String fileName) {
        this.sourceId = sourceId;
        this.folderTargetId = folderTargetId;
        this.userId = userId;
        this.fileName = fileName;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Long getFolderTargetId() {
        return folderTargetId;
    }

    public void setFolderTargetId(Long folderTargetId) {
        this.folderTargetId = folderTargetId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Edit365Option{" +
            "sourceId=" + sourceId +
            ", folderTargetId=" + folderTargetId +
            ", userId=" + userId +
            ", fileName='" + fileName + '\'' +
            '}';
    }
}
