package com.vsm.business.service.custom.file.bo;

public class GetNumberPagesOfPdfResponse {

    private String id; //attachDocumentId
    private Long numberOfPages;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Long numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    @Override
    public String toString() {
        return "GetNumberPagesOfPdfResponse{" +
                "id='" + id + '\'' +
                ", numberOfPages=" + numberOfPages +
                '}';
    }
}
