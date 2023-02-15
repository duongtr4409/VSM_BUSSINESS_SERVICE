package com.vsm.business.utils;

import org.springframework.stereotype.Component;

@Component
public class ConditionUtils {

    /**
     *  hàm kiểm tra xem giá trị của biến là true hay false (TH null -> false)
     * @param condition: biến điều kiện (true, false, null)
     * @return
     */
    public boolean checkTrueFalse(Boolean condition){
        if(condition == null) return false;
        return condition;
    }

    /**
     * Hảm kiểm tra cờ isDelete của các đối tường (true -> true; flase, null -> false)
     * @param isDelete: cở isDelete của đối tượng cần kiểm tra (có 3 giá  trị true, false, null)
     * @return  : true là đã bị xóa, false chưa bị xóa
     */
    public boolean checkDelete(Boolean isDelete){
        if(isDelete == null) return false;
        return isDelete;
    }
}
