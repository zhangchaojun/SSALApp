package com.hzwq.codec.entity.bo;

import com.hzwq.codec.config.SSALConfigInitProperties;
import com.hzwq.codec.util.ByteTools;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*6.4.5 源地址SA*/
    /*源地址SA，标识传输帧的源IP地址，包括端口长度、源地址长度和源地址内容、端口内容四部分。
    其中，端口长度为bit5、bit6、bit7表示长度，源地址长度为低5位（bit0、bit1、bit2、bit3、bit4），源地址内容为变长，
    实际长度由源地址长度决定。端口内容根据端口长度来判断。*/
public class SourceAddress implements Cloneable {
    private static final Logger logger = LoggerFactory.getLogger(SourceAddress.class);
    private static SourceAddress instance;
    private String sourceAddressHex;
    private String sourceAddressLengthHex;

    private String sourceAddressLengthBin;
    /*a) 端口长度（bit5~bit7）
    标识报文源地址的端口长度。
    如果为端口长度为0，则端口不存在；当端口不存在时，源地址可以为IP地址，也可以为逻辑地址；
    逻辑地址是链路通道的逻辑编号，最长为2字节，范围为0-65535。*/
    private String sourcePortLengthBin;

    /*b) 源地址长度（bit0~bit4）
    标识报文源地址的长度。*/
    private String sourceIpLengthBin;

    /*c) 源地址内容（BYTE1~BYTEn）和端口内容
    标识传输帧的源IP地址，采用BIN码填写，反序传输。
    例1：IP地址为10.0.10.31、18002，实际SSAL报文中填写为：1F 0A 00 0A52 46；
    例2：逻辑地址为F01B，实际SSAL报文中填写为：1BF0。*/
    private String sourceIp;
    private String sourceIpHex;

    private int sourcePort;
    private String sourcePortHex;

    public SourceAddress() {
    }

    //    用系统参数创建一个对象并返回
    private void initBySSALConfigProperties() {
        if (StringUtils.isEmpty(SSALConfigInitProperties.localIp)) {
            this.setSourceIp(StringUtils.EMPTY);
            this.setSourcePort(0);
            this.setSourcePortLengthBin("000");
            this.setSourceIpLengthBin("00000");
            this.setSourceAddressLengthHex("00");
            this.setSourceAddressHex("00");
        }else {
            String[] ipArr = SSALConfigInitProperties.localIp.replace(".", " ").split(" ");
            String ipHex =
                    StringUtils.leftPad(ByteTools.int2HexStr(Integer.parseInt(ipArr[3], 10)), 2, '0')
                    + StringUtils.leftPad(ByteTools.int2HexStr(Integer.parseInt(ipArr[2], 10)), 2, '0')
                    + StringUtils.leftPad(ByteTools.int2HexStr(Integer.parseInt(ipArr[1], 10)), 2, '0')
                    + StringUtils.leftPad(ByteTools.int2HexStr(Integer.parseInt(ipArr[0], 10)), 2, '0');
            String portHex = ByteTools.int2HexStr(SSALConfigInitProperties.localPort);
            if (portHex.length() > 2) {
                portHex = portHex.substring(2, 4) + portHex.substring(0, 2);
            }
            this.setSourceIp(SSALConfigInitProperties.localIp);
            this.setSourceIpHex(ipHex);
            this.setSourcePort(SSALConfigInitProperties.localPort);
            this.setSourcePortHex(portHex);
            this.setSourceIpLengthBin(StringUtils.leftPad(Integer.toBinaryString(ipHex.length() / 2), 5, '0'));
            this.setSourcePortLengthBin(StringUtils.leftPad(Integer.toBinaryString(portHex.length() / 2), 3, '0'));
            String sourceAddressLengthBinStr = this.getSourcePortLengthBin() + this.getSourceIpLengthBin();
            this.setSourceAddressLengthBin(sourceAddressLengthBinStr);
            this.setSourceAddressLengthHex(StringUtils.leftPad(ByteTools.byteArr2HexStr(ByteTools.binStr2Bytes(this.getSourceAddressLengthBin())), 2, '0'));
            this.setSourceAddressHex(this.getSourceAddressLengthHex() + this.getSourceIpHex() + this.getSourcePortHex());
        }
    }

    public static SourceAddress giveMeOne() {
        if (instance == null) {
            instance = new SourceAddress();
            instance.initBySSALConfigProperties();
        }
        return instance.copy();
    }

    public String getSourceAddressLengthBin() {
        return sourceAddressLengthBin;
    }

    public void setSourceAddressLengthBin(String sourceAddressLengthBin) {
        this.sourceAddressLengthBin = sourceAddressLengthBin;
    }

    public String getSourceIpLengthBin() {
        return sourceIpLengthBin;
    }

    public void setSourceIpLengthBin(String sourceIpLengthBin) {
        this.sourceIpLengthBin = sourceIpLengthBin;
    }

    public String getSourcePortLengthBin() {
        return sourcePortLengthBin;
    }

    public void setSourcePortLengthBin(String sourcePortLengthBin) {
        this.sourcePortLengthBin = sourcePortLengthBin;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public int getSourcePort() {
        return sourcePort;
    }

    public void setSourcePort(int sourcePort) {
        this.sourcePort = sourcePort;
    }

    public String getSourceAddressHex() {
        return sourceAddressHex;
    }

    public void setSourceAddressHex(String sourceAddressHex) {
        this.sourceAddressHex = sourceAddressHex;
    }

    public String getSourceAddressLengthHex() {
        return sourceAddressLengthHex;
    }

    public void setSourceAddressLengthHex(String sourceAddressLengthHex) {
        this.sourceAddressLengthHex = sourceAddressLengthHex;
    }

    public String getSourceIpHex() {
        return sourceIpHex;
    }

    public void setSourceIpHex(String sourceIpHex) {
        this.sourceIpHex = sourceIpHex;
    }

    public String getSourcePortHex() {
        return sourcePortHex;
    }

    public void setSourcePortHex(String sourcePortHex) {
        this.sourcePortHex = sourcePortHex;
    }

    public SourceAddress copy() {
        try {
            return (SourceAddress) this.clone();
        }catch (CloneNotSupportedException e) {
            logger.error(e.getMessage());
            return this;
        }
    }

    @Override
    public String toString() {
        return "SourceAddress{" +
                "sourceAddressHex='" + sourceAddressHex + '\'' +
                ", sourceAddressLengthHex='" + sourceAddressLengthHex + '\'' +
                ", sourceAddressLengthBin='" + sourceAddressLengthBin + '\'' +
                ", sourcePortLengthBin='" + sourcePortLengthBin + '\'' +
                ", sourceIpLengthBin='" + sourceIpLengthBin + '\'' +
                ", sourceIp='" + sourceIp + '\'' +
                ", sourceIpHex='" + sourceIpHex + '\'' +
                ", sourcePort=" + sourcePort +
                ", sourcePortHex='" + sourcePortHex + '\'' +
                '}';
    }

    //            6.4.5 源地址SA
//            源地址SA，标识传输帧的源IP地址，包括源地址长度和源地址内容两部分。
//            其中，源地址长度为1字节，源地址内容为变长，实际长度由源地址长度决定。
    public String buildInstanceByHexString(String hexString) {
        String sourceAddressLengthHex = hexString.substring(0,2);
        this.setSourceAddressLengthHex(sourceAddressLengthHex);
        this.setSourceAddressHex(sourceAddressLengthHex);
        if (Integer.parseInt(sourceAddressLengthHex, 16) != 0) {
            String sourceAddressLengthBin = ByteTools.byteArr2BinStr(ByteTools.hexStr2ByteArr(sourceAddressLengthHex));
            String sourceAddressLengthBin8Bit = StringUtils.leftPad(sourceAddressLengthBin, 8, '0');
            this.setSourceAddressLengthBin(sourceAddressLengthBin8Bit);
            //端口长度二进制
            String portLenBin = sourceAddressLengthBin8Bit.substring(0,3);
            String ipLenBin = sourceAddressLengthBin8Bit.substring(3,8);
            this.setSourcePortLengthBin(portLenBin);
            this.setSourceIpLengthBin(ipLenBin);
            //转化为端口长度 int 10进制
            String portLenBin8Bit = StringUtils.leftPad(portLenBin,8, '0');
            int portByteCount = ByteTools.binStr2Bytes(portLenBin8Bit)[0]&0xff;
            String ipLenBin8Bit = StringUtils.leftPad(ipLenBin,8, '0');
            int ipByteCount = ByteTools.binStr2Bytes(ipLenBin8Bit)[0]&0xff;
            if(ipByteCount + portByteCount > 0) {
                String sourceAddressHex = hexString.substring(2, 2 + (ipByteCount + portByteCount) * 2);
                String sourceIpHex = sourceAddressHex.substring(0, ipByteCount * 2);
                this.setSourceIpHex(sourceIpHex);
                this.setSourceAddressHex(sourceAddressLengthHex + sourceIpHex);
                String sourceIp = String.valueOf(Integer.valueOf(sourceIpHex.substring(6, 8), 16)) + "." +
                        Integer.valueOf(sourceIpHex.substring(4, 6), 16) + "." +
                        Integer.valueOf(sourceIpHex.substring(2, 4), 16) + "." +
                        Integer.valueOf(sourceIpHex.substring(0, 2), 16);
                this.setSourceIp(sourceIp);

                if (portByteCount > 0) {
                    String sourcePortHex = sourceAddressHex.substring(ipByteCount * 2);
                    this.setSourcePortHex(sourcePortHex);
                    this.setSourceAddressHex(sourceAddressLengthHex + sourceIpHex + sourcePortHex);
                    int sourcePort;
                    if (portByteCount > 1) {
                        sourcePort = Integer.parseInt(sourcePortHex.substring(2, 4) + sourcePortHex.substring(0, 2), 16);
                    }else {
                        sourcePort = Integer.parseInt(sourcePortHex, 16);
                    }
                    this.setSourcePort(sourcePort);
                }
                hexString = hexString.substring(2 + (ipByteCount + portByteCount) * 2);
            }
        }else {
            hexString = hexString.substring(2);
        }
        return hexString;
    }
}
