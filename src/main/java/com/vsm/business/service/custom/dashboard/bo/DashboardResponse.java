package com.vsm.business.service.custom.dashboard.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DashboardResponse implements Serializable {
    private String name;
    private Long count = 0L;

    private Object listStatistic = new ArrayList<>();

    public DashboardResponse() {
    }

    public DashboardResponse(String name, Long count) {
        this.name = name;
        this.count = count;
    }

    public DashboardResponse(String name, Long count, Object listStatistic) {
        this.name = name;
        this.count = count;
        this.listStatistic = listStatistic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Object getListStatistic() {
        return listStatistic;
    }

    public void setListStatistic(Object listStatistic) {
        this.listStatistic = listStatistic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DashboardResponse that = (DashboardResponse) o;
        return Objects.equals(name, that.name) && Objects.equals(count, that.count) && Objects.equals(listStatistic, that.listStatistic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, count, listStatistic);
    }

    @Override
    public String toString() {
        return "DashboardResponse{" +
            "name='" + name + '\'' +
            ", count=" + count +
            ", listStatistic=" + listStatistic +
            '}';
    }
}
