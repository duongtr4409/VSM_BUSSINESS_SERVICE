package com.vsm.business.service.custom.search.service.bo;

import java.io.Serializable;
import java.util.List;

public class ISearchResponseDTO implements Serializable {
    private Long total;
    private Object listData;

    public ISearchResponseDTO() {
    }

    public ISearchResponseDTO(Long total, Object listData) {
        this.total = total;
        this.listData = listData;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Object getListData() {
        return listData;
    }

    public void setListData(Object listData) {
        this.listData = listData;
    }

    @Override
    public String toString() {
        return "ISearchResponseDTO{" +
            "total=" + total +
            ", listData=" + listData +
            '}';
    }
}
