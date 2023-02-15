package com.vsm.business.service.custom.file.bo;

import java.util.Objects;

public class FileRq {
    String fileId;
    String folderId;
    String fileName;

    public FileRq() {
    }

    public FileRq(String fileId, String folderId, String fileName) {
        this.fileId = fileId;
        this.folderId = folderId;
        this.fileName = fileName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileRq fileRq = (FileRq) o;
        return Objects.equals(fileId, fileRq.fileId) && Objects.equals(folderId, fileRq.folderId) && Objects.equals(fileName, fileRq.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileId, folderId, fileName);
    }

    @Override
    public String toString() {
        return "FileRq{" +
            "fileId='" + fileId + '\'' +
            ", folderId='" + folderId + '\'' +
            ", fileName='" + fileName + '\'' +
            '}';
    }
}
