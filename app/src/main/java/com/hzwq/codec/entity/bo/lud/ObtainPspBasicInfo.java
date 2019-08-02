package com.hzwq.codec.entity.bo.lud;

import com.hzwq.codec.client.EncryptListener;
import com.hzwq.codec.entity.bo.MainLinkUserData;
import com.hzwq.codec.enums.FCCodeBinEnum;

//  /*网关获取终端基本信息的报文，包括请求报文和响应报文。*/
//  b) 获取终端基本信息报文
//        请求报文的数据长度为0，数据域为空。
//        响应报文的数据长度根据实际返回内容的长度填写，返回信息根据实际情况填写。
//        数据域部分采用长度+内容的方式组合表示获取到的终端基本信息，如果某个基本信息没有则将其长度设置为0即可。
public class ObtainPspBasicInfo extends MainLinkUserData {
    public String buildInstanceByHexString(String hexString, FCCodeBinEnum startUpSymbol, EncryptListener encryptListener) {
        hexString = this.initBackCodeByHexStringIfNeeded(hexString, startUpSymbol, encryptListener);
        // @TODO parse msg
        return hexString;
    }
}
