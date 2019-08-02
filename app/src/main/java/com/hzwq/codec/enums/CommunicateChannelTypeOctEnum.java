package com.hzwq.codec.enums;

/*a) 通信信道类型（bit4~bit7）*/
public enum CommunicateChannelTypeOctEnum {
    NO_CHANNEL_INFO("无信道信息", "0"),
    WIRELESS_PUBLIC_NETWORK("无线公网", "1"),
    PRIVATE_NETWORK("专网", "2"),
    TWO_THRI_ONE("230", "3"),
    SMS("短信", "4"),
    BEIDOU_5("北斗", "5"),
    RETAIN_6("保留", "6"),
    RETAIN_7("保留", "7"),
    RETAIN_8("保留", "8"),
    RETAIN_9("保留", "9"),
    RETAIN_10("保留", "10"),
    RETAIN_11("保留", "11"),
    RETAIN_12("保留", "12"),
    RETAIN_13("保留", "13"),
    RETAIN_14("保留", "14"),
    RETAIN_15("保留", "15");
    private String desc;
    private String value;

    CommunicateChannelTypeOctEnum(String desc, String value) {
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

    public static CommunicateChannelTypeOctEnum getByValue(String value, int radix) {
        if (value == null) return null;
        for (CommunicateChannelTypeOctEnum enumTmp : CommunicateChannelTypeOctEnum.values()) {
            if (enumTmp.getValue().equalsIgnoreCase(value) || Integer.parseInt(enumTmp.getValue(), 10) == Integer.parseInt(value, radix)) return enumTmp;
        }
        return null;
    }

    @Override
    public String toString() {
        return "CommunicateChannelTypeOctEnum{" +
                "desc='" + desc + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
