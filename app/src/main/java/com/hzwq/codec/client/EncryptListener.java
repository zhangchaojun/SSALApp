package com.hzwq.codec.client;


/**
 * 加/解密的回调接口
 * */
public interface EncryptListener {
    /**
     * 加密明文的16进制lud字符串
     * */
    String encrypt(String originalLudHex);


    /**
     * 解密明文的16进制lud字符串
     * */
    String decrypt(String encryptLudHex);
}
