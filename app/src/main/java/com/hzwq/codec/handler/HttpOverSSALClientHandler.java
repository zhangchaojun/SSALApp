package com.hzwq.codec.handler;

import com.hzwq.codec.client.CallbackListener;
import com.hzwq.codec.consts.SSALConst;
import com.hzwq.codec.enums.ProtocolEnum;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.util.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpOverSSALClientHandler extends SimpleChannelInboundHandler<FullHttpResponse> {
    private static final Logger logger = LoggerFactory.getLogger(HttpOverSSALClientHandler.class);
    private FullHttpRequest request;
    private CallbackListener<FullHttpResponse> responseCallbackListener;


    public HttpOverSSALClientHandler(FullHttpRequest request, CallbackListener<FullHttpResponse> responseCallbackListener) {
        this.request = request;
        this.responseCallbackListener = responseCallbackListener;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 为channel打一个应用层网络协议的标记，用于SSAL错误无法处理时返回错误信息用
        Attribute<String> protocolAttr = ctx.channel().attr(SSALConst.PROTOCOL_TAG_KEY);
        protocolAttr.setIfAbsent(ProtocolEnum.HTTP.name());
        String fullContent = request.method().name() + " " + request.uri() + " " + request.protocolVersion().text() + "\r\n"
                                    + "Host: " + request.headers().get(HttpHeaderNames.HOST) + "\r\n\r\n";
        logger.trace("fullContent ready to send:\n {}", fullContent);
        ctx.writeAndFlush(Unpooled.wrappedBuffer(fullContent.getBytes()));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpResponse response) throws Exception {
    	if(responseCallbackListener != null) responseCallbackListener.onMessage(response);
        ctx.close();
    }
}
