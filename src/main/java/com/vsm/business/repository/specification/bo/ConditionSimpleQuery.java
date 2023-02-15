package com.vsm.business.repository.specification.bo;

public class ConditionSimpleQuery {

    public enum QuerySimpleOperator {
        LIKE,
        EQUALS,
        BETWEEN,

        OBJECT_IN,
    }

    public String fieldName;
    public QuerySimpleOperator simpleOperator;
    public Object value;

    public Object valueStart;
    public Object valueEnd;

    public ConditionSimpleQuery() {
    }

    public ConditionSimpleQuery(String fieldName, QuerySimpleOperator simpleOperator, Object value) {
        this.fieldName = fieldName;
        this.simpleOperator = simpleOperator;
        this.value = value;
    }

    public ConditionSimpleQuery(String fieldName, QuerySimpleOperator simpleOperator, Object value, Object valueStart, Object valueEnd) {
        this.fieldName = fieldName;
        this.simpleOperator = simpleOperator;
        this.value = value;
        this.valueStart = valueStart;
        this.valueEnd = valueEnd;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public QuerySimpleOperator getSimpleOperator() {
        return simpleOperator;
    }

    public void setSimpleOperator(QuerySimpleOperator simpleOperator) {
        this.simpleOperator = simpleOperator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getValueStart() {
        return valueStart;
    }

    public void setValueStart(Object valueStart) {
        this.valueStart = valueStart;
    }

    public Object getValueEnd() {
        return valueEnd;
    }

    public void setValueEnd(Object valueEnd) {
        this.valueEnd = valueEnd;
    }

    @Override
    public String toString() {
        return "ConditionSimpleQuery{" +
            "fieldName='" + fieldName + '\'' +
            ", simpleOperator=" + simpleOperator +
            ", value=" + value +
            ", valueStart=" + valueStart +
            ", valueEnd=" + valueEnd +
            '}';
    }
}
