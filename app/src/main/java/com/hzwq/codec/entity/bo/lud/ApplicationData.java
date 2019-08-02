package com.hzwq.codec.entity.bo.lud;


import com.hzwq.codec.client.EncryptListener;
import com.hzwq.codec.entity.bo.MainLinkUserData;
import com.hzwq.codec.enums.FCCodeBinEnum;

//  /*主站与终端之间需要进行加密传输的业务报文。报文格式符合终端的业务报文协议要求。*/
//  f) 应用数据报文
//  应用报文是网关与终端完成会话密钥协商后，主站与终端之间的原有业务报文经过网关进行加解密处理的报文。
//  请求报文的数据长度字段根据实际情况填写，数据域部分为业务报文的密文值；
// 响应报文的数据长度字段和返回信息字段也是根据实际情况填写，数据域部分为业务报文的密文值。
public class ApplicationData extends MainLinkUserData {

    @Override
    public String buildInstanceByHexString(String hexString, FCCodeBinEnum startUpSymbol, EncryptListener encryptListener) {
        hexString = this.initBackCodeByHexStringIfNeeded(hexString, startUpSymbol, encryptListener);
        return hexString;
    }
}
