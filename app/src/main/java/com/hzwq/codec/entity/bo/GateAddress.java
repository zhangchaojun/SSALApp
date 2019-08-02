package com.hzwq.codec.entity.bo;

import com.hzwq.codec.config.SSALConfigInitProperties;
import com.hzwq.codec.util.ByteTools;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*6.4.9 网关地址GA
   网关地址GA，标识传输帧的安全网关地址，包括网关地址长度和网关地址内容两部分。
   其中，网关地址长度为1字节，网关地址内容为变长，实际长度由网关地址长度决定。*/
public class GateAddress implements Cloneable {
    private static final Logger logger = LoggerFactory.getLogger(GateAddress.class);
    private static GateAddress instance;
    private String gateAddressOriginalHex;
    private String gateAddressLengthHex;
    private String gateAddressLengthBin8Bits;
    /*bit7 bit6 bit5 保留*/
    private String retainBin = "000";

    /*bit4 bit3 bit2 bit1 bit0 网关地址长度*/
    private String gateAddressLengthBin;

    /*网关地址内容*/
    private String gateAddressHex;
    private String gateAddress;

    //    用系统参数创建一个对象并返回
    private void initBySSALConfigProperties() {
        if (StringUtils.isEmpty(SSALConfigInitProperties.gateIp)) {
            instance.setGateAddress(StringUtils.EMPTY);
            instance.setGateAddressHex(StringUtils.EMPTY);
            instance.setGateAddressLengthBin("00000");
            instance.setGateAddressLengthHex("00");
        }else {
            instance.setGateAddress(SSALConfigInitProperties.gateIp);
            String[] ipArr = SSALConfigInitProperties.gateIp.replace(".", " ").split(" ");
            instance.setGateAddressHex(
                    StringUtils.leftPad(ByteTools.int2HexStr(Integer.parseInt(ipArr[3], 10)), 2, '0')
                    + StringUtils.leftPad(ByteTools.int2HexStr(Integer.parseInt(ipArr[2], 10)), 2, '0')
                    + StringUtils.leftPad(ByteTools.int2HexStr(Integer.parseInt(ipArr[1], 10)), 2, '0')
                    + StringUtils.leftPad(ByteTools.int2HexStr(Integer.parseInt(ipArr[0], 10)), 2, '0')
            );
            int addressBytesCount = instance.getGateAddressHex().length() / 2;
            instance.setGateAddressLengthBin(StringUtils.leftPad(ByteTools.hexStr2BinStr(ByteTools.int2HexStr(addressBytesCount)), 5, '0'));
            instance.setGateAddressLengthHex(StringUtils.leftPad(ByteTools.int2HexStr(addressBytesCount), 2, '0'));
        }
        instance.setGateAddressLengthBin8Bits(instance.getRetainBin() + instance.getGateAddressLengthBin());
        instance.setGateAddressOriginalHex(instance.getGateAddressLengthHex() + instance.getGateAddressHex());
    }

    public static GateAddress giveMeOne() {
        if (instance == null) {
            instance = new GateAddress();
            instance.initBySSALConfigProperties();
        }
        return instance.copy();
    }

    public String getGateAddressOriginalHex() {
        return gateAddressOriginalHex;
    }

    public void setGateAddressOriginalHex(String gateAddressOriginalHex) {
        this.gateAddressOriginalHex = gateAddressOriginalHex;
    }

    public String getGateAddressLengthBin8Bits() {
        return gateAddressLengthBin8Bits;
    }

    public void setGateAddressLengthBin8Bits(String gateAddressLengthBin8Bits) {
        this.gateAddressLengthBin8Bits = gateAddressLengthBin8Bits;
    }

    public String getRetainBin() {
        return retainBin;
    }

    public void setRetainBin(String retainBin) {
        this.retainBin = retainBin;
    }

    public String getGateAddressLengthBin() {
        return gateAddressLengthBin;
    }

    public void setGateAddressLengthBin(String gateAddressLengthBin) {
        this.gateAddressLengthBin = gateAddressLengthBin;
    }

    public String getGateAddressHex() {
        return gateAddressHex;
    }

    public void setGateAddressHex(String gateAddressHex) {
        this.gateAddressHex = gateAddressHex;
    }

    public String getGateAddress() {
        return gateAddress;
    }

    public void setGateAddress(String gateAddress) {
        this.gateAddress = gateAddress;
    }

    public String getGateAddressLengthHex() {
        return gateAddressLengthHex;
    }

    public void setGateAddressLengthHex(String gateAddressLengthHex) {
        this.gateAddressLengthHex = gateAddressLengthHex;
    }

    public GateAddress copy() {
        try {
            return (GateAddress) this.clone();
        }catch (CloneNotSupportedException e) {
            logger.error(e.getMessage());
            return this;
        }
    }

    @Override
    public String toString() {
        return "GateAddress{" +
                "gateAddressOriginalHex='" + gateAddressOriginalHex + '\'' +
                ", gateAddressLengthHex='" + gateAddressLengthHex + '\'' +
                ", gateAddressLengthBin8Bits='" + gateAddressLengthBin8Bits + '\'' +
                ", retainBin='" + retainBin + '\'' +
                ", gateAddressLengthBin='" + gateAddressLengthBin + '\'' +
                ", gateAddressHex='" + gateAddressHex + '\'' +
                ", gateAddress='" + gateAddress + '\'' +
                '}';
    }

    public String buildInstanceByHexString(String hexString) {
        String gateAddressLengthHex = hexString.substring(0, 2);
        this.setGateAddressOriginalHex(gateAddressLengthHex);
        this.setGateAddressLengthHex(gateAddressLengthHex);
        String gateAddressLengthBin = ByteTools.hexStr2BinStr(gateAddressLengthHex);
        String gateAddressLengthBin8Bit = StringUtils.leftPad(gateAddressLengthBin, 8, '0');
        this.setGateAddressLengthBin8Bits(gateAddressLengthBin8Bit);
        String gateAddressLengthBinStr = gateAddressLengthBin8Bit.substring(3);
        this.setGateAddressLengthBin(gateAddressLengthBinStr);
        int gateAddressByteCount = Integer.parseInt(gateAddressLengthBinStr, 2);
        if(gateAddressByteCount != 0) {
            String gateAddressHex = hexString.substring(2, 2 + gateAddressByteCount * 2);
            this.setGateAddressHex(gateAddressHex);
            this.setGateAddressOriginalHex(gateAddressLengthHex + gateAddressHex);
            String gateAddressStr = String.valueOf(Integer.valueOf(gateAddressHex.substring(6, 8), 16)) + "." +
                    Integer.valueOf(gateAddressHex.substring(4, 6), 16) + "." +
                    Integer.valueOf(gateAddressHex.substring(2, 4), 16) + "." +
                    Integer.valueOf(gateAddressHex.substring(0, 2), 16);
            this.setGateAddress(gateAddressStr);
            hexString = hexString.substring(2 + gateAddressByteCount * 2);
        }else {
            hexString = hexString.substring(2);
        }
        return hexString;
    }
}
