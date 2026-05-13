package com.ylguohe.admin.entity.enums;

public enum InquiryType {
    EXPORT_PURCHASE("出口产品采购询盘"),
    IMPORT_SUPPLY("进口商品供应合作"),
    VEHICLE_EXPORT("二手车出口业务"),
    LOGISTICS("国际物流合作"),
    WAREHOUSE("海外仓储合作"),
    OTHER("其他业务合作");

    private final String label;

    InquiryType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
