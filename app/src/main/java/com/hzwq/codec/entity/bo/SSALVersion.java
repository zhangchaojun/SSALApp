package com.hzwq.codec.entity.bo;

import com.hzwq.codec.config.SSALConfigInitProperties;
import com.hzwq.codec.enums.EncryptAlgorithmOctEnum;
import com.hzwq.codec.util.ByteTools;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*6.4.2 SSAL协议版本SV*/
/*SSAL协议版本SV，标识SSAL协议的版本信息并包含协议中采用的密码算法信息，由2字节组成。*/
    /*目前使用的加密算法为“01”CBC密文方式。终端的加密算法由网关在与终端进行会话密钥协商的过程中，在会话密钥协商请求报文中的该字段进行设定。
    该字段采用反序传输，例如：	SSAL协议版本：0x00，加密算法：01，SSAL报文中实际填写顺序为 01 00。*/
public class SSALVersion implements Cloneable {
    private static final Logger logger = LoggerFactory.getLogger(SSALVersion.class);
    private static SSALVersion instance;

    private String ssalVersionHex;

    /*1字节*/
    /*标识SSAL协议版本。其中bit8～bit11，表示小版本号，bit12～bit15标识大版本号。当前版本大版本号为1，小版本号为0*/
    private String versionHex;
    private String versionBigHex;
    private String versionSmallHex;

    /*保留位（bit4~bit7）*/
    private String retain = "0000";

    /*加密算法（bit0~bit3）*/
    /*标识应用报文传输加密采用的密码运算模式*/
    private EncryptAlgorithmOctEnum encryptAlgorithm;

    public SSALVersion() {
    }

//    用系统参数创建一个对象并返回
    private void initBySSALConfigProperties() {
        this.setVersionBigHex(SSALConfigInitProperties.ssalVersionBigHex);
        this.setVersionSmallHex(SSALConfigInitProperties.ssalVersionSmallHex);
        this.setVersionHex(this.getVersionBigHex() + this.getVersionSmallHex());
        this.setEncryptAlgorithm(SSALConfigInitProperties.encryptAlgorithm);
        String lower8bit = this.getRetain() + StringUtils.leftPad(ByteTools.int2HexStr(Integer.parseInt(SSALConfigInitProperties.encryptAlgorithm.getValue(), 10)), 4, '0');
        String lower8bitHex = ByteTools.int2HexStr(Integer.parseInt(lower8bit));
        String hight8bitHex = this.getVersionHex();
        this.setSsalVersionHex(lower8bitHex + hight8bitHex);
    }
    public static SSALVersion giveMeOne() {
        if (instance == null) {
            instance = new SSALVersion();
            instance.initBySSALConfigProperties();
        }
        return instance.copy();
    }

    //            6.4.2 SSAL协议版本SV
//            SSAL协议版本SV，标识SSAL协议的版本信息并包含协议中采用的密码算法信息，由2字节组成。
//            目前使用的加密算法为“01”CBC密文方式。终端的加密算法由网关在与终端进行会话密钥协商的过程中，在会话密钥协商请求报文中的该字段进行设定。
//            该字段采用反序传输，例如：	SSAL协议版本：0x00，加密算法：01，SSAL报文中实际填写顺序为 01 00。
    public SSALVersion(String ssalVersionHex) {
        this.setSsalVersionHex(ssalVersionHex);
        ssalVersionHex = ssalVersionHex.substring(2, 4) + ssalVersionHex.substring(0, 2);
        this.setEncryptAlgorithm(EncryptAlgorithmOctEnum.getByValue(ssalVersionHex.substring(3, 4), 16));
        this.setVersionHex(ssalVersionHex.substring(0, 2));
        this.setVersionBigHex(ssalVersionHex.substring(0, 1));
        this.setVersionSmallHex(ssalVersionHex.substring(1, 2));
    }

    public String getVersionBigHex() {
        return versionBigHex;
    }

    public void setVersionBigHex(String versionBigHex) {
        this.versionBigHex = versionBigHex;
    }

    public String getVersionSmallHex() {
        return versionSmallHex;
    }

    public void setVersionSmallHex(String versionSmallHex) {
        this.versionSmallHex = versionSmallHex;
    }

    public String getSsalVersionHex() {
        return ssalVersionHex;
    }

    public void setSsalVersionHex(String ssalVersionHex) {
        this.ssalVersionHex = ssalVersionHex;
    }

    public String getVersionHex() {
        return versionHex;
    }

    public void setVersionHex(String versionHex) {
        this.versionHex = versionHex;
    }

    public String getRetain() {
        return retain;
    }

    public void setRetain(String retain) {
        this.retain = retain;
    }

    public EncryptAlgorithmOctEnum getEncryptAlgorithm() {
        return encryptAlgorithm;
    }

    public SSALVersion copy() {
        try {
            return (SSALVersion) this.clone();
        }catch (CloneNotSupportedException e) {
            logger.error(e.getMessage());
            return this;
        }
    }

    public void setEncryptAlgorithm(EncryptAlgorithmOctEnum encryptAlgorithm) {
        this.encryptAlgorithm = encryptAlgorithm;
    }


    @Override
    public String toString() {
        return "SSALVersion{" +
                "ssalVersionHex='" + ssalVersionHex + '\'' +
                ", versionHex='" + versionHex + '\'' +
                ", versionBigHex='" + versionBigHex + '\'' +
                ", versionSmallHex='" + versionSmallHex + '\'' +
                ", retain='" + retain + '\'' +
                ", encryptAlgorithm=" + encryptAlgorithm +
                '}';
    }
}
