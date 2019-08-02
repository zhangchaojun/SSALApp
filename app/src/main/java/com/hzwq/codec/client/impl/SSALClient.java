package com.hzwq.codec.client.impl;

import com.hzwq.codec.client.CallbackListener;
import com.hzwq.codec.client.SSALHTTPClient;
import com.hzwq.codec.client.model.BaseRequestParams;
import com.hzwq.codec.client.model.SSALServerProperties;
import com.hzwq.codec.config.SSALConfigInitProperties;
import com.hzwq.codec.consts.SSALConst;
import com.hzwq.codec.handler.HttpOverSSALClientHandler;
import com.hzwq.codec.handler.SSALErrorHandler;
import com.hzwq.codec.protocol.SSALDecoder;
import com.hzwq.codec.protocol.SSALEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SSALClient implements SSALHTTPClient {
    private static final Logger logger = LoggerFactory.getLogger(SSALClient.class);

    /**
     * [客户端通过SSAL协议发起http请求的功能接口]
     *
     * @param ssalServerProperties               SSAL Server信息, 不允许为空
     * @throws IllegalArgumentException      当参数ssalServerProperties为空是会抛出非法参数异常
     *
     * @param uri                                           请求uri接口, 不允许为空
     * @throws IllegalArgumentException       当参数uri为空是会抛出非法参数异常
     *
     * @param params                                    参数, 允许为空, 表示无参
     * @param listener                                     对http响应的处理回调, 允许为空，表示不做处理
     */
    @Override
    public void sendHttpRequest(SSALServerProperties ssalServerProperties, String uri, BaseRequestParams params, CallbackListener<FullHttpResponse> listener) throws Exception {
        if (ssalServerProperties == null) throw new IllegalArgumentException("[ssalServerProperties] cannot be null!");
        if (StringUtils.isEmpty(uri)) throw new IllegalArgumentException("[uri] cannot be empty!");
        // init request
        String requestUri = params == null || StringUtils.isEmpty(params.toString()) ? uri : uri + "?" + params.toString();
        DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, requestUri);
        request.headers().set(HttpHeaderNames.HOST, SSALConfigInitProperties.serverIp);

        // config Nio eventLoopGroup for client
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            ByteBuf delimiter = Unpooled.copiedBuffer(SSALConst.SSAL_FRAME_TAIL_BYTES);
                            int maxSSALFrameLength = StringUtils.isEmpty(SSALConfigInitProperties.gateIp) ? SSALConst.MAX_LUD_BYTE_LENGTH_NO_GATE * 2 : SSALConst.MAX_LUD_BYTE_LENGTH * 2;
                            pipeline.addLast(new DelimiterBasedFrameDecoder(maxSSALFrameLength, delimiter));
                            pipeline.addLast("ssalEncoder", new SSALEncoder(null));
                            pipeline.addLast("ssalDecoder", new SSALDecoder(null));
                            pipeline.addLast("ssalErrorHandler", new SSALErrorHandler(null));
                            pipeline.addLast("http-chunked", new ChunkedWriteHandler());
                            pipeline.addLast("http-decoder", new HttpResponseDecoder());
                            pipeline.addLast("http-aggregate", new HttpObjectAggregator(8096));
                            pipeline.addLast(new HttpOverSSALClientHandler(request, listener));
                        }
                    });

            // lunch connect action
            ChannelFuture channelFuture = b.connect(SSALConfigInitProperties.serverIp, SSALConfigInitProperties.serverPort).sync();
            logger.info("connected to server: /{}:{}", SSALConfigInitProperties.serverIp, SSALConfigInitProperties.serverPort);
            // wait channel to close
            channelFuture.channel().closeFuture().sync();
        }finally {
            // gracefully shutdown
            group.shutdownGracefully();
        }
    }

}
