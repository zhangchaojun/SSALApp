package com.hzwq.codec.entity.bo.lud;

import com.hzwq.codec.client.EncryptListener;
import com.hzwq.codec.entity.bo.MainLinkUserData;
import com.hzwq.codec.enums.FCCodeBinEnum;

//i) 终端网关证书更新报文
//        保留，暂未定义。
public class PspGateCertUpdate extends MainLinkUserData {
    public String buildInstanceByHexString(String hexString, FCCodeBinEnum startUpSymbol, EncryptListener encryptListener) {
        hexString = this.initBackCodeByHexStringIfNeeded(hexString, startUpSymbol, encryptListener);
        // @TODO parse msg
        return hexString;
    }
}
