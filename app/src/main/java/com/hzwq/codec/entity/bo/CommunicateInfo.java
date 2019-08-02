package com.hzwq.codec.entity.bo;

import com.hzwq.codec.enums.CommunicateChannelTypeOctEnum;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*6.4.7 通信信息CI
通信信息CI，标识终端的通信信息内容，包括通信信道类型和信道信息内容两部分。
其中，通信信息类型由1字节组成；信道信息为变长，由通信信道类型中的末位4bit定义，不超过15字节。*/
public class CommunicateInfo implements Cloneable {
    private static final Logger logger = LoggerFactory.getLogger(CommunicateInfo.class);
    private static CommunicateInfo instance;

    private String communicateInfoHex;
    /*a) 通信信道类型（bit4~bit7）*/
    private CommunicateChannelTypeOctEnum communicateChannelType;

    /*b) 信道信息长度（bit0~bit3）*/
    /*标识信道信息长型，取值范围0~15。*/
    private String channelInfoLengthHex;

    /*c) 信道信息内容（BYTE1~BYTEn）*/
    /*标识信道信息内容，不超过15字节。*/
    private String channelIinfoHex;

    public CommunicateInfo() {
    }

    public static CommunicateInfo giveMeOne() {
        if (instance == null) {
            instance = new CommunicateInfo();
            instance.setCommunicateChannelType(CommunicateChannelTypeOctEnum.NO_CHANNEL_INFO);
            instance.setCommunicateInfoHex(StringUtils.EMPTY);
        }
        return instance.copy();
    }

    public CommunicateChannelTypeOctEnum getCommunicateChannelType() {
        return communicateChannelType;
    }

    public void setCommunicateChannelType(CommunicateChannelTypeOctEnum communicateChannelType) {
        this.communicateChannelType = communicateChannelType;
    }

    public String getChannelInfoLengthHex() {
        return channelInfoLengthHex;
    }

    public void setChannelInfoLengthHex(String channelInfoLengthHex) {
        this.channelInfoLengthHex = channelInfoLengthHex;
    }

    public String getChannelIinfoHex() {
        return channelIinfoHex;
    }

    public void setChannelIinfoHex(String channelIinfoHex) {
        this.channelIinfoHex = channelIinfoHex;
    }

    public String getCommunicateInfoHex() {
        return communicateInfoHex;
    }

    public void setCommunicateInfoHex(String communicateInfoHex) {
        this.communicateInfoHex = communicateInfoHex;
    }

    public CommunicateInfo copy() {
        try {
            return (CommunicateInfo) this.clone();
        }catch (CloneNotSupportedException e) {
            logger.error(e.getMessage());
            return this;
        }
    }

    @Override
    public String toString() {
        return "CommunicateInfo{" +
                "communicateInfoHex='" + communicateInfoHex + '\'' +
                ", communicateChannelType=" + communicateChannelType +
                ", channelInfoLengthHex='" + channelInfoLengthHex + '\'' +
                ", channelIinfoHex='" + channelIinfoHex + '\'' +
                '}';
    }

    //            6.4.7 通信信息CI
//            通信信息CI，标识终端的通信信息内容，包括通信信道类型和信道信息内容两部分。
//            其中，通信信息类型由1字节组成；信道信息为变长，由通信信道类型中的末位4bit定义，不超过15字节。
    public String buildInstanceByHexString(String hexString) {
        String channelTypeHex = hexString.substring(0, 1);
        this.setCommunicateChannelType(CommunicateChannelTypeOctEnum.getByValue(channelTypeHex, 16));
        String channelInfoLengthHex = hexString.substring(1, 2);
        this.setChannelInfoLengthHex(channelInfoLengthHex);
        int channelInfoByteCount = Integer.parseInt(channelInfoLengthHex, 16);
        if (channelInfoByteCount > 0) {
            String channelInfoHex = hexString.substring(2, 2 + channelInfoByteCount * 2);
            this.setChannelIinfoHex(channelInfoHex);
            hexString = hexString.substring(2 + channelInfoByteCount * 2);
        }else {
            hexString = hexString.substring(2);
        }
        return hexString;
    }
}
