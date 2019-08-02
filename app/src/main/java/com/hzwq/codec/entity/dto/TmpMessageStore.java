package com.hzwq.codec.entity.dto;

import com.hzwq.codec.consts.SSALConst;
import com.hzwq.codec.util.ByteTools;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class TmpMessageStore {
    private static HashMap<ChannelId, byte[]> unCompleteBytesMessage = new LinkedHashMap<ChannelId, byte[]>();

    public static void putByteMessage(ChannelId channelId, byte[] bytes) {
        unCompleteBytesMessage.put(channelId, bytes);
    }

    public static byte[] getByteMessage(ChannelId channelId) {
        return unCompleteBytesMessage.get(channelId);
    }

    public static void rmByteMessage(ChannelId channelId) {
        unCompleteBytesMessage.remove(channelId);
    }

    public static byte[] getBytesArr2Analyze(ChannelHandlerContext ctx, ByteBuf msg) {
        byte[] unCompleteByteArr = TmpMessageStore.getByteMessage(ctx.channel().id());
        byte[] newByteArr = new byte[msg.readableBytes()];
        msg.readBytes(newByteArr, 0, msg.readableBytes());
        byte[] bytes2Analysis;
        if (unCompleteByteArr != null && unCompleteByteArr.length > 0) {
            bytes2Analysis = ByteTools.byteMerger(unCompleteByteArr, newByteArr);
        }else {
            bytes2Analysis = newByteArr;
        }
        bytes2Analysis = ByteTools.byteMerger(bytes2Analysis, SSALConst.SSAL_FRAME_TAIL_BYTES);
        return bytes2Analysis;
    }
}
