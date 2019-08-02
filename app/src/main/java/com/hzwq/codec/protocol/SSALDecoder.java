package com.hzwq.codec.protocol;

import com.hzwq.codec.client.EncryptListener;
import com.hzwq.codec.consts.SSALConst;
import com.hzwq.codec.entity.SSALParser;
import com.hzwq.codec.entity.bo.SourceAddress;
import com.hzwq.codec.entity.dto.TmpMessageStore;
import com.hzwq.codec.entity.impl.DefaultSSALParser;
import com.hzwq.codec.entity.impl.SSALMessage;
import com.hzwq.codec.enums.DeviceTypeOctEnum;
import com.hzwq.codec.util.ByteTools;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SSALDecoder extends MessageToMessageDecoder<ByteBuf> {
    private static final Logger logger = LoggerFactory.getLogger(SSALDecoder.class);
    private EncryptListener encryptListener;

    /**
     * Create a new instance which will try to detect the types to match out of the type parameter of the class.
     */
    public SSALDecoder(EncryptListener encryptListener) {
        this.encryptListener = encryptListener;
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        byte[] bytesArr2Analyze = TmpMessageStore.getBytesArr2Analyze(ctx, msg);
        SSALParser ssalParser = DefaultSSALParser.instance();
        SSALMessage ssalMessage = null;
        try {
            ssalMessage = ssalParser.parse(ByteTools.byteArr2HexStr(bytesArr2Analyze), encryptListener);
        }catch (StringIndexOutOfBoundsException e) {
            logger.trace(e.getMessage());
            // ignore
        }
        if (ssalMessage != null && ssalMessage.decodeResult()) {
            logger.trace("[DECODE SUCCESS]: {}", ssalMessage.toString());
            markChannelTagAuto(ctx.channel(), ssalMessage);
            TmpMessageStore.rmByteMessage(ctx.channel().id());
            out.add(ssalMessage);
        }else {
            logger.trace("[DECODE FAILED]: ssal frame not complete yet!");
            TmpMessageStore.putByteMessage(ctx.channel().id(), bytesArr2Analyze);
        }
    }

//    下行的server自动将上行数据的sourceAddress信息标记到channel上，供下行的encoder使用
    private void markChannelTagAuto(Channel channel, SSALMessage ssalMessage) {
        SourceAddress sourceAddress = ssalMessage.getControlData().getSourceAddress();
        boolean iAmDownStreamServer = DeviceTypeOctEnum.PSP.equals(ssalMessage.getControlData().getDeviceAddressType().getDeviceType());
       if (iAmDownStreamServer) {
           if (sourceAddress == null) {
               throw new RuntimeException("[SSAL UPSTRAM] ERROR, NO SOURCE ADDRESS INFO!");
           }else {
               Attribute<String> pspIpAttr = channel.attr(SSALConst.PSP_IP_KEY);
               Attribute<String> pspPortAttr = channel.attr(SSALConst.PSP_PORT_KEY);
               pspIpAttr.setIfAbsent(sourceAddress.getSourceIp());
               pspPortAttr.setIfAbsent(String.valueOf(sourceAddress.getSourcePort()));
           }
       }

    }
}
