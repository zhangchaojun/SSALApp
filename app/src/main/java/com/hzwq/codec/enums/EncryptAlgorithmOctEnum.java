package com.hzwq.codec.enums;

public enum EncryptAlgorithmOctEnum {
    PLAINTEXT("明文", "00"),
    CBC_CIPHERTEXT("CBC密文", "01"),
    PLAINTEXT_AND_MAC("明文+MAC", "02"),
    ECB_CIPHERTEXT("EBC密文", "03"),
    CBC_CIPHERTEXT_AND_MAC("CBC密文+MAC", "04"),
    ECB_CIPHERTEXT_AND_MAC("EBC密文+MAC", "05");
    private String name;
    private String value;

    EncryptAlgorithmOctEnum(String name, String value) {
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

    public static EncryptAlgorithmOctEnum getByValue(String value, int radix) {
        if (value == null) return null;
        for (EncryptAlgorithmOctEnum enumTmp : EncryptAlgorithmOctEnum.values()) {
            if (enumTmp.getValue().equalsIgnoreCase(value) || Integer.parseInt(enumTmp.getValue(), 10) == Integer.parseInt(value, radix)) return enumTmp;
        }
        return null;
    }

    @Override
    public String toString() {
        return "EncryptAlgorithmOctEnum{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
