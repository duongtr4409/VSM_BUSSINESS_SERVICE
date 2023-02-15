package com.vsm.business.repository.specification.bo;

import java.util.List;

public class ConditionQuery {

    public enum QueryOperator {
        LIKE,
        EQUALS,
        NOT_EQUALS,
        GREATER_THAN,
        LESS_THAN,
        IN,
        GREATER_THAN_OR_EQUALS,
        LESS_THAN_OR_EQUALS,
    }

    public String fieldName;
    public QueryOperator operator;
    public Object value;
    public List<Object> listValue;

    public ConditionQuery() {
    }

    public ConditionQuery(String fieldName, QueryOperator operator, Object value, List<Object> listValue) {
        this.fieldName = fieldName;
        this.operator = operator;
        this.value = value;
        this.listValue = listValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public QueryOperator getOperator() {
        return operator;
    }

    public void setOperator(QueryOperator operator) {
        this.operator = operator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public List<Object> getListValue() {
        return listValue;
    }

    public void setListValue(List<Object> listValue) {
        this.listValue = listValue;
    }

    @Override
    public String toString() {
        return "ConditionQuery{" +
            "fieldName='" + fieldName + '\'' +
            ", operator=" + operator +
            ", value=" + value +
            ", listValue=" + listValue +
            '}';
    }
}
