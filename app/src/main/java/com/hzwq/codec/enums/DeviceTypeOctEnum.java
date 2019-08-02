package com.hzwq.codec.enums;

/*设备类型（bit11~bit15）*/
public enum  DeviceTypeOctEnum {

    MAIN_STATION("采集主站", "00"),
    ACQUISITION_SYSTEM_EQUIPMENT("采集设备", "01"),
    PSP("现场服务终端（掌机）", "02"),
    MOBILE_OFFICE_EQUIPMENT("移动办公设备(营业办公设备)", "03"),
    COMMUNICATION_FRONT_END("通信前置", "04"),
    AAA_AUTHENTICATION_SERVER("3A认证服务器", "05"),
    ONLINE_PERFORMANCE_DETECTION_SERVER("在线性能检测服务器", "06"),
    SPECIAL_EQUIPMENT_FOR_OPERATION_AND_MAINTENANCE_IN_ACCESS_AREA("接入区运维专用设备", "07"),

    TRANSPORTATION_INSPECTION_PROFESSIONAL_EQUIPMENT_08("运检专业设备（配自、输变电状态监测等）", "08"),
    TRANSPORTATION_INSPECTION_PROFESSIONAL_EQUIPMENT_09("运检专业设备（配自、输变电状态监测等）", "09"),
    TRANSPORTATION_INSPECTION_PROFESSIONAL_EQUIPMENT_10("运检专业设备（配自、输变电状态监测等）", "10"),
    TRANSPORTATION_INSPECTION_PROFESSIONAL_EQUIPMENT_11("运检专业设备（配自、输变电状态监测等）", "11"),
    TRANSPORTATION_INSPECTION_PROFESSIONAL_EQUIPMENT_12("运检专业设备（配自、输变电状态监测等）", "12"),
    TRANSPORTATION_INSPECTION_PROFESSIONAL_EQUIPMENT_13("运检专业设备（配自、输变电状态监测等）", "13"),
    TRANSPORTATION_INSPECTION_PROFESSIONAL_EQUIPMENT_14("运检专业设备（配自、输变电状态监测等）", "14"),
    TRANSPORTATION_INSPECTION_PROFESSIONAL_EQUIPMENT_15("运检专业设备（配自、输变电状态监测等）", "15"),

    COMMUNICATION_SYSTEM_PROFESSIONAL_EQUIPMENT_16("信通专业设备（移动终端等）", "16"),
    COMMUNICATION_SYSTEM_PROFESSIONAL_EQUIPMENT_17("信通专业设备（移动终端等）", "17"),
    COMMUNICATION_SYSTEM_PROFESSIONAL_EQUIPMENT_18("信通专业设备（移动终端等）", "18"),
    COMMUNICATION_SYSTEM_PROFESSIONAL_EQUIPMENT_19("信通专业设备（移动终端等）", "19"),
    COMMUNICATION_SYSTEM_PROFESSIONAL_EQUIPMENT_20("信通专业设备（移动终端等）", "20"),
    COMMUNICATION_SYSTEM_PROFESSIONAL_EQUIPMENT_21("信通专业设备（移动终端等）", "21"),
    COMMUNICATION_SYSTEM_PROFESSIONAL_EQUIPMENT_22("信通专业设备（移动终端等）", "22"),
    COMMUNICATION_SYSTEM_PROFESSIONAL_EQUIPMENT_23("信通专业设备（移动终端等）", "23"),

    APP_PSP_OF_INTERNET_CHINA_NETWORK("网上国网APP终端", "24"),

    RETAIN_25("保留，各专业自行扩展", "25"),
    RETAIN_26("保留，各专业自行扩展", "26"),
    RETAIN_27("保留，各专业自行扩展", "27"),
    RETAIN_28("保留，各专业自行扩展", "28"),
    RETAIN_29("保留，各专业自行扩展", "29"),
    RETAIN_30("保留，各专业自行扩展", "30"),
    RETAIN_3("保留，各专业自行扩展", "31");

    private String name;
    private String value;

    DeviceTypeOctEnum(String name, String value) {
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

    public static DeviceTypeOctEnum getByValue(String value, int radix) {
        if (value == null) return null;
        for (DeviceTypeOctEnum enumTmp : DeviceTypeOctEnum.values()) {
            if (enumTmp.getValue().equalsIgnoreCase(value) || Integer.parseInt(enumTmp.getValue(), 10) == Integer.parseInt(value, radix)) return enumTmp;
        }
        return null;
    }

    @Override
    public String toString() {
        return "DeviceTypeOctEnum{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
