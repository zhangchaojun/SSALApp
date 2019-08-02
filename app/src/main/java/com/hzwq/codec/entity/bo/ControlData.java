package com.hzwq.codec.entity.bo;


import com.hzwq.codec.config.SSALConfigInitProperties;
import com.hzwq.codec.util.ByteTools;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/*6.4 控制数据CD*/
public class ControlData implements Cloneable {
    private static final Logger logger = LoggerFactory.getLogger(ControlData.class);
    private static Map<String, ControlData> instanceStore = new HashMap<>();

    private String controlCodeHex;
//  控制数据的hex值，用于组装SSAL报文时使用
    private String controlDataHex;

//    6.4.1 功能码FC[必有]
//    功能码FC，标识传输帧的类型及关键参数，由1个字节组成
    private FunctionCode functionCode;

//    6.4.2 SSAL协议版本SV[必有]
//    SSAL协议版本SV，标识SSAL协议的版本信息并包含协议中采用的密码算法信息，由2字节组成。
//    目前使用的加密算法为“01”CBC密文方式。终端的加密算法由网关在与终端进行会话密钥协商的过程中，在会话密钥协商请求报文中的该字段进行设定。
//    该字段采用反序传输，例如：	SSAL协议版本：0x00，加密算法：01，SSAL报文中实际填写顺序为 01 00。
    private SSALVersion ssalVersion;

//    6.4.3 设备地址类型DAT[必有]
//    设备地址类型DAT，标识终端的地址类型，由2个字节组成。
//    该字段采用反序传输，
//    例如：设备类型1，地址域类型0，协议版本01，SSAL报文中实际填写为 01 08。
    private DeviceAddressType deviceAddressType;

//    6.4.4 设备地址DA[必有]
//    设备地址DA，标识终端的逻辑地址或网络地址，由设备地址长度和设备地址内容两部分组成。
//    其中，设备地址长度为1字节；设备地址内容为变长，实际长度由设备地址长度决定。
//    设备地址内容在设备类型字段中标识设备类型的bit位取值为01、02、03、24时表示的是设备的逻辑地址；
//    其他取值时表示的是设备的网络IP地址。
//    当设备地址内容为设备网络IP地址时，需反序传输。例如：IP为10.0.10.31，填充后应为1F 0A 00 0A。
    private DeviceAddress deviceAddress;

//    6.4.5 源地址SA[必有]
//    源地址SA，标识传输帧的源IP地址，包括端口长度、源地址长度和源地址内容、端口内容四部分。
//    其中，端口长度为bit5、bit6、bit7表示长度，源地址长度为低5位（bit0、bit1、bit2、bit3、bit4），源地址内容为变长，
//    实际长度由源地址长度决定。端口内容根据端口长度来判断。
    private SourceAddress sourceAddress;

//    6.4.6 目的地址TA[必有, 下行的需要从上行数据中取，上行SA可作为标记tag到通道上供下行使用]
//    目的地址TA，标识传输帧的目的IP地址，包括端口长度、目的地址长度和目的地址内容、端口内容四部分
    private TargetAddress targetAddress;

//    6.4.7 通信信息CI[必有]
//    通信信息CI，标识终端的通信信息内容，包括通信信道类型和信道信息内容两部分。
//    其中，通信信息类型由1字节组成；信道信息为变长，由通信信道类型中的末位4bit定义，不超过15字节。
    private CommunicateInfo communicateInfo;

//    6.4.8 时间标签TP[必有]
//    时间标签TP，标识报文的产生时间，由报文生成者填写，由7个字节组成。
    private SSALTimestamp ssalTimestamp;

//    6.4.9 网关地址GA[可选]
//    网关地址GA，标识传输帧的安全网关地址，包括网关地址长度和网关地址内容两部分。
//    其中，网关地址长度为1字节，网关地址内容为变长，实际长度由网关地址长度决定。
    private GateAddress gateAddress;

    public void refreshTimestamp() {
        this.setSsalTimestamp(SSALTimestamp.giveMeOne());
        String controlDataHex = this.getFunctionCode().getFunctionCodeHex()
                + this.getSsalVersion().getSsalVersionHex()
                + this.getDeviceAddressType().getDeviceAddressTypeHexRevrs()
                + this.getDeviceAddress().getDeviceAddressHex()
                + this.getSourceAddress().getSourceAddressHex()
                + this.getTargetAddress().getTargetAddressHex()
                + this.getCommunicateInfo().getCommunicateInfoHex()
                + this.getSsalTimestamp().getTimestampHex()
                + this.getGateAddress().getGateAddressOriginalHex();
        this.setControlDataHex(controlDataHex);
    }

    private void initBySSALConfigProperties(String targetIp, int targetPort) {
        String controlCodeBinHigh8Bits = "11111101";

        this.setFunctionCode(FunctionCode.giveMeOne());
        this.setSsalVersion(SSALVersion.giveMeOne());
        this.setDeviceAddressType(DeviceAddressType.giveMeOne());
        this.setDeviceAddress(DeviceAddress.giveMeOne());
        this.setSourceAddress(SourceAddress.giveMeOne());
        this.setTargetAddress(TargetAddress.giveMeOne(targetIp, targetPort));
//        no communicate Info
        this.setCommunicateInfo(CommunicateInfo.giveMeOne());
        this.setSsalTimestamp(SSALTimestamp.giveMeOne());
        this.setGateAddress(GateAddress.giveMeOne());

        String controlCodeBinLower8Bits = "";
        if (StringUtils.isEmpty(SSALConfigInitProperties.gateIp)) {
            controlCodeBinLower8Bits = "00000000";
        }else {
            controlCodeBinLower8Bits = "10000000";
        }
        String controlCodeHexHighByte = ByteTools.byteArr2HexStr(ByteTools.binStr2Bytes(controlCodeBinHigh8Bits));
        String controlCodeHexLowerByte = ByteTools.byteArr2HexStr(ByteTools.binStr2Bytes(controlCodeBinLower8Bits));
        String controlCodeHex = StringUtils.leftPad(controlCodeHexLowerByte, 2, '0') + StringUtils.leftPad(controlCodeHexHighByte, 2, '0');
        this.setControlCodeHex(StringUtils.leftPad(controlCodeHex, 4, '0'));
    }

//    下行/上行
    public static ControlData giveMeOne(String targetIp, int targetPort) {
        String key = targetIp + ":" + targetPort;
        ControlData instance;
        if (!instanceStore.containsKey(key)) {
            instance = new ControlData();
            instance.initBySSALConfigProperties(targetIp, targetPort);
            instanceStore.put(key, instance);
        }else {
            instance = instanceStore.get(key);
        }

        return instance.copy();
    }

    public String getControlCodeHex() {
        return controlCodeHex;
    }

    public void setControlCodeHex(String controlCodeHex) {
        this.controlCodeHex = controlCodeHex;
    }

    public String getControlDataHex() {
        return controlDataHex;
    }

    public void setControlDataHex(String controlDataHex) {
        this.controlDataHex = controlDataHex;
    }

    public FunctionCode getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(FunctionCode functionCode) {
        this.functionCode = functionCode;
    }

    public SSALVersion getSsalVersion() {
        return ssalVersion;
    }

    public void setSsalVersion(SSALVersion ssalVersion) {
        this.ssalVersion = ssalVersion;
    }

    public DeviceAddressType getDeviceAddressType() {
        return deviceAddressType;
    }

    public void setDeviceAddressType(DeviceAddressType deviceAddressType) {
        this.deviceAddressType = deviceAddressType;
    }

    public DeviceAddress getDeviceAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(DeviceAddress deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public SourceAddress getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(SourceAddress sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public TargetAddress getTargetAddress() {
        return targetAddress;
    }

    public void setTargetAddress(TargetAddress targetAddress) {
        this.targetAddress = targetAddress;
    }

    public CommunicateInfo getCommunicateInfo() {
        return communicateInfo;
    }

    public void setCommunicateInfo(CommunicateInfo communicateInfo) {
        this.communicateInfo = communicateInfo;
    }

    public SSALTimestamp getSsalTimestamp() {
        return ssalTimestamp;
    }

    public void setSsalTimestamp(SSALTimestamp ssalTimestamp) {
        this.ssalTimestamp = ssalTimestamp;
    }

    public GateAddress getGateAddress() {
        return gateAddress;
    }

    public void setGateAddress(GateAddress gateAddress) {
        this.gateAddress = gateAddress;
    }

    public ControlData copy() {
        try {
            return (ControlData) this.clone();
        }catch (CloneNotSupportedException e) {
            logger.error(e.getMessage());
            return this;
        }
    }

    @Override
    public String toString() {
        return "ControlData{" +
                "controlCodeHex='" + controlCodeHex + '\'' +
                ", controlDataHex='" + controlDataHex + '\'' +
                ", functionCode=" + functionCode +
                ", ssalVersion=" + ssalVersion +
                ", deviceAddressType=" + deviceAddressType +
                ", deviceAddress=" + deviceAddress +
                ", sourceAddress=" + sourceAddress +
                ", targetAddress=" + targetAddress +
                ", communicateInfo=" + communicateInfo +
                ", ssalTimestamp=" + ssalTimestamp +
                ", gateAddress=" + gateAddress +
                '}';
    }
}
