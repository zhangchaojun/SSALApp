package com.hzwq.codec.enums;

/*b) 地址类型（bit8~bit10）*/
public enum AddressTypeOctEnum {
    STANDALONE("单地址", "0"),
    GROUP("组地址", "1"),
    BROADCAST("广播地址", "2");

    private String desc;
    private String value;

    AddressTypeOctEnum(String desc, String value) {
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

    public static AddressTypeOctEnum getByValue(String value, int radix) {
        if (value == null) return null;
        for (AddressTypeOctEnum enumTmp : AddressTypeOctEnum.values()) {
            if (enumTmp.getValue().equalsIgnoreCase(value) || Integer.parseInt(enumTmp.getValue(), 10) == Integer.parseInt(value, radix)) return enumTmp;
        }
        return null;
    }

    @Override
    public String toString() {
        return "AddressTypeOctEnum{" +
                "desc='" + desc + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
