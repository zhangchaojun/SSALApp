package com.hzwq.codec.client;

import com.hzwq.codec.entity.impl.SSALMessage;
import com.hzwq.codec.enums.ProtocolEnum;
import io.netty.channel.ChannelHandlerContext;

/**
 * 接口功能: SSAL错误处理的回调，
 * 实现宗旨: 每一个对应的错误，尽可能做到静默处理，不影响业务接口的调用
 *                  如遇到错误无法处理，已经无法继续后续的业务操作，可以对业务操作返回错误信息<>T</>
 * */
public interface SSALErrorListener {

    /**
     *  PSP_DECRYPTION_ERROR("终端解密错误", "1001")
     */
    void onPspDecryptionError(SSALMessage ssalMessage, ChannelHandlerContext ctx, ProtocolEnum bizProtocol);


     /**
     PSP_CHECK_SIGN_FAILED("终端验签失败", "1002")
      */
     void onPspCheckSignFailed(SSALMessage ssalMessage, ChannelHandlerContext ctx, ProtocolEnum bizProtocol);

     /**
     PSP_CHECK_MAC_FAILED("终端MAC校验失败", "1003")
      */
     void onPspCheckMacFailed(SSALMessage ssalMessage, ChannelHandlerContext ctx, ProtocolEnum bizProtocol);

     /**
     SESSION_COUNTER_ERROR("会话计数器错误", "1004")
      */
      void onSessionCounterError(SSALMessage ssalMessage, ChannelHandlerContext ctx, ProtocolEnum bizProtocol);

      /**
     GATE_DECRYPTION_ERROR("网关解密错误", "1005")
       */
      void onGateDecryptionError(SSALMessage ssalMessage, ChannelHandlerContext ctx, ProtocolEnum bizProtocol);

    /**
     GATE_CHECK_SIGN_FAILED("网关验签失败", "1006")
     */
    void onGateCheckSignFailed(SSALMessage ssalMessage, ChannelHandlerContext ctx, ProtocolEnum bizProtocol);

    /**
     GATE_CHECK_MAC_FAILED("网关MAC校验失败", "1007")
     */
    void onGateCheckMacFailed(SSALMessage ssalMessage, ChannelHandlerContext ctx, ProtocolEnum bizProtocol);

    /**
     GATE_KEY_UNIT_ISSUE("网关密码单元故障", "1008")
     */
    void onGateKeyUnitIssue(SSALMessage ssalMessage, ChannelHandlerContext ctx, ProtocolEnum bizProtocol);

    /**
     LINK_EQUIPMENT_KEY_UNIT_ERROR("链路设备密码单元故障", "1009")
     */
    void onLinkEquipmentKeyUnitError(SSALMessage ssalMessage, ChannelHandlerContext ctx, ProtocolEnum bizProtocol);

    /**
     PROTOCOL_VERSION_ERROR("协议版本错误", "2001")
     */
    void onProtocolVersionError(SSALMessage ssalMessage, ChannelHandlerContext ctx, ProtocolEnum bizProtocol);

    /**
     ENCRYPTION_ALGORITHM_FLAG_MISMATCH("加解密算法标志不匹配", "2002")
     */
    void onEncryptionAlgorithmFlagMismatch(SSALMessage ssalMessage, ChannelHandlerContext ctx, ProtocolEnum bizProtocol);

    /**
     DEVICE_TYPE_NOT_FOUND("设备类型无法识别", "2003")
     */
    void onDeviceTypeNotFound(SSALMessage ssalMessage, ChannelHandlerContext ctx, ProtocolEnum bizProtocol);

    /**
     CONTROL_CODE_NOT_FOUND("控制码无法识别", "2004")
     */
    void onControlCodeNotFound(SSALMessage ssalMessage, ChannelHandlerContext ctx, ProtocolEnum bizProtocol);

    /**
     STARTUP_DIRECTION_ERROR("传输方向位错误", "2005")
     */
    void onStartupDirectionError(SSALMessage ssalMessage, ChannelHandlerContext ctx, ProtocolEnum bizProtocol);

    /**
     LUD_LENGTH_ABNORMAL_SHORTER_THAN_4_BYTES("数据域长度异常（小于4字节）", "2006")
     */
    void onLudLengthAbnormalShorterThan4Bytes(SSALMessage ssalMessage, ChannelHandlerContext ctx, ProtocolEnum bizProtocol);

    /**
     LUD_LENGTH_MISMATCH("数据域长度不匹配", "2007")
     */
    void onLudLengthMismatch(SSALMessage ssalMessage, ChannelHandlerContext ctx, ProtocolEnum bizProtocol);

    /**
     TARGET_ADDRESS_NOT_EXIST("目标节点不存在", "3001")
     */
    void onTargetAddressNotExist(SSALMessage ssalMessage, ChannelHandlerContext ctx, ProtocolEnum bizProtocol);

    /**
     COMMUNICATE_CHANNEL_NOT_CREATED("当前会话链路未建立", "3002")
     */
    void onCommunicateChannelNotCreated(SSALMessage ssalMessage, ChannelHandlerContext ctx, ProtocolEnum bizProtocol);

    /**
     MSG_SEND_ERROR("报文发送失败", "3003")
     */
    void onMsgSendError(SSALMessage ssalMessage, ChannelHandlerContext ctx, ProtocolEnum bizProtocol);

    /**
     CHANNEL_ERROR("信道错误", "3004")
     */
    void onChannelError(SSALMessage ssalMessage, ChannelHandlerContext ctx, ProtocolEnum bizProtocol);

    /**
     CHANNEL_KEY_NEGOTIATION_FAILED("当前链路会话协商失败", "3005")
     */
    void onChannelKeyNegotiationFailed(SSALMessage ssalMessage, ChannelHandlerContext ctx, ProtocolEnum bizProtocol);

}
