package com.vsm.business.service.custom.search.service.bo;

import org.springframework.data.domain.Pageable;

public class IBaseSearchDTO {
    private Object objectSearch;
    private Pageable pageable;

    public IBaseSearchDTO() {
    }

    public IBaseSearchDTO(Object objectSearch, Pageable pageable) {
        this.objectSearch = objectSearch;
        this.pageable = pageable;
    }

    public Object getObjectSearch() {
        return objectSearch;
    }

    public void setObjectSearch(Object objectSearch) {
        this.objectSearch = objectSearch;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

    @Override
    public String toString() {
        return "IBaseSearchDTO{" +
            "objectSearch=" + objectSearch +
            ", pageable=" + pageable +
            '}';
    }
}
