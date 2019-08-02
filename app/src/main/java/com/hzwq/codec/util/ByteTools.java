package com.hzwq.codec.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 作者：wgz on 2018/7/29 23:43
 * 邮箱：root@fiskz.com
 */
public class ByteTools {

    private static Logger logger = LoggerFactory.getLogger(ByteTools.class);

    /**
     * 16进制到字符串
     */
    public static String hexStr2Str(String hexStr)
    {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;

        for (int i = 0; i < bytes.length; i++)
        {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

    /**
     * hexString to BinaryString
     * */
    public static String hexStr2BinStr(String hexStr) {
        int anInt = Integer.parseInt(hexStr, 16);
        return Integer.toBinaryString(anInt);
    }

    /**
     * byte数组转换为二进制字符串,每个字节以","隔开
     * **/
    public static String byteArr2BinStr(byte [] b) {
        StringBuilder result = new StringBuilder();
        for (byte aB : b) {
            result.append(Long.toString(aB & 0xff, 2)).append(",");
        }
        return result.toString().substring(0, result.length()-1);
    }

    /**
     * byte数组转换为二进制字符串,每个字节以","隔开
     * **/
    public static String byte2BinStr(byte b) {
        String result = String.valueOf(Long.toString(b & 0xff, 2));
        return result.substring(0, result.length()-1);
    }

    /**
     * 二进制字符串转换为byte数组,每个字节以","隔开
     * **/
    public static byte[] binStr2Bytes(String bin2Str) {
        String [] temp = bin2Str.split(",");
        byte [] b = new byte[temp.length];
        for(int i = 0;i<b.length;i++)
        {
            b[i] = Long.valueOf(temp[i], 2).byteValue();
        }
        return b;
    }


    /**
     * byte数组转换为十六进制的字符串
     * **/
    public static String byteArr2HexStr(byte [] b) {
        StringBuilder result = new StringBuilder();
        for (byte aB : b) {
            if ((aB & 0xff) < 0x10)
                result.append("0");
            result.append(Long.toString(aB & 0xff, 16));
        }
        return result.toString().toUpperCase();
    }

    /**
     * 十六进制的字符串转换为byte数组
     * **/
    public static byte[] hexStr2ByteArr(String hex16Str) {
        if(hex16Str.length() == 1) {
            hex16Str = "0" + hex16Str;
        }
        if(hex16Str.trim().equals("")) {
            return new byte[0];
        }
        byte[] bytes = new byte[hex16Str.length() / 2];
        for(int i = 0; i < hex16Str.length() / 2; i++) {
            String subStr = hex16Str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }
        return bytes;
    }

    /**
     * int 转16进制字符串
     * @param value
     * @return
     */
    public static String int2HexStr(int value) {
        String hexInt = Integer.toHexString(value);
        if(hexInt.length() == 1) {
            hexInt = "0" + hexInt;
        }
        if(hexInt.length()%2 != 0) {
            hexInt = "0" + hexInt;
        }
        return hexInt;
    }

    /**
     * byte 数组与 int 的相互转换
     * @param bytes
     * @return
     */
    //
    public static int byteArr2Int(byte[] bytes) {
        return   bytes[3] & 0xFF |
                (bytes[2] & 0xFF) << 8 |
                (bytes[1] & 0xFF) << 16 |
                (bytes[0] & 0xFF) << 24;
    }

    /**
     * byte[]转int
     * @param bytes
     * @return
     */
    public static int byteArr2Int2(byte[] bytes) {
        int value=0;
        //由高位到低位
        for(int i = 0; i < 4; i++) {
            int shift= (4-1-i) * 8;
            value +=(bytes[i] & 0x000000FF) << shift;//往高位游
        }
        return value;
    }


    /**
     * int 与 byte 数组的相互转换
     * @param a
     * @return
     */
    public static byte[] int2ByteArr(int a) {
        return new byte[] {
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }

    /**
     * 合并字节数组
     * @param bytes1 数组1
     * @param bytes2 数组2
     * @return 数组3
     */
    public static byte[] byteMerger(byte[] bytes1, byte[] bytes2) {
        if (bytes1 == null || bytes1.length == 0) {
            return bytes2;
        }else {
            byte[] bytes0 = new byte[bytes1.length + bytes2.length];
            System.arraycopy(bytes1, 0, bytes0, 0, bytes1.length);
            System.arraycopy(bytes2, 0, bytes0, bytes1.length, bytes2.length);
            return bytes0;
        }
    }

    /**
     * 按照指定长度把字符串差分成数组
     * @param str 差分字符串
     * @param length0 指定长度
     * */
    public static String[] splitStrForSpecificLength(String str, int length0) {
        logger.trace("bizContentLength: {}", length0);
        int mod = str.length() % length0;
        int size = str.length() / length0 + (mod == 0 ? 0 : 1);
        String[] strings = new String[size];
        int cut = length0;
        for (int i = 0; i < size; i++) {
            if (i + 1 == size && mod != 0) cut = mod;
            strings[i] = str.substring(0, cut);
            if (i + 1 != size) str = str.substring(length0, str.length());
        }
        return strings;
    }

}
