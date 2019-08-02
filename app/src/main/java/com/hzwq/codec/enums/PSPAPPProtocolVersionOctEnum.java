package com.hzwq.codec.enums;

/*d) 应用协议版本（bit0~bit4）*/
/*新接入系统的设备，该字段填写0，初始版本。
该字段采用反序传输，例如：设备类型1，地址域类型0，协议版本01，SSAL报文中实际填写为 01 08。*/
public enum PSPAPPProtocolVersionOctEnum {
    INIT("初始版本", "0"),
    STATUTE_16("16规约（698面向对象）", "1"),
    STATUTE_13("13规约（376.1）", "2"),
    MOBILE_INTERNET_APP("移动互联网APP", "3"),

    RATAIN_4("保留", "4"),
    RATAIN_5("保留", "5"),
    RATAIN_6("保留", "6"),
    RATAIN_7("保留", "7"),
    RATAIN_8("保留", "8"),
    RATAIN_9("保留", "9"),
    RATAIN_10("保留", "10"),
    RATAIN_11("保留", "11"),
    RATAIN_12("保留", "12"),
    RATAIN_13("保留", "13"),
    RATAIN_14("保留", "14"),
    RATAIN_15("保留", "15"),
    RATAIN_16("保留", "16"),
    RATAIN_17("保留", "17"),
    RATAIN_18("保留", "18"),
    RATAIN_19("保留", "19"),
    RATAIN_20("保留", "20"),
    RATAIN_21("保留", "21"),
    RATAIN_22("保留", "22"),
    RATAIN_23("保留", "23"),
    RATAIN_24("保留", "24"),
    RATAIN_25("保留", "25"),
    RATAIN_26("保留", "26"),
    RATAIN_27("保留", "27"),
    RATAIN_28("保留", "28"),
    RATAIN_29("保留", "29"),
    RATAIN_30("保留", "30"),
    RATAIN_31("保留", "31");

    private String desc;
    private String value;

    PSPAPPProtocolVersionOctEnum(String desc, String value) {
        this.desc = desc;
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static PSPAPPProtocolVersionOctEnum getByValue(String value, int radix) {
        if (value == null) return null;
        for (PSPAPPProtocolVersionOctEnum enumTmp : PSPAPPProtocolVersionOctEnum.values()) {
            if (enumTmp.getValue().equalsIgnoreCase(value) || Integer.parseInt(enumTmp.getValue(), 10) == Integer.parseInt(value, radix)) return enumTmp;
        }
        return null;
    }

    @Override
    public String toString() {
        return "PSPAPPProtocolVersionOctEnum{" +
                "desc='" + desc + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
