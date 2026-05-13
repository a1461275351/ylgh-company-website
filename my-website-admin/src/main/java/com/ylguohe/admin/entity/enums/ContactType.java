package com.ylguohe.admin.entity.enums;

public enum ContactType {
    MAIN("总部"),
    OVERSEAS("海外");

    private final String label;

    ContactType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
