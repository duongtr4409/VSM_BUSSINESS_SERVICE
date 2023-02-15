package com.vsm.business.service.custom.processRequest;

import com.vsm.business.domain.ProcessData;
import com.vsm.business.domain.RequestData;
import com.vsm.business.domain.StepData;
import com.vsm.business.repository.RequestDataRepository;
import com.vsm.business.service.custom.processRequest.bo.ApproveOption;
import com.vsm.business.service.custom.processRequest.bo.ConcurrentApproveOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ConcurrentProcessRequestCustomService {

    private final Logger log = LoggerFactory.getLogger(ConcurrentProcessRequestCustomService.class);

    @Autowired
    public RequestDataRepository requestDataRepository;

    @Autowired
    public ProcessRequestCustomService processRequestCustomService;

    /**
     * Hàm thực hiện lấy ra trạng thái xử lý của phiếu -> tạm thời chỉ Send(Gửi), Agree(Đồng ý), Approve(Phê duyệt)
     * @param requestDataId : id của phiếu
     * @return              : trả về Action tương ứng
     */
    private ApproveOption.Action getActionFormRequest(Long requestDataId){
        RequestData requestData = this.requestDataRepository.findById(requestDataId).get();
        ProcessData currentProcessData = requestData.getProcessData().stream().sorted((ele1, ele2) -> (int)(ele2.getRoundNumber() - ele1.getRoundNumber())).collect(Collectors.toList()).get(0);
        List<StepData> stepDataList = requestData.getStepData().stream().filter(ele -> currentProcessData.getId().equals(ele.getProcessData().getId())).sorted((ele1, ele2) -> (int)(ele2.getStepOrder() - ele1.getStepOrder())).collect(Collectors.toList());
        Long maxStepOrder = stepDataList.get(0).getStepOrder();
        StepData currentStepData = stepDataList.stream().filter(ele -> ele.getIsActive() == true).findFirst().orElse(null);
        if(currentStepData == null) return ApproveOption.Action.Send;                                           // TH nếu ko có bước nào -> Active -> Gửi
        if(currentStepData.getStepOrder().equals(maxStepOrder)) return ApproveOption.Action.Approve;            // TH bước cuối -> Approve -> Phê Duyệt
        return ApproveOption.Action.Agree;                                                                      // TH còn lại sẽ là Agree -> Đồng ý
    }

    public Map<Long, Boolean> concurrentActionRequest(ConcurrentApproveOption concurrentApproveOption){
        Map<Long, Boolean> result = new HashMap<>();
        ApproveOption approveOption = new ApproveOption();
        BeanUtils.copyProperties(concurrentApproveOption, approveOption);
        for(Long requestDataId : concurrentApproveOption.getListRequestDataId()){
            try {
                ApproveOption.Action action = getActionFormRequest(requestDataId);
                approveOption.setRequestDataId(requestDataId);
                approveOption.setAction(action);
                Boolean resultOne = this.processRequestCustomService.actionRequestData(approveOption);
                result.put(requestDataId, resultOne);
            }catch (Exception e){
                log.error("{}", e);
                result.put(requestDataId, false);
            }
        }
        return result;
    }
}
