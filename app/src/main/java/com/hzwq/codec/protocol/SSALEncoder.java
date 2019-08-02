package com.hzwq.codec.protocol;

import com.hzwq.codec.client.EncryptListener;
import com.hzwq.codec.entity.SSALParser;
import com.hzwq.codec.entity.impl.DefaultSSALParser;
import com.hzwq.codec.entity.impl.SSALMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SSALEncoder extends MessageToMessageEncoder<ByteBuf> {
    private static final Logger logger = LoggerFactory.getLogger(SSALEncoder.class);
    private EncryptListener encryptListener;

    public SSALEncoder(EncryptListener encryptListener) {
        this.encryptListener = encryptListener;
    }

    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        SSALParser ssalParser = DefaultSSALParser.instance();
        List<SSALMessage> ssalMessageList = ssalParser.parseReverse(msg, ctx.channel(), encryptListener);
        logger.trace("ssalMessageList: {}", ssalMessageList);
        for (SSALMessage ssalMessage : ssalMessageList) {
            logger.trace("[ENCODE SUCCESS AND READY TO SEND]: {}", ssalMessage.toString());
            out.add(ssalParser.loadSSAL(ssalMessage));
        }
    }
}
