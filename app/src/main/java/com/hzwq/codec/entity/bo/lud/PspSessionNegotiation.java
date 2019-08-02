package com.hzwq.codec.entity.bo.lud;

import com.hzwq.codec.client.EncryptListener;
import com.hzwq.codec.entity.bo.MainLinkUserData;
import com.hzwq.codec.enums.FCCodeBinEnum;

//    网关与终端之间进行身份认证和会话密钥协商的报文，包括请求报文和响应报文。
//    具体协商报文定义与终端的类型相关，具体参见不同类型终端的协商机制描述。
//     c) 会话密钥协商报文
//   请求报文的数据长度为数据域部分的实际长度，为可变长度；数据域部分采用长度+内容的方式组合表示
public class PspSessionNegotiation extends MainLinkUserData {
    public String buildInstanceByHexString(String hexString, FCCodeBinEnum startUpSymbol, EncryptListener encryptListener) {
        hexString = this.initBackCodeByHexStringIfNeeded(hexString, startUpSymbol, encryptListener);
        // @TODO parse msg
        return hexString;
    }
}
