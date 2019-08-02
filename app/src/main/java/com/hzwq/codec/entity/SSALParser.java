package com.hzwq.codec.entity;

import com.hzwq.codec.client.EncryptListener;
import com.hzwq.codec.entity.impl.SSALMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.util.List;

public interface SSALParser {

    /**
     * [Decode使用]
    *把SSAL格式[HEXString]的网络报文解析成一份普通的易操作的SSALMessage
     * @throws StringIndexOutOfBoundsException
    * */
    SSALMessage parse(String hexString, EncryptListener encryptListener) throws StringIndexOutOfBoundsException;

    /**
     * [配合Decode使用]
     * 把SSALMessage中的用户数据取出，转回为普通网络数据
     * */
    ByteBuf removeSSAL(SSALMessage ssalMessage);

    /**
     * [Encode使用]
     *把普通（http/ws/mqtt/...等基于tcp协议传输的）报文转化为一个易操作的SSALMessage集合
     * */
    List<SSALMessage> parseReverse(ByteBuf plainByteBuf, Channel channel, EncryptListener encryptListener);

    /**
     *  [配合Encode使用]
     * 把SSALMessage对象转化为SSAL格式的网络数据
     * */
    ByteBuf loadSSAL(SSALMessage ssalMessage);
}
