package com.hzwq.codec.entity.bo.lud;

import com.hzwq.codec.client.EncryptListener;
import com.hzwq.codec.entity.bo.MainLinkUserData;
import com.hzwq.codec.enums.FCCodeBinEnum;

//  e) 会话确认报文
//        报文的数据长度为数据域部分的实际长度，为可变长度；数据域部分采用长度+内容的方式组合表示
public class SessionNegotiationComplete extends MainLinkUserData {
    public String buildInstanceByHexString(String hexString, FCCodeBinEnum startUpSymbol, EncryptListener encryptListener) {
        hexString = this.initBackCodeByHexStringIfNeeded(hexString, startUpSymbol, encryptListener);
        // @TODO parse msg
        return hexString;
    }
}
