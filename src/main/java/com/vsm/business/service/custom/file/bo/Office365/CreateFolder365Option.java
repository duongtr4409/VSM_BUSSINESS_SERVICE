package com.vsm.business.service.custom.file.bo.Office365;

public class CreateFolder365Option {
    private String folderName;
    private String parentItemId;
    private Long parentId;
    private Long createdId;

    private String pathInHandBook;

    private String description;

    public CreateFolder365Option() {
    }

    public CreateFolder365Option(String folderName, String parentItemId, Long parentId, Long createdId, String pathInHandBook, String description) {
        this.folderName = folderName;
        this.parentItemId = parentItemId;
        this.parentId = parentId;
        this.createdId = createdId;
        this.pathInHandBook = pathInHandBook;
        this.description = description;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getParentItemId() {
        return parentItemId;
    }

    public void setParentItemId(String parentItemId) {
        this.parentItemId = parentItemId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getCreatedId() {
        return createdId;
    }

    public void setCreatedId(Long createdId) {
        this.createdId = createdId;
    }

    public String getPathInHandBook() {
        return pathInHandBook;
    }

    public void setPathInHandBook(String pathInHandBook) {
        this.pathInHandBook = pathInHandBook;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CreateFolder365Option{" +
            "folderName='" + folderName + '\'' +
            ", parentItemId='" + parentItemId + '\'' +
            ", parentId=" + parentId +
            ", createdId=" + createdId +
            ", pathInHandBook='" + pathInHandBook + '\'' +
            ", description='" + description + '\'' +
            '}';
    }
}
