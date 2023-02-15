package com.vsm.business.utils;

import com.vsm.business.domain.AttachmentFile;
import com.vsm.business.domain.AttachmentPermisition;
import com.vsm.business.repository.AttachmentFileRepository;
import com.vsm.business.repository.AttachmentPermisitionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Set;

@Component
public class PermissionFileUtils {

    private final Logger log = LoggerFactory.getLogger(PermissionFileUtils.class);

    public static final String VIEW = "view";
    public static final String EDIT = "edit";
    public static final String DELETE = "delete";

    @Autowired
    public UserUtils userUtils;
    @Autowired
    public AttachmentFileRepository attachmentFileRepository;
    @Autowired
    public AttachmentPermisitionRepository attachmentPermisitionRepository;

    /**
     * Hàm thực hiện kiểm tra user có được cấu hình quyền với file hay không)
     * @param attachmentFileId  : id của file
     * @param action            : thao tác
     * @return                  : true: có quyền | false: ko có quyền
     */
    public boolean checkPermisstionToFile(Long attachmentFileId, String action){
        try {
            List<AttachmentPermisition> attachmentPermisitionList = new ArrayList<>();
            List<AttachmentFile> attachmentFileList = this.attachmentFileRepository.getAllParent(attachmentFileId);
            if(attachmentFileList == null || attachmentFileList.isEmpty()) return false;        // nếu không có file -> trả về file luôn;
            List<Long> userInfoList = new ArrayList<>();
            attachmentFileList.forEach(ele -> {
                userInfoList.addAll(this.attachmentPermisitionRepository.findAllByAttachmentFileId(ele.getId()).stream().filter(ele1 -> {
                    if(action.equals(VIEW)) return ele1.getIsView();
                    else if(action.equals(EDIT)) return ele1.getIsEdit();
                    else if(action.equals(DELETE)) return ele1.getIsDelete();
                    else return false;
                }).map(ele1 -> {
                    return ele1.getUserInfo() != null ? ele1.getUserInfo().getId() : null;
                }).collect(Collectors.toList()));
            });
            Long currentUserId = this.userUtils.getCurrentUser().getId();
            return userInfoList.stream().anyMatch(ele -> currentUserId.equals(ele));
        }catch (Exception e){
            log.error("{}", e);     // nếu có lỗi trong quá trình -> vẫn cho xem
            return true;
        }
    }

    /**
     * Hàm thực hiện kiểm tra user có quyền xem folder(file) không (TH có quyền với thư mục con thì phải cho click vào thư mực chả trên frontend nên cũng cần cho call API)
     * @param attachmentFileId  : id của folder (file) cần kiểm tra
     * @return  : true: có quyền | false: không có quyền
     */
    public boolean chechPermisitionViewFile(Long attachmentFileId){
        try {
            Set<Long> attachmentHasPermisition = new HashSet<>();
            List<AttachmentPermisition> attachmentPermisitionList = this.attachmentPermisitionRepository.findAllByUserInfoId(this.userUtils.getCurrentUser().getId());
            attachmentPermisitionList.forEach(ele -> {
                try {
                    attachmentHasPermisition.addAll(this.attachmentFileRepository.getAllParent(ele.getAttachmentFile().getId()).stream().map(ele1 -> ele1.getId()).collect(Collectors.toSet()));
                }catch (Exception e1) {log.error("{}", e1);}
            });
            return attachmentHasPermisition.stream().anyMatch(ele -> ele.equals(attachmentFileId));
        }catch (Exception e){       // nếu có lỗi trong quá trình -> vẫn cho xem
            log.error("{}", e);
            return true;
        }
    }

    /**
     * Hàm thực hiện lấy dánh sách id của những attachmentFile mà user có quyền (xem, sửa, xóa)
     * @param action    : hành động tương ứng
     * @return  : danh sách id của những attachmentFile mà user có quyền
     */
    public List<AttachmentFile> getAllFileHasPermisition(String action){
        try {
            Long currentUserId = this.userUtils.getCurrentUser().getId();
            List<AttachmentPermisition> attachmentPermisitionList = this.attachmentPermisitionRepository.findAllByUserInfoId(currentUserId);
            if(attachmentPermisitionList != null && !attachmentPermisitionList.isEmpty()){
                return attachmentPermisitionList.stream().filter(ele -> {
                    if(action.equals(VIEW)) return ele.getIsView();
                    else if(action.equals(EDIT)) return ele.getIsEdit() || ele.getIsView();
                    else if(action.equals(DELETE)) return ele.getIsDelete() || ele.getIsView();
                    else return false;
                }).map(ele -> ele.getAttachmentFile()).collect(Collectors.toList());
            }else{
                return new ArrayList<>();
            }
        }catch (Exception e){
            log.error("{}", e);
            return new ArrayList<>();
        }
    }
}
