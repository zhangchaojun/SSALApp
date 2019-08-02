package com.hzwq.codec.entity.bo;

import com.hzwq.codec.config.SSALConfigInitProperties;
import com.hzwq.codec.enums.AddressTypeOctEnum;
import com.hzwq.codec.enums.DeviceTypeOctEnum;
import com.hzwq.codec.enums.PSPAPPProtocolVersionOctEnum;
import com.hzwq.codec.util.ByteTools;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*6.4.3 设备地址类型DAT*/
/*设备地址类型DAT，标识终端的地址类型，由2个字节组成。*/
/*该字段采用反序传输，
例如：设备类型1，地址域类型0，协议版本01，SSAL报文中实际填写为 01 08。*/
public class DeviceAddressType implements Cloneable {
    private static final Logger logger = LoggerFactory.getLogger(DeviceAddressType.class);

    private static DeviceAddressType instance;

    private String deviceAddressTypeHexRevrs;

    private String deviceAddressTypeHex;

    /*设备类型（bit11~bit15）*/
    private String deviceTypeBinStr;
    private DeviceTypeOctEnum deviceType;

    /*b) 地址类型（bit8~bit10）*/
    private String addressTypeBinStr;
    private AddressTypeOctEnum addressType;

    /*c) 保留（bit5~bit7）*/
    private String obtainBin = "000";

    /*d) 应用协议版本（bit0~bit4）*/
    private String pspAppProtocolVersionBinStr;
    private PSPAPPProtocolVersionOctEnum pspAppProtocolVersion;

    public DeviceAddressType() {
    }

    //    用系统参数创建一个对象并返回
    private void initBySSALConfigProperties() {
        this.setDeviceType(SSALConfigInitProperties.deviceType);
        this.setAddressType(AddressTypeOctEnum.STANDALONE);
        this.setPspAppProtocolVersion(PSPAPPProtocolVersionOctEnum.STATUTE_16);
        this.buildDeviceAddressTypeHexRevrs();
    }
    public static DeviceAddressType giveMeOne() {
        if (instance == null) {
            instance = new DeviceAddressType();
            instance.initBySSALConfigProperties();
        }
        return instance.copy();
    }

    private void buildDeviceAddressTypeHexRevrs() {
        this.setPspAppProtocolVersion(SSALConfigInitProperties.pspAppProtocolVerion);
        this.setPspAppProtocolVersionBinStr(StringUtils.leftPad(ByteTools.hexStr2BinStr(ByteTools.int2HexStr(Integer.parseInt(this.getPspAppProtocolVersion().getValue(), 10))), 5, '0'));
        addressTypeBinStr = StringUtils.leftPad(ByteTools.hexStr2BinStr(ByteTools.int2HexStr(Integer.parseInt(addressType.getValue(), 10))), 3, '0');
        deviceTypeBinStr = StringUtils.leftPad(ByteTools.hexStr2BinStr(ByteTools.int2HexStr(Integer.parseInt(deviceType.getValue(), 10))), 5, '0');
        String highByteBinStr = deviceTypeBinStr + addressTypeBinStr;
        String lowerByteBinStr = obtainBin + pspAppProtocolVersionBinStr;
        String highByteHexStr = StringUtils.leftPad(ByteTools.byteArr2HexStr(ByteTools.binStr2Bytes(highByteBinStr)), 2, '0');
        String lowerByteHexStr = StringUtils.leftPad(ByteTools.byteArr2HexStr(ByteTools.binStr2Bytes(lowerByteBinStr)), 2, '0');
        deviceAddressTypeHex = highByteHexStr + lowerByteHexStr;
        deviceAddressTypeHexRevrs = lowerByteHexStr + highByteHexStr;
    }
    //            6.4.3 设备地址类型DAT
//            设备地址类型DAT，标识终端的地址类型，由2个字节组成。
//            该字段采用反序传输，例如：设备类型1，地址域类型0，协议版本01，SSAL报文中实际填写为 01 08。
    public DeviceAddressType(String deviceAddressTypeHexRevrs) {
        this.setDeviceAddressTypeHexRevrs(deviceAddressTypeHexRevrs);
        String deviceAddressTypeHex = deviceAddressTypeHexRevrs.substring(2,4) + deviceAddressTypeHexRevrs.substring(0,2);
        this.setDeviceAddressTypeHex(deviceAddressTypeHex);
        String deviceAddressTypeBinStr = ByteTools.byteArr2BinStr(ByteTools.hexStr2ByteArr(deviceAddressTypeHex));
        String [] deviceAddressTypeBinArr = deviceAddressTypeBinStr.split(",");
        if(deviceAddressTypeBinArr.length == 2) {
            deviceAddressTypeBinStr = StringUtils.leftPad(deviceAddressTypeBinArr[0],8, '0');
            deviceAddressTypeBinStr = deviceAddressTypeBinStr+ StringUtils.leftPad(deviceAddressTypeBinArr[1],8, '0');
        }
        //1.解析设备类型
        //把 dat 转化为 二进制数据
        String deviceTypeBinStr = deviceAddressTypeBinStr.substring(0, 5);
        this.setDeviceTypeBinStr(deviceTypeBinStr);
        this.setDeviceType(DeviceTypeOctEnum.getByValue(deviceTypeBinStr, 2));

        String addressTypeBinStr = deviceAddressTypeBinStr.substring(5, 8);
        this.setAddressTypeBinStr(addressTypeBinStr);
        this.setAddressType(AddressTypeOctEnum.getByValue(addressTypeBinStr, 2));

        String pspAppProtocolVersionBinStr = deviceAddressTypeBinStr.substring(11, 16);
        this.setPspAppProtocolVersionBinStr(pspAppProtocolVersionBinStr);
        this.setPspAppProtocolVersion(PSPAPPProtocolVersionOctEnum.getByValue(pspAppProtocolVersionBinStr, 2));
    }


    public String getDeviceTypeBinStr() {
        return deviceTypeBinStr;
    }

    public void setDeviceTypeBinStr(String deviceTypeBinStr) {
        this.deviceTypeBinStr = deviceTypeBinStr;
    }

    public String getAddressTypeBinStr() {
        return addressTypeBinStr;
    }

    public void setAddressTypeBinStr(String addressTypeBinStr) {
        this.addressTypeBinStr = addressTypeBinStr;
    }

    public String getDeviceAddressTypeHexRevrs() {
        return deviceAddressTypeHexRevrs;
    }

    public void setDeviceAddressTypeHexRevrs(String deviceAddressTypeHexRevrs) {
        this.deviceAddressTypeHexRevrs = deviceAddressTypeHexRevrs;
    }

    public String getDeviceAddressTypeHex() {
        return deviceAddressTypeHex;
    }

    public void setDeviceAddressTypeHex(String deviceAddressTypeHex) {
        this.deviceAddressTypeHex = deviceAddressTypeHex;
    }

    public DeviceTypeOctEnum getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceTypeOctEnum deviceType) {
        this.deviceType = deviceType;
    }

    public AddressTypeOctEnum getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressTypeOctEnum addressType) {
        this.addressType = addressType;
    }

    public String getObtainBin() {
        return obtainBin;
    }

    public void setObtainBin(String obtainBin) {
        this.obtainBin = obtainBin;
    }

    public PSPAPPProtocolVersionOctEnum getPspAppProtocolVersion() {
        return pspAppProtocolVersion;
    }

    public void setPspAppProtocolVersion(PSPAPPProtocolVersionOctEnum pspAppProtocolVersion) {
        this.pspAppProtocolVersion = pspAppProtocolVersion;
    }

    public String getPspAppProtocolVersionBinStr() {
        return pspAppProtocolVersionBinStr;
    }

    public void setPspAppProtocolVersionBinStr(String pspAppProtocolVersionBinStr) {
        this.pspAppProtocolVersionBinStr = pspAppProtocolVersionBinStr;
    }

    public DeviceAddressType copy() {
        try {
            return (DeviceAddressType) this.clone();
        }catch (CloneNotSupportedException e) {
            logger.error(e.getMessage());
            return this;
        }
    }

    @Override
    public String toString() {
        return "DeviceAddressType{" +
                "deviceAddressTypeHexRevrs='" + deviceAddressTypeHexRevrs + '\'' +
                ", deviceAddressTypeHex='" + deviceAddressTypeHex + '\'' +
                ", deviceTypeBinStr='" + deviceTypeBinStr + '\'' +
                ", deviceType=" + deviceType +
                ", addressTypeBinStr='" + addressTypeBinStr + '\'' +
                ", addressType=" + addressType +
                ", obtainBin='" + obtainBin + '\'' +
                ", pspAppProtocolVersionBinStr='" + pspAppProtocolVersionBinStr + '\'' +
                ", pspAppProtocolVerion=" + pspAppProtocolVersion +
                '}';
    }
}
