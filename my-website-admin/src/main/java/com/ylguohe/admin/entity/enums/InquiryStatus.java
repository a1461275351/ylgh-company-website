package com.ylguohe.admin.entity.enums;

public enum InquiryStatus {
    PENDING("待处理"),
    PROCESSING("处理中"),
    REPLIED("已回复"),
    CLOSED("已关闭");

    private final String label;

    InquiryStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
