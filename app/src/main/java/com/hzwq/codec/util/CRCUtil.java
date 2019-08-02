package com.hzwq.codec.util;

public class CRCUtil {
    public static String calculationHcs(String data) {
        byte [] hcs = CRC16.CRC16_ccitt(ByteTools.hexStr2ByteArr(data));
        return ByteTools.byteArr2HexStr(hcs);
    }

    public static String calculationFcs(String data) {
        return calculationHcs(data);
    }
}