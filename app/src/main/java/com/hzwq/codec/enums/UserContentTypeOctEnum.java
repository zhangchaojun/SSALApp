package com.hzwq.codec.enums;

public enum UserContentTypeOctEnum {
    /*主站与终端之间需要进行加密传输的业务报文。报文格式符合终端的业务报文协议要求。*/
    APPLICATION_DATA("应用数据报文", "0"),

    /*终端的链路报文，包括：登录报文、心跳报文等，报文格式符合终端的业务报文协议要求。*/
    LINK_MANAGE("链路管理报文", "1"),

    /*网关获取终端基本信息的报文，包括请求报文和响应报文。*/
    OBTAIN_PSP_BASIC_INFO("获取终端基本信息报文", "2"),

    /*网关与终端之间进行身份认证和会话密钥协商的报文，包括请求报文和响应报文。
    具体协商报文定义与终端的类型相关，具体参见不同类型终端的协商机制描述。*/
    PSP_SESSION_NEGOTIATION("终端会话协商报文", "3"),

    /*更改终端会话密钥时效门限的报文*/
    CHANGE_FAILURE_THRESHOLD("更改时效门限", "4"),

    /*更新终端保存的用于协商的链路密钥的报文*/
    PSP_LINK_KEYGEN_UPDATE("终端链路密钥更新", "5"),

    /*更新终端保存的网关证书的报文*/
    PSP_GATE_CERT_UPDATE("终端网关证书更新", "6"),

    /*查询网关与所有客户机链接状态的报文*/
    QUERY_GATE_LINK_STATUS("查询网关链接状态", "7"),

    /*安全接入区设备需要进行密钥协商时的协商触发报文，由安全接入区设备发出，触发网关进行会话密钥协商*/
    SECURE_ACCESS_AREA_DEVICE_KEY_NEGOTIATION_TRIGGER("安全接入区设备密钥协商触发", "8"),

    /*客户机与网关间的心跳报文*/
    GATE_LINK_HEARTBEAT("网关链路心跳", "9"),

    /*终端向网关申请进行会话密钥协商，主要供互联网移动终端使用。*/
    PSP_SESSION_NEGOTIATION_PROMPT("终端会话申请", "10"),

    /*会话协商完成确认报文，主要供互联网移动终端使用。*/
    SESSION_NEGOTIATION_COMPLETE("终端会话确认", "11"),

    /*附录c里面的查询指令，按照SSAL协议封装作为数据域。
    该报文与其他报文不同，不会穿透网关，网关收到该报文后，发送回复信息，不会向后转发报文。*/
    GATE_QUERY("网关查询指令", "12"),

    RETAIN_13("保留", "13"),
    RETAIN_14("保留", "14"),
    RETAIN_15("保留", "15");

    private String name;
    /*[October]*/
    private String value;

    UserContentTypeOctEnum(String name, String value) {
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

    public static UserContentTypeOctEnum getByValue(String value, int radix) {
        if (value == null) return null;
        for (UserContentTypeOctEnum enumTmp : UserContentTypeOctEnum.values()) {
            if (enumTmp.getValue().equalsIgnoreCase(value) || Integer.parseInt(enumTmp.getValue(), 10) == Integer.parseInt(value, radix)) return enumTmp;
        }
        return null;
    }

    @Override
    public String toString() {
        return "UserContentTypeOctEnum{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
