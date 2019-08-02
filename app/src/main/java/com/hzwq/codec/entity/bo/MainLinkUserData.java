package com.hzwq.codec.entity.bo;

import com.hzwq.codec.client.EncryptListener;
import com.hzwq.codec.config.SSALConfigInitProperties;
import com.hzwq.codec.enums.FCCodeBinEnum;
import com.hzwq.codec.enums.SSALErrorHexEnum;
import com.hzwq.codec.util.ByteTools;

/*6.6 链路用户数据LUD
链路用户数据LUD，定义了主站与终端之间、终端与网关之间的报文的具体内容。
链路用户数据包含一个完整的应用层协议数据单元（APDU）字节序列或APDU的分帧片段。
由多个字节组成，长度可变*/
public abstract class MainLinkUserData {

    protected String mainLinkUserDataHex;
    /*数据长度字段固定为2字节*/
    protected String ludLengthHexRevrs;
    protected String ludLengthHex;
    protected int ludLengthOct;

    protected String backCodeHexRevrs;
    protected String backCodeHex;
    protected int backCodeOct;
    protected SSALErrorHexEnum backCode;

    protected String ludHex;
    protected String ludPlain;

    public String getMainLinkUserDataHex() {
        return mainLinkUserDataHex;
    }

    public void setMainLinkUserDataHex(String mainLinkUserDataHex) {
        this.mainLinkUserDataHex = mainLinkUserDataHex;
    }

    public String getLudHex() {
        return ludHex;
    }

    public void setLudHex(String ludHex) {
        this.ludHex = ludHex;
    }

    public String getLudPlain() {
        return ludPlain;
    }

    public void setLudPlain(String ludPlain) {
        this.ludPlain = ludPlain;
    }

    public String getLudLengthHexRevrs() {
        return ludLengthHexRevrs;
    }

    public void setLudLengthHexRevrs(String ludLengthHexRevrs) {
        this.ludLengthHexRevrs = ludLengthHexRevrs;
    }

    public String getLudLengthHex() {
        return ludLengthHex;
    }

    public void setLudLengthHex(String ludLengthHex) {
        this.ludLengthHex = ludLengthHex;
    }

    public int getLudLengthOct() {
        return ludLengthOct;
    }

    public void setLudLengthOct(int ludLengthOct) {
        this.ludLengthOct = ludLengthOct;
    }

    public SSALErrorHexEnum getBackCode() {
        return backCode;
    }

    public void setBackCode(SSALErrorHexEnum backCode) {
        this.backCode = backCode;
    }

    public String getBackCodeHexRevrs() {
        return backCodeHexRevrs;
    }

    public void setBackCodeHexRevrs(String backCodeHexRevrs) {
        this.backCodeHexRevrs = backCodeHexRevrs;
    }

    public String getBackCodeHex() {
        return backCodeHex;
    }

    public void setBackCodeHex(String backCodeHex) {
        this.backCodeHex = backCodeHex;
    }

    public int getBackCodeOct() {
        return backCodeOct;
    }

    public void setBackCodeOct(int backCodeOct) {
        this.backCodeOct = backCodeOct;
    }

    @Override
    public String toString() {
        return "MainLinkUserData{" +
                "mainLinkUserDataHex='" + mainLinkUserDataHex + '\'' +
                ", ludLengthHexRevrs='" + ludLengthHexRevrs + '\'' +
                ", ludLengthHex='" + ludLengthHex + '\'' +
                ", ludLengthOct=" + ludLengthOct +
                ", backCodeHexRevrs='" + backCodeHexRevrs + '\'' +
                ", backCodeHex='" + backCodeHex + '\'' +
                ", backCodeOct=" + backCodeOct +
                ", backCode=" + backCode +
                ", ludHex='" + ludHex + '\'' +
                ", ludPlain='" + ludPlain + '\'' +
                '}';
    }

    protected String initBackCodeByHexStringIfNeeded(String hexString, FCCodeBinEnum startUpSymbol, EncryptListener encryptListener) {
        String ludLengthHexRevrs = hexString.substring(0, 4);
        String ludLenghtHex = ludLengthHexRevrs.substring(2, 4) + ludLengthHexRevrs.substring(0, 2);
        this.setLudLengthHex(ludLenghtHex);
        this.setLudLengthHexRevrs(ludLengthHexRevrs);
        int ludLengthOct = Integer.parseInt(ludLenghtHex, 16);
        this.setLudLengthOct(ludLengthOct);
        hexString = hexString.substring(4);
        if (FCCodeBinEnum.ZERO.equals(startUpSymbol)) {
            //从动站需要返回码
            String backCodeHexRevrs = hexString.substring(0, 4);
            this.setBackCodeHexRevrs(backCodeHexRevrs);
            String backCodeHex = backCodeHexRevrs.substring(2, 4) + backCodeHexRevrs.substring(0, 2);
            this.setBackCodeHex(backCodeHex);
            int backCodeOct = Integer.parseInt(backCodeHex, 16);
            this.setBackCodeOct(backCodeOct);
            this.setBackCode(SSALErrorHexEnum.getByValue(String.valueOf(backCodeOct), 10));
            hexString = hexString.substring(4);
        }

        String ludHex = hexString.substring(0, this.getLudLengthOct() * 2);
        if (SSALConfigInitProperties.encryptEnabled) ludHex = encryptListener.decrypt(ludHex);
        this.setLudHex(ludHex);
        this.setMainLinkUserDataHex(this.getLudLengthHex() + this.getLudHex());
        this.setLudPlain(ByteTools.hexStr2Str(ludHex));
        return hexString;
    }

    public abstract String buildInstanceByHexString(String hexString, FCCodeBinEnum startUpSymbol, EncryptListener encryptListener);
}
