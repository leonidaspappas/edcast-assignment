package com.edcast.fraud.reporting.enums;

public enum CountryCodes {
    NL("nl","TheNetherlands"),
    HR("hr","Croatia"),
    BG("bg","Bulgaria"),
    IN("in","India"),
    JP("jp","Japan");

    public final String label;
    public final String country;

    private CountryCodes(String label,String country) {
        this.label = label;
        this.country = country;
    }

}
