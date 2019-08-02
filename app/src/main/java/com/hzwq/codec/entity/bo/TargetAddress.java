package com.hzwq.codec.entity.bo;

import com.hzwq.codec.util.ByteTools;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/*6.4.6 目的地址TA
目的地址TA，标识传输帧的目的IP地址，包括端口长度、目的地址长度和目的地址内容、端口内容四部分*/
public class TargetAddress implements Cloneable {
    private static final Logger logger = LoggerFactory.getLogger(TargetAddress.class);
    private static Map<String, TargetAddress> instanceStore = new HashMap<>();
    private String targetAddressHex;
    private String targetAddressLengthHex;
    private String targetAddressLengthBin;
    /*目的地址长度为1字节*/
    /*a) 端口长度（bit5~bit7）
    其中，端口长度为bit5、bit6、bit7表示长度，
    目的地址长度为1字节低5位（bit0、bit1、bit2、bit3、bit4），
    目的地址内容为变长，实际长度由目的地址长度决定。*/
    /*如果为端口长度为0，则端口不存在；
    当端口不存在时，目的地址可以为IP地址，也可以为逻辑地址；
    逻辑地址是链路通道的逻辑编号，最长为2字节，范围为0-65535。*/
    private String portLengthBin;

    /*b) 目的地址长度（bit0~bit4）
        标识报文目的地址的长度。*/
    /*目的地址内容为变长, 实际长度由目的地址长度决定
     * 目的地址的定义与源地址定义相同。*/
    private String targetIpAddressLengthBin;


    /*c) 目的地址内容（BYTE1~BYTEn）和端口内容
    标识传输帧的目的IP地址，采用BIN码填写，反序传输。
    例1：IP地址为10.0.10.31、18002，
    实际SSAL报文中填写为：1F 0A 00 0A52 46；例2：逻辑地址为F01B，实际SSAL报文中填写为：1BF0。*/
    private int targetPort;
    private String targetPortHex;
    private String targetIpAddress;
    private String targetIpAddressHex;

    public TargetAddress() {
    }

    //    用系统参数创建一个对象并返回
    private void initBySSALConfigProperties(String targetIp, int targetPort) {
        this.setTargetIpAddress(targetIp);
        this.setTargetPort(targetPort);

        String[] ipArr = targetIp.replace(".", " ").split(" ");
        String ipHex =
                StringUtils.leftPad(ByteTools.int2HexStr(Integer.parseInt(ipArr[3], 10)), 2, '0')
                + StringUtils.leftPad(ByteTools.int2HexStr(Integer.parseInt(ipArr[2], 10)), 2, '0')
                + StringUtils.leftPad(ByteTools.int2HexStr(Integer.parseInt(ipArr[1], 10)), 2, '0')
                + StringUtils.leftPad(ByteTools.int2HexStr(Integer.parseInt(ipArr[0], 10)), 2, '0');
        this.setTargetIpAddressHex(ipHex);
        String portHex = ByteTools.int2HexStr(targetPort);
        if (portHex.length() > 2) {
            portHex = portHex.substring(2, 4) + portHex.substring(0, 2);
        }
        this.setTargetPortHex(portHex);

        this.setPortLengthBin(StringUtils.leftPad(Integer.toBinaryString(portHex.length() / 2), 3, '0'));
        this.setTargetIpAddressLengthBin(StringUtils.leftPad(Integer.toBinaryString(ipHex.length() / 2), 5, '0'));

        this.setTargetAddressLengthBin(this.getPortLengthBin() + this.getTargetIpAddressLengthBin());
        this.setTargetAddressLengthHex(ByteTools.byteArr2HexStr(ByteTools.binStr2Bytes(this.getTargetAddressLengthBin())));
        this.setTargetAddressHex(this.getTargetAddressLengthHex() + this.getTargetIpAddressHex() + this.getTargetPortHex());
    }

//    下行/上行
    public static TargetAddress giveMeOne(String targetIp, int targetPort) {
        String key = targetIp + ":" + targetPort;
        TargetAddress instanceDown;
        if (!instanceStore.containsKey(key)) {
            instanceDown = new TargetAddress();
            instanceDown.initBySSALConfigProperties(targetIp, targetPort);
            instanceStore.put(key, instanceDown);
        }else {
            instanceDown = instanceStore.get(key);
        }

        return instanceDown;
    }

    public String getPortLengthBin() {
        return portLengthBin;
    }

    public void setPortLengthBin(String portLengthBin) {
        this.portLengthBin = portLengthBin;
    }

    public String getTargetIpAddressLengthBin() {
        return targetIpAddressLengthBin;
    }

    public void setTargetIpAddressLengthBin(String targetIpAddressLengthBin) {
        this.targetIpAddressLengthBin = targetIpAddressLengthBin;
    }

    public int getTargetPort() {
        return targetPort;
    }

    public void setTargetPort(int targetPort) {
        this.targetPort = targetPort;
    }

    public String getTargetIpAddress() {
        return targetIpAddress;
    }

    public void setTargetIpAddress(String targetIpAddress) {
        this.targetIpAddress = targetIpAddress;
    }

    public String getTargetAddressHex() {
        return targetAddressHex;
    }

    public void setTargetAddressHex(String targetAddressHex) {
        this.targetAddressHex = targetAddressHex;
    }

    public String getTargetAddressLengthHex() {
        return targetAddressLengthHex;
    }

    public void setTargetAddressLengthHex(String targetAddressLengthHex) {
        this.targetAddressLengthHex = targetAddressLengthHex;
    }

    public String getTargetAddressLengthBin() {
        return targetAddressLengthBin;
    }

    public void setTargetAddressLengthBin(String targetAddressLengthBin) {
        this.targetAddressLengthBin = targetAddressLengthBin;
    }

    public String getTargetPortHex() {
        return targetPortHex;
    }

    public void setTargetPortHex(String targetPortHex) {
        this.targetPortHex = targetPortHex;
    }

    public String getTargetIpAddressHex() {
        return targetIpAddressHex;
    }

    public void setTargetIpAddressHex(String targetIpAddressHex) {
        this.targetIpAddressHex = targetIpAddressHex;
    }

    public TargetAddress copy() {
        try {
            return (TargetAddress) this.clone();
        }catch (CloneNotSupportedException e) {
            logger.error(e.getMessage());
            return this;
        }
    }

    @Override
    public String toString() {
        return "TargetAddress{" +
                "targetAddressHex='" + targetAddressHex + '\'' +
                ", targetAddressLengthHex='" + targetAddressLengthHex + '\'' +
                ", targetAddressLengthBin='" + targetAddressLengthBin + '\'' +
                ", portLengthBin='" + portLengthBin + '\'' +
                ", targetIpAddressLengthBin='" + targetIpAddressLengthBin + '\'' +
                ", targetPort=" + targetPort +
                ", targetPortHex='" + targetPortHex + '\'' +
                ", targetIpAddress='" + targetIpAddress + '\'' +
                ", targetIpAddressHex='" + targetIpAddressHex + '\'' +
                '}';
    }

    //           6.4.6 目的地址TA
//            目的地址TA，标识传输帧的目的IP地址，包括端口长度、目的地址长度和目的地址内容、端口内容四部分
//            /*目的地址TA，标识传输帧的目的IP地址，包括目的地址长度和目的地址内容两部分。
//            其中，目的地址长度为1字节，
    public String buidInstanceByHexString(String hexString) {
        String targetAddressLengthHex = hexString.substring(0, 2);
        this.setTargetAddressLengthHex(targetAddressLengthHex);
        this.setTargetAddressHex(targetAddressLengthHex);
//            其中，端口长度为bit5、bit6、bit7表示长度，
        String targetAddressLengthBin = ByteTools.hexStr2BinStr(targetAddressLengthHex);
        String targetAddressLengthBin8Bit = StringUtils.leftPad(targetAddressLengthBin, 8, '0');
        this.setTargetAddressLengthBin(targetAddressLengthBin8Bit);
        String portLengthBin = targetAddressLengthBin8Bit.substring(0, 3);
        String targetIpAddressLengthBin = targetAddressLengthBin8Bit.substring(3);
        this.setPortLengthBin(portLengthBin);
        this.setTargetIpAddressLengthBin(targetIpAddressLengthBin);
        int addressByteCount = Integer.parseInt(targetIpAddressLengthBin, 2);
        String targetAddressStr = StringUtils.EMPTY;
        if (addressByteCount > 0) {
            String ipAddressHexRevrs = hexString.substring(2, 2+ addressByteCount * 2);
            this.setTargetIpAddressHex(ipAddressHexRevrs);
            this.setTargetAddressHex(targetAddressLengthHex + ipAddressHexRevrs);
            hexString = hexString.substring(2 + addressByteCount * 2);
            targetAddressStr = Integer.parseInt(ipAddressHexRevrs.substring(6, 8), 16) + "."
                    + Integer.parseInt(ipAddressHexRevrs.substring(4, 6), 16) + "."
                    + Integer.parseInt(ipAddressHexRevrs.substring(2, 4), 16) + "."
                    +Integer.parseInt(ipAddressHexRevrs.substring(0, 2), 16);
        }
        this.setTargetIpAddress(targetAddressStr);
        int portByteCount = Integer.parseInt(portLengthBin, 2);
        int targetPort = 0;
        if (portByteCount > 0) {
            String portHexRevrs = hexString.substring(0, portByteCount * 2);
            this.setTargetPortHex(portHexRevrs);
            this.setTargetAddressHex(this.getTargetAddressHex() + portHexRevrs);
            hexString = hexString.substring(portByteCount * 2);
            if (portByteCount > 1) {
                targetPort = Integer.parseInt(portHexRevrs.substring(2, 4) + portHexRevrs.substring(0, 2), 16);
            }else {
                targetPort = Integer.parseInt(portHexRevrs, 16);
            }
        }
        this.setTargetPort(targetPort);
        return hexString;
    }
}
