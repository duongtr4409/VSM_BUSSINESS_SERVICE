package com.vsm.business.service.custom.statistic.bo;

import java.io.Serializable;
import java.util.List;

public class StatisticOption implements Serializable{

    public enum Operator {
        EQUAL("EQUAL", "="),                                // bằng
        LESSTHAN("LESSTHAN", "<"),                          // nhỏ hơn
        GREATERTHAN("GREATERTHAN", ">"),                    // lơn hơn
        LESSTHANOREQUAL("LESSTHANOREQUAL", "<="),           // nhỏ hơn hoặc bằng
        GREATERTHENOREQUAL("GREATERTHENOREQUAL", "<="),       // lớn hơn hoặc băng
        NOTEQUAL("NOTEQUAL", "!="),                         // khác
        CONTAIN("CONTAIN", "like");                         // chứa

        public String name;
        private String operator;

        Operator(String name, String operator) {
            this.name = name;
            this.operator = operator;
        }

        Operator() {
        }

        Operator(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }
    }

    private Long requestId;
    private List<Long> organizationIds;
    private List<CompareDto> compareDtos;

    public StatisticOption() {
    }

    public StatisticOption(Long requestId, List<Long> organizationIds, List<CompareDto> compareDtos) {
        this.requestId = requestId;
        this.organizationIds = organizationIds;
        this.compareDtos = compareDtos;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public List<Long> getOrganizationIds() {
        return organizationIds;
    }

    public void setOrganizationIds(List<Long> organizationIds) {
        this.organizationIds = organizationIds;
    }

    public List<CompareDto> getCompareDtos() {
        return compareDtos;
    }

    public void setCompareDtos(List<CompareDto> compareDtos) {
        this.compareDtos = compareDtos;
    }

    public static class CompareDto implements Serializable {
        public Operator operator;           // toán tử so sánh
        public String operatorString;
        public String fieldCode;            // của trường cần so sánh
        public String value;                // giá trị so sánh

        public CompareDto() {
        }

        public CompareDto(Operator operator, String fieldCode, String value) {
            this.operator = operator;
            this.fieldCode = fieldCode;
            this.value = value;
        }

        public CompareDto(String operatorString, String fieldCode, String value) {
            this.operatorString = operatorString;
            this.fieldCode = fieldCode;
            this.value = value;
//            switch (operatorString){
//                case Operator.CONTAIN.name:
//                    this.operator = Operator.CONTAIN;
//                    break;
//
//                default:
//                    return;
//            }
        }
    }
}
