package com.hzwq.codec.entity.bo;

import com.hzwq.codec.config.SSALConfigInitProperties;
import com.hzwq.codec.enums.DeviceTypeOctEnum;
import com.hzwq.codec.util.ByteTools;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*6.4.4 设备地址DA*/
    /*设备地址DA，标识终端的逻辑地址或网络地址，由设备地址长度和设备地址内容两部分组成。
    其中，设备地址长度为1字节；设备地址内容为变长，实际长度由设备地址长度决定。*/
    /*设备地址内容在设备类型字段中标识设备类型的bit位取值为01、02、03、24时表示的是设备的逻辑地址；
    其他取值时表示的是设备的网络IP地址。
    当设备地址内容为设备网络IP地址时，需反序传输。例如：IP为10.0.10.31，填充后应为1F 0A 00 0A。*/
public class DeviceAddress implements Cloneable {
    private static final Logger logger = LoggerFactory.getLogger(DeviceAddress.class);
    private static DeviceAddress instance;

    private String deviceAddressHex;

    private String deviceAddressLengthHex;
    private String deviceAddressLengthBin8Bits;
    /*设备地址长度为1字节*/
    /*Bit7 保留*/
    private String retainBit = "0";
    /*Bit6 Bit5 Bit4 地址长度*/
    private String mainStationAddressLengthBin;

    /*Bit3 Bit2 Bit1 Bit0 行政地址+逻辑地址长度*/
    private String AdministrativeOrLogicalAddressLengthBin;

    /*设备地址内容在设备类型字段中标识设备类型的bit位取值为01、02、03、24时表示的是设备的逻辑地址；其他取值时表示的是设备的网络IP地址。
    当设备地址内容为设备网络IP地址时，需反序传输。
    例如：IP为10.0.10.31，填充后应为1F 0A 00 0A。*/
    private boolean isLogicalAddress;
    private String administrativeOrLogicalAddressHex;
    private String administrativeOrLogicalAddress;
    private String mainStationAddressHex;
    private String mainStationAddress;

    //    用系统参数创建一个对象并返回
    private void initBySSALConfigProperties() {
        if (DeviceTypeOctEnum.PSP.equals(SSALConfigInitProperties.deviceType)) {
            this.setLogicalAddress(true);
            this.setAdministrativeOrLogicalAddress(SSALConfigInitProperties.pspMACAddress);
            this.setAdministrativeOrLogicalAddressHex(ByteTools.byteArr2HexStr(this.getAdministrativeOrLogicalAddress().getBytes()));
            this.setAdministrativeOrLogicalAddressLengthBin(StringUtils.leftPad(ByteTools.hexStr2BinStr(ByteTools.int2HexStr(this.getAdministrativeOrLogicalAddressHex().length() / 2)), 4, '0'));
        }else {
            this.setLogicalAddress(false);
            this.setAdministrativeOrLogicalAddress(SSALConfigInitProperties.localIp);
            String[] ipArr = SSALConfigInitProperties.localIp.replace(".", " ").split(" ");
            this.setAdministrativeOrLogicalAddressHex(
                    StringUtils.leftPad(ByteTools.int2HexStr(Integer.parseInt(ipArr[3], 10)), 2, '0')
                    + StringUtils.leftPad(ByteTools.int2HexStr(Integer.parseInt(ipArr[2], 10)), 2, '0')
                    + StringUtils.leftPad(ByteTools.int2HexStr(Integer.parseInt(ipArr[1], 10)), 2, '0')
                    + StringUtils.leftPad(ByteTools.int2HexStr(Integer.parseInt(ipArr[0], 10)), 2, '0'));
            this.setAdministrativeOrLogicalAddressLengthBin("0100");
        }
        this.setMainStationAddress(StringUtils.EMPTY);
        this.setMainStationAddressLengthBin("000");
        this.setMainStationAddressHex(StringUtils.EMPTY);

        String deviceAddressLengthBin8Bits = this.retainBit + this.getMainStationAddressLengthBin() + this.getAdministrativeOrLogicalAddressLengthBin();
        this.setDeviceAddressLengthBin8Bits(deviceAddressLengthBin8Bits);
        this.setDeviceAddressLengthHex(StringUtils.leftPad(ByteTools.int2HexStr(Integer.parseInt(this.getDeviceAddressLengthBin8Bits(), 2)), 2, '0'));
        this.setDeviceAddressHex(this.getDeviceAddressLengthHex() + this.getMainStationAddressHex() + this.getAdministrativeOrLogicalAddressHex());
    }

    public static DeviceAddress giveMeOne() {
        if (instance == null) {
            instance = new DeviceAddress();
            instance.initBySSALConfigProperties();
        }
        return instance.copy();
    }

    public String getDeviceAddressLengthBin8Bits() {
        return deviceAddressLengthBin8Bits;
    }

    public void setDeviceAddressLengthBin8Bits(String deviceAddressLengthBin8Bits) {
        this.deviceAddressLengthBin8Bits = deviceAddressLengthBin8Bits;
    }

    public String getDeviceAddressLengthHex() {
        return deviceAddressLengthHex;
    }

    public void setDeviceAddressLengthHex(String deviceAddressLengthHex) {
        this.deviceAddressLengthHex = deviceAddressLengthHex;
    }

    public String getRetainBit() {
        return retainBit;
    }

    public void setRetainBit(String retainBit) {
        this.retainBit = retainBit;
    }

    public String getMainStationAddressLengthBin() {
        return mainStationAddressLengthBin;
    }

    public void setMainStationAddressLengthBin(String mainStationAddressLengthBin) {
        this.mainStationAddressLengthBin = mainStationAddressLengthBin;
    }

    public String getAdministrativeOrLogicalAddressLengthBin() {
        return AdministrativeOrLogicalAddressLengthBin;
    }

    public void setAdministrativeOrLogicalAddressLengthBin(String administrativeOrLogicalAddressLengthBin) {
        AdministrativeOrLogicalAddressLengthBin = administrativeOrLogicalAddressLengthBin;
    }

    public boolean isLogicalAddress() {
        return isLogicalAddress;
    }

    public void setLogicalAddress(boolean logicalAddress) {
        isLogicalAddress = logicalAddress;
    }

    public String getAdministrativeOrLogicalAddress() {
        return administrativeOrLogicalAddress;
    }

    public void setAdministrativeOrLogicalAddress(String administrativeOrLogicalAddress) {
        this.administrativeOrLogicalAddress = administrativeOrLogicalAddress;
    }

    public String getMainStationAddress() {
        return mainStationAddress;
    }

    public void setMainStationAddress(String mainStationAddress) {
        this.mainStationAddress = mainStationAddress;
    }

    public String getDeviceAddressHex() {
        return deviceAddressHex;
    }

    public void setDeviceAddressHex(String deviceAddressHex) {
        this.deviceAddressHex = deviceAddressHex;
    }

    public String getAdministrativeOrLogicalAddressHex() {
        return administrativeOrLogicalAddressHex;
    }

    public void setAdministrativeOrLogicalAddressHex(String administrativeOrLogicalAddressHex) {
        this.administrativeOrLogicalAddressHex = administrativeOrLogicalAddressHex;
    }

    public String getMainStationAddressHex() {
        return mainStationAddressHex;
    }

    public void setMainStationAddressHex(String mainStationAddressHex) {
        this.mainStationAddressHex = mainStationAddressHex;
    }

    public DeviceAddress copy() {
        try {
            return (DeviceAddress) this.clone();
        }catch (CloneNotSupportedException e) {
            logger.error(e.getMessage());
            return this;
        }
    }

    @Override
    public String toString() {
        return "DeviceAddress{" +
                "deviceAddressHex='" + deviceAddressHex + '\'' +
                ", deviceAddressLengthHex='" + deviceAddressLengthHex + '\'' +
                ", deviceAddressLengthBin8Bits='" + deviceAddressLengthBin8Bits + '\'' +
                ", retainBit='" + retainBit + '\'' +
                ", mainStationAddressLengthBin='" + mainStationAddressLengthBin + '\'' +
                ", AdministrativeOrLogicalAddressLengthBin='" + AdministrativeOrLogicalAddressLengthBin + '\'' +
                ", isLogicalAddress=" + isLogicalAddress +
                ", administrativeOrLogicalAddressHex='" + administrativeOrLogicalAddressHex + '\'' +
                ", administrativeOrLogicalAddress='" + administrativeOrLogicalAddress + '\'' +
                ", mainStationAddressHex='" + mainStationAddressHex + '\'' +
                ", mainStationAddress='" + mainStationAddress + '\'' +
                '}';
    }

    //            6.4.4 设备地址DA
//              设备地址DA，标识终端的逻辑地址或网络地址，由设备地址长度和设备地址内容两部分组成。
//              其中，设备地址长度为1字节；设备地址内容为变长，实际长度由设备地址长度决定。
//              主站地址通过长度字节的bit6-bit4标识，为0，则和现有协议一样；逻辑地址含区域码和逻辑地址。该地址从应用协议中直接拷贝获取，即导向逻辑和应用协议一致。
//              设备地址内容在设备类型字段中标识设备类型的bit位取值为01、02、03、24时表示的是设备的逻辑地址；其他取值时表示的是设备的网络IP地址。
//              当设备地址内容为设备网络IP地址时，需反序传输。例如：IP为10.0.10.31，填充后应为1F 0A 00 0A。
    public String buildInstanceByHexString(String hexString, DeviceTypeOctEnum deviceType) {
        String deviceAddressLengthHexStr = hexString.substring(0, 2);
        this.setDeviceAddressLengthHex(deviceAddressLengthHexStr);
        String deviceAddressLengthBinStr = ByteTools.byteArr2BinStr(ByteTools.hexStr2ByteArr(deviceAddressLengthHexStr));
        String deviceAddressLengthBinStr8Bit = StringUtils.leftPad(deviceAddressLengthBinStr,8, '0');
        this.setDeviceAddressLengthBin8Bits(deviceAddressLengthBinStr8Bit);
        //主站长度二进制
        String mainStationAddressBinStr = deviceAddressLengthBinStr8Bit.substring(1,4);
        this.setMainStationAddressLengthBin(mainStationAddressBinStr);
        //截取转化为 byte 数组
        String mainStationAddressBinStr8Bit = StringUtils.leftPad(mainStationAddressBinStr,8, '0');
        //主站长度
        int mainStationAddressLengthOct = ByteTools.binStr2Bytes(mainStationAddressBinStr8Bit)[0]&0xff;

        //行政地址和逻辑地址
        String administrativeOrLogicalAddressLengthBin = deviceAddressLengthBinStr8Bit.substring(4, 8);
        this.setAdministrativeOrLogicalAddressLengthBin(administrativeOrLogicalAddressLengthBin);
        String AdministrativeOrLogicalAddressLengthBin8Bit = StringUtils.leftPad(administrativeOrLogicalAddressLengthBin, 8, '0');
        //行政地址和逻辑地址长度
        int administrativeOrLogicalAddressLengthOct = ByteTools.binStr2Bytes(AdministrativeOrLogicalAddressLengthBin8Bit)[0]&0xff;
        //当存在主站长度时，行政地址长度或者逻辑地址长度+1
        if(mainStationAddressLengthOct > 0) {
            administrativeOrLogicalAddressLengthOct += 1;
        }

        boolean isLogicalAddress = deviceType != null &&
                (deviceType.getValue().equals("01")
                || deviceType.getValue().equals("02")
                || deviceType.getValue().equals("03")
                || deviceType.getValue().equals("24"));
        this.setLogicalAddress(isLogicalAddress);
        if (isLogicalAddress) {
            //为逻辑地址时
            String administrativeOrLogicalAddressHex = hexString.substring(2, 2 + administrativeOrLogicalAddressLengthOct * 2);
            this.setAdministrativeOrLogicalAddressHex(administrativeOrLogicalAddressHex);
            this.setAdministrativeOrLogicalAddress(ByteTools.hexStr2Str(administrativeOrLogicalAddressHex));
        }else {
            String administrativeOrLogicalAddressHex = hexString.substring(2, 2 + administrativeOrLogicalAddressLengthOct * 2);
            this.setAdministrativeOrLogicalAddressHex(administrativeOrLogicalAddressHex);
            String administrativeOrLogicalAddress =
                    String.valueOf(Integer.parseInt(administrativeOrLogicalAddressHex.substring(6, 8), 16)) + "." +
                            Integer.parseInt(administrativeOrLogicalAddressHex.substring(4, 6), 16) + "." +
                            Integer.parseInt(administrativeOrLogicalAddressHex.substring(2, 4), 16) + "." +
                            Integer.parseInt(administrativeOrLogicalAddressHex.substring(0, 2), 16);
            this.setAdministrativeOrLogicalAddress(administrativeOrLogicalAddress);
        }
        hexString = hexString.substring(2 + administrativeOrLogicalAddressLengthOct * 2);
        String mainStationAddressHex = hexString.substring(0, mainStationAddressLengthOct * 2);
        this.setMainStationAddressHex(mainStationAddressHex);
        this.setMainStationAddress(ByteTools.hexStr2Str(mainStationAddressHex));
        this.setDeviceAddressHex(this.getDeviceAddressLengthHex() + this.getMainStationAddressHex() + this.getAdministrativeOrLogicalAddressHex());
        hexString = hexString.substring(mainStationAddressLengthOct * 2);
        return hexString;
    }
}
