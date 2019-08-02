package com.hzwq.codec.client;

import com.hzwq.codec.client.model.BaseRequestParams;
import com.hzwq.codec.client.model.SSALServerProperties;
import com.sun.istack.internal.NotNull;
import io.netty.handler.codec.http.FullHttpResponse;


public interface SSALHTTPClient {

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
    void sendHttpRequest(
            @NotNull SSALServerProperties ssalServerProperties,
            @NotNull String uri,
            BaseRequestParams params,
            CallbackListener<FullHttpResponse> listener) throws Exception;
}
