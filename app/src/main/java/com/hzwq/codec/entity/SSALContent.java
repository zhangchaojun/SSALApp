package com.hzwq.codec.entity;

public interface SSALContent {
    /**
     * SSAL报文的实现，必须能够在解码完成后，可以直接拿到解码成功与否的标志，true表示成功，false表示失败
     * */
    boolean decodeResult();
}
