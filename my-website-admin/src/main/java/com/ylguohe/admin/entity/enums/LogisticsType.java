package com.ylguohe.admin.entity.enums;

public enum LogisticsType {
    RAIL("中欧班列"),
    TIR("TIR公路运输"),
    AIR("航空冷链"),
    SEA("海运物流");

    private final String label;

    LogisticsType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
