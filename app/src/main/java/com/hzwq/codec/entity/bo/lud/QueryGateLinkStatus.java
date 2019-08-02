package com.hzwq.codec.entity.bo.lud;

import com.hzwq.codec.client.EncryptListener;
import com.hzwq.codec.entity.bo.MainLinkUserData;
import com.hzwq.codec.enums.FCCodeBinEnum;

//  j) 查询网关链接状态报文
//        查询网关链接状态报文，是由内网客户机发出，查询所有与网关建立链接的客户机节点链接状态信息。
//        请求报文数据长度为0，数据域为空。
//        响应报文的数据长度根据实际返回内容的长度填写，返回信息根据实际情况填写。数据域部分采用长度+内容的方式组合。
//        实际组合方式为：len1+data1+ … +lenN+dataN。其中，每个data字段定义相同
public class QueryGateLinkStatus extends MainLinkUserData {
    public String buildInstanceByHexString(String hexString, FCCodeBinEnum startUpSymbol, EncryptListener encryptListener) {
        hexString = this.initBackCodeByHexStringIfNeeded(hexString, startUpSymbol, encryptListener);
        // @TODO parse msg
        return hexString;
    }
}
