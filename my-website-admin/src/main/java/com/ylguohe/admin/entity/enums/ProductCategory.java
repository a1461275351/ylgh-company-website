package com.ylguohe.admin.entity.enums;

public enum ProductCategory {
    EXPORT("出口产品"),
    IMPORT("进口产品");

    private final String label;

    ProductCategory(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
