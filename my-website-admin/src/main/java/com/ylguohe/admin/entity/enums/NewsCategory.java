package com.ylguohe.admin.entity.enums;

public enum NewsCategory {
    EXPORT("出口动态"),
    IMPORT("进口业务"),
    VEHICLE("二手车出口"),
    COMPANY("企业新闻"),
    EVENT("展会活动");

    private final String label;

    NewsCategory(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
