package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vsm.business.domain.AttachmentFile} entity.
 */
public class AttachmentFileDTO implements Serializable {

    private Long id;

    private String fileName;

    private String fileExtension;

    private String contentType;

    private String pathInHandBook;

    private String idInFileService;

    private String path;

    private String fullPath;

    private String ofice365Path;

    private String itemId365;

    private Long fileSize;

    private String urlView;

    private String description;

    private Boolean isFolder;

    private Boolean isMainDoc;

    private String fileTypeName;

    private String fileTypeCode;

    private Long parentId;

    private String urlUpload;

    private String nextExpectedRanges;

    private String signOfFile;

    private String createdName;

    private String createdOrgName;

    private String createdRankName;

    private Instant createdDate;

    private String modifiedName;

    private Instant modifiedDate;

    private Boolean isActive;

    private Boolean isDelete;

    private String tennantCode;

    private String tennantName;

    private FileTypeDTO fileType;

    private RequestDataDTO requestData;

    private TennantDTO tennant;

    private UserInfoDTO created;

    private UserInfoDTO modified;

    private TemplateFormDTO templateForm;

    private ReqdataProcessHisDTO reqdataProcessHis;

    private OfficialDispatchDTO officialDispatch;

    private StepProcessDocDTO stepProcessDoc;

    private MailTemplateDTO mailTemplate;

    private ManageStampInfoDTO manageStampInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getPathInHandBook() {
        return pathInHandBook;
    }

    public void setPathInHandBook(String pathInHandBook) {
        this.pathInHandBook = pathInHandBook;
    }

    public String getIdInFileService() {
        return idInFileService;
    }

    public void setIdInFileService(String idInFileService) {
        this.idInFileService = idInFileService;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getOfice365Path() {
        return ofice365Path;
    }

    public void setOfice365Path(String ofice365Path) {
        this.ofice365Path = ofice365Path;
    }

    public String getItemId365() {
        return itemId365;
    }

    public void setItemId365(String itemId365) {
        this.itemId365 = itemId365;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getUrlView() {
        return urlView;
    }

    public void setUrlView(String urlView) {
        this.urlView = urlView;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsFolder() {
        return isFolder;
    }

    public void setIsFolder(Boolean isFolder) {
        this.isFolder = isFolder;
    }

    public Boolean getIsMainDoc() {
        return isMainDoc;
    }

    public void setIsMainDoc(Boolean isMainDoc) {
        this.isMainDoc = isMainDoc;
    }

    public String getFileTypeName() {
        return fileTypeName;
    }

    public void setFileTypeName(String fileTypeName) {
        this.fileTypeName = fileTypeName;
    }

    public String getFileTypeCode() {
        return fileTypeCode;
    }

    public void setFileTypeCode(String fileTypeCode) {
        this.fileTypeCode = fileTypeCode;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getUrlUpload() {
        return urlUpload;
    }

    public void setUrlUpload(String urlUpload) {
        this.urlUpload = urlUpload;
    }

    public String getNextExpectedRanges() {
        return nextExpectedRanges;
    }

    public void setNextExpectedRanges(String nextExpectedRanges) {
        this.nextExpectedRanges = nextExpectedRanges;
    }

    public String getSignOfFile() {
        return signOfFile;
    }

    public void setSignOfFile(String signOfFile) {
        this.signOfFile = signOfFile;
    }

    public String getCreatedName() {
        return createdName;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return createdOrgName;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return createdRankName;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return modifiedName;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getTennantCode() {
        return tennantCode;
    }

    public void setTennantCode(String tennantCode) {
        this.tennantCode = tennantCode;
    }

    public String getTennantName() {
        return tennantName;
    }

    public void setTennantName(String tennantName) {
        this.tennantName = tennantName;
    }

    public FileTypeDTO getFileType() {
        return fileType;
    }

    public void setFileType(FileTypeDTO fileType) {
        this.fileType = fileType;
    }

    public RequestDataDTO getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestDataDTO requestData) {
        this.requestData = requestData;
    }

    public TennantDTO getTennant() {
        return tennant;
    }

    public void setTennant(TennantDTO tennant) {
        this.tennant = tennant;
    }

    public UserInfoDTO getCreated() {
        return created;
    }

    public void setCreated(UserInfoDTO created) {
        this.created = created;
    }

    public UserInfoDTO getModified() {
        return modified;
    }

    public void setModified(UserInfoDTO modified) {
        this.modified = modified;
    }

    public TemplateFormDTO getTemplateForm() {
        return templateForm;
    }

    public void setTemplateForm(TemplateFormDTO templateForm) {
        this.templateForm = templateForm;
    }

    public ReqdataProcessHisDTO getReqdataProcessHis() {
        return reqdataProcessHis;
    }

    public void setReqdataProcessHis(ReqdataProcessHisDTO reqdataProcessHis) {
        this.reqdataProcessHis = reqdataProcessHis;
    }

    public OfficialDispatchDTO getOfficialDispatch() {
        return officialDispatch;
    }

    public void setOfficialDispatch(OfficialDispatchDTO officialDispatch) {
        this.officialDispatch = officialDispatch;
    }

    public StepProcessDocDTO getStepProcessDoc() {
        return stepProcessDoc;
    }

    public void setStepProcessDoc(StepProcessDocDTO stepProcessDoc) {
        this.stepProcessDoc = stepProcessDoc;
    }

    public MailTemplateDTO getMailTemplate() {
        return mailTemplate;
    }

    public void setMailTemplate(MailTemplateDTO mailTemplate) {
        this.mailTemplate = mailTemplate;
    }

    public ManageStampInfoDTO getManageStampInfo() {
        return manageStampInfo;
    }

    public void setManageStampInfo(ManageStampInfoDTO manageStampInfo) {
        this.manageStampInfo = manageStampInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttachmentFileDTO)) {
            return false;
        }

        AttachmentFileDTO attachmentFileDTO = (AttachmentFileDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, attachmentFileDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttachmentFileDTO{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", fileExtension='" + getFileExtension() + "'" +
            ", contentType='" + getContentType() + "'" +
            ", pathInHandBook='" + getPathInHandBook() + "'" +
            ", idInFileService='" + getIdInFileService() + "'" +
            ", path='" + getPath() + "'" +
            ", fullPath='" + getFullPath() + "'" +
            ", ofice365Path='" + getOfice365Path() + "'" +
            ", itemId365='" + getItemId365() + "'" +
            ", fileSize=" + getFileSize() +
            ", urlView='" + getUrlView() + "'" +
            ", description='" + getDescription() + "'" +
            ", isFolder='" + getIsFolder() + "'" +
            ", isMainDoc='" + getIsMainDoc() + "'" +
            ", fileTypeName='" + getFileTypeName() + "'" +
            ", fileTypeCode='" + getFileTypeCode() + "'" +
            ", parentId=" + getParentId() +
            ", urlUpload='" + getUrlUpload() + "'" +
            ", nextExpectedRanges='" + getNextExpectedRanges() + "'" +
            ", signOfFile='" + getSignOfFile() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            ", tennantCode='" + getTennantCode() + "'" +
            ", tennantName='" + getTennantName() + "'" +
            ", fileType=" + getFileType() +
            ", requestData=" + getRequestData() +
            ", tennant=" + getTennant() +
            ", created=" + getCreated() +
            ", modified=" + getModified() +
            ", templateForm=" + getTemplateForm() +
            ", reqdataProcessHis=" + getReqdataProcessHis() +
            ", officialDispatch=" + getOfficialDispatch() +
            ", stepProcessDoc=" + getStepProcessDoc() +
            ", mailTemplate=" + getMailTemplate() +
            ", manageStampInfo=" + getManageStampInfo() +
            "}";
    }
}
