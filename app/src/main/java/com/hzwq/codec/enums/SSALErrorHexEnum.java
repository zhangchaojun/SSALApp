package com.hzwq.codec.enums;

public enum SSALErrorHexEnum {
    SUCCESS("SUCCESS", "0000"),
    
    PSP_DECRYPTION_ERROR("终端解密错误", "1001"),
    PSP_CHECK_SIGN_FAILED("终端验签失败", "1002"),
    PSP_CHECK_MAC_FAILED("终端MAC校验失败", "1003"),
    SESSION_COUNTER_ERROR("会话计数器错误", "1004"),
    GATE_DECRYPTION_ERROR("网关解密错误", "1005"),
    GATE_CHECK_SIGN_FAILED("网关验签失败", "1006"),
	GATE_CHECK_MAC_FAILED("网关MAC校验失败", "1007"),
	GATE_KEY_UNIT_ISSUE("网关密码单元故障", "1008"),
	LINK_EQUIPMENT_KEY_UNIT_ERROR("链路设备密码单元故障", "1009"),
	
	PROTOCOL_VERSION_ERROR("协议版本错误", "2001"),
	ENCRYPTION_ALGORITHM_FLAG_MISMATCH("加解密算法标志不匹配", "2002"),
	DEVICE_TYPE_NOT_FOUND("设备类型无法识别", "2003"),
	CONTROL_CODE_NOT_FOUND("控制码无法识别", "2004"),
	STARTUP_DIRECTION_ERROR("传输方向位错误", "2005"),
	LUD_LENGTH_ABNORMAL_SHORTER_THAN_4_BYTES("数据域长度异常（小于4字节）", "2006"),
	LUD_LENGTH_MISMATCH("数据域长度不匹配", "2007"),
	
	
	TARGET_ADDRESS_NOT_EXIST("目标节点不存在", "3001"),
	COMMUNICATE_CHANNEL_NOT_CREATED("当前会话链路未建立", "3002"),
	MSG_SEND_ERROR("报文发送失败", "3003"),
	CHANNEL_ERROR("信道错误", "3004"),
	CHANNEL_KEY_NEGOTIATION_FAILED("当前链路会话协商失败", "3005");
	
	
    private String desc;
    private String value;

    SSALErrorHexEnum(String desc, String value) {
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

    public static SSALErrorHexEnum getByValue(String value, int radix) {
        if (value == null) return null;
        for (SSALErrorHexEnum enumTmp : SSALErrorHexEnum.values()) {
            if (enumTmp.getValue().equalsIgnoreCase(value) || Integer.parseInt(enumTmp.getValue(), 16) == Integer.parseInt(value, radix)) return enumTmp;
        }
        return null;
    }

    @Override
    public String toString() {
        return "SSALErrorHexEnum{" +
                "desc='" + desc + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
