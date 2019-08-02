package com.hzwq.codec.client;


public interface CallbackListener<T> {
    /**
     * 客户端处理响应结果的回调接口
     * @param message 响应信息，根据不同协议，返回对应类型，比如http请求返回类型是FullHttpResponse
     * */
    void onMessage(T message);
}
