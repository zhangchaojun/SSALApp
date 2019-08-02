package com.hzwq.codec.entity.bo.lud;

import com.hzwq.codec.client.EncryptListener;
import com.hzwq.codec.entity.bo.MainLinkUserData;
import com.hzwq.codec.enums.FCCodeBinEnum;

//          附录C
//        （规范性附录）
//        I型网关指令集数据格式定义
//        终端/通信前置等可按照下面定义的查询命令格式组装报文，作为SSAL的数据域部分发给网关，网关能输出查询内容。
public class GateQuery extends MainLinkUserData {
    public String buildInstanceByHexString(String hexString, FCCodeBinEnum startUpSymbol, EncryptListener encryptListener) {
        hexString = this.initBackCodeByHexStringIfNeeded(hexString, startUpSymbol, encryptListener);
        // @TODO parse msg
        return hexString;
    }
}
