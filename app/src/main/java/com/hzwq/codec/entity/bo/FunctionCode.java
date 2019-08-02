package com.hzwq.codec.entity.bo;

import com.hzwq.codec.config.SSALConfigInitProperties;
import com.hzwq.codec.enums.DeviceTypeOctEnum;
import com.hzwq.codec.enums.FCCodeBinEnum;
import com.hzwq.codec.enums.UserContentTypeOctEnum;
import com.hzwq.codec.util.ByteTools;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*6.4.1 功能码FC*/
/*功能码FC，标识传输帧的类型及关键参数，由1个字节组成*/
public class FunctionCode implements Cloneable {
    private static final Logger logger = LoggerFactory.getLogger(FunctionCode.class);
    private static FunctionCode instance;

    private String functionCodeHex;
    private String functionCodeBin;

//    传输方向位DIR, [binary], bit7
//    a) 传输方向位（bit7）标识传输帧的传送方向。
//    0表示此传输帧是由主站发出的下行数据帧；
//    1表示此传输帧是由终端发出的上行数据帧。
    private FCCodeBinEnum transportDirection;

//    启动标志位PRM, [binary], bit6
//    标识传输帧的发起方。
//    0表示此传输帧来自从动站；
//    1表示此传输帧来自启动站。
//    对于没有启动站和从动站标志区分的老终端，由前置机在对其进行SSAL协议封装时填写，均填为启动站标志。
//    改造过渡期，上行明文密文由网关通过界面来配置，原业务（698、376.1）需要通信前置进行SSAL封装，
//    下行需要采集前置进行SSAL封装；
//    确定过渡期间，通信前置对费SSAL的协议统一封装成SSAL；
//    采集前置同样将所有协议进行SSAL；网关给采集前置的报文，根据网关配置项确定是给SSAL或应用报文。
    private FCCodeBinEnum startUpSymbol;

    /*内网机收发数据格式, [binary], bit5*/
    private String retainBin = "0";

//    主站触发协商标志, [binary], bit4
//    标识主站下行传输帧是否触发网关与传输帧的目标终端进行会话密钥协商。
//    0表示主站下行传输帧永远不会触发网关启动与终端的会话密钥协商；
//    1表示主站下行的传输帧可能触发网关与目标终端的会话密钥协商，
//    网关需要根据终端情况进行具体判断是否启动会话密钥协商。
//    针对230终端，24h密钥过期后，网关不主动触发会话密钥协商, 建议网关是否自动可配，主站选择触发会话协商。
    private FCCodeBinEnum mainStationTriggerNegotiation;

    /*报文类型, , [October], bit3-bit0*/
    /*标识链路用户数据域的关键报文类型*/
    private UserContentTypeOctEnum userContentTypeOct;

    public FunctionCode() {
    }

    //    用系统参数创建一个对象并返回
    private void initBySSALConfigProperties() {
        this.setTransportDirection(SSALConfigInitProperties.deviceType.equals(DeviceTypeOctEnum.PSP) ? FCCodeBinEnum.ONE : FCCodeBinEnum.ZERO);
        this.setStartUpSymbol(SSALConfigInitProperties.deviceType.equals(DeviceTypeOctEnum.PSP) ? FCCodeBinEnum.ONE : FCCodeBinEnum.ZERO);
        this.setMainStationTriggerNegotiation(FCCodeBinEnum.ZERO);
        this.setUserContentTypeOct(UserContentTypeOctEnum.APPLICATION_DATA);
        String binStr8Bits = this.getTransportDirection().getValue()
                + this.getStartUpSymbol().getValue()
                + this.getRetainBin()
                + this.getMainStationTriggerNegotiation().getValue()
                + StringUtils.leftPad(Integer.toBinaryString(Integer.parseInt(this.getUserContentTypeOct().getValue(), 10)), 4, '0');
        this.setFunctionCodeBin(binStr8Bits);
        this.setFunctionCodeHex(ByteTools.byteArr2HexStr(ByteTools.binStr2Bytes(binStr8Bits)));
    }
    public static FunctionCode giveMeOne() {
        if (instance == null) {
            instance = new FunctionCode();
            instance.initBySSALConfigProperties();
        }
        return instance.copy();
    }

    public FunctionCode(String functionCodeHex) {
        this.functionCodeHex = functionCodeHex;
        byte[] functionCodeBinArr = ByteTools.hexStr2ByteArr(functionCodeHex);
        String functionCodeBinArrStr = ByteTools.byteArr2BinStr(functionCodeBinArr);
        String functionCodeBinArrStr8Bit = StringUtils.leftPad(functionCodeBinArrStr, 8, '0');
        this.setFunctionCodeBin(functionCodeBinArrStr8Bit);
        this.setTransportDirection(FCCodeBinEnum.getByValue(functionCodeBinArrStr8Bit.substring(0, 1), 2));
        this.setStartUpSymbol(FCCodeBinEnum.getByValue(functionCodeBinArrStr8Bit.substring(1, 2), 2));
        this.setMainStationTriggerNegotiation(FCCodeBinEnum.getByValue(functionCodeBinArrStr8Bit.substring(3, 4), 2));
        this.setUserContentTypeOct(UserContentTypeOctEnum.getByValue(functionCodeBinArrStr8Bit.substring(4, 8), 2));
    }


    public String getFunctionCodeBin() {
        return functionCodeBin;
    }

    public void setFunctionCodeBin(String functionCodeBin) {
        this.functionCodeBin = functionCodeBin;
    }

    public String getFunctionCodeHex() {
        return functionCodeHex;
    }

    public void setFunctionCodeHex(String functionCodeHex) {
        this.functionCodeHex = functionCodeHex;
    }

    public FCCodeBinEnum getTransportDirection() {
        return transportDirection;
    }

    public void setTransportDirection(FCCodeBinEnum transportDirection) {
        this.transportDirection = transportDirection;
    }

    public FCCodeBinEnum getStartUpSymbol() {
        return startUpSymbol;
    }

    public void setStartUpSymbol(FCCodeBinEnum startUpSymbol) {
        this.startUpSymbol = startUpSymbol;
    }

    public String getRetainBin() {
        return retainBin;
    }

    public void setRetainBin(String retainBin) {
        this.retainBin = retainBin;
    }

    public FCCodeBinEnum getMainStationTriggerNegotiation() {
        return mainStationTriggerNegotiation;
    }

    public void setMainStationTriggerNegotiation(FCCodeBinEnum mainStationTriggerNegotiation) {
        this.mainStationTriggerNegotiation = mainStationTriggerNegotiation;
    }

    public UserContentTypeOctEnum getUserContentTypeOct() {
        return userContentTypeOct;
    }

    public void setUserContentTypeOct(UserContentTypeOctEnum userContentTypeOct) {
        this.userContentTypeOct = userContentTypeOct;
    }

    public FunctionCode copy() {
        try {
            return (FunctionCode) this.clone();
        }catch (CloneNotSupportedException e) {
            logger.error(e.getMessage());
            return this;
        }
    }

    @Override
    public String toString() {
        return "FunctionCode{" +
                "functionCodeHex='" + functionCodeHex + '\'' +
                ", functionCodeBin='" + functionCodeBin + '\'' +
                ", transportDirection=" + transportDirection +
                ", startUpSymbol=" + startUpSymbol +
                ", retainBin='" + retainBin + '\'' +
                ", mainStationTriggerNegotiation=" + mainStationTriggerNegotiation +
                ", userContentTypeOct=" + userContentTypeOct +
                '}';
    }
}
