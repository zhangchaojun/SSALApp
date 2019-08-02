package com.hzwq.codec.enums;

public enum FCCodeBinEnum {
    ONE("ONE", "1"), ZERO("ZERO", "0");
    private String name;
    /*[binary]*/
    private String value;

    FCCodeBinEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static FCCodeBinEnum getByValue(String value, int radix) {
        if (value == null) return null;
        for (FCCodeBinEnum enumTmp : FCCodeBinEnum.values()) {
            if (enumTmp.getValue().equalsIgnoreCase(value) || Integer.parseInt(enumTmp.getValue(), 2) == Integer.parseInt(value, radix)) return enumTmp;
        }
        return null;
    }

    @Override
    public String toString() {
        return "FCCodeBinEnum{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
