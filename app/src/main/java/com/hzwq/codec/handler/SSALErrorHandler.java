package com.hzwq.codec.handler;

import com.hzwq.codec.client.SSALErrorListener;
import com.hzwq.codec.consts.SSALConst;
import com.hzwq.codec.enums.ProtocolEnum;
import io.netty.util.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hzwq.codec.entity.impl.DefaultSSALParser;
import com.hzwq.codec.entity.impl.SSALMessage;
import com.hzwq.codec.enums.SSALErrorHexEnum;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class SSALErrorHandler extends SimpleChannelInboundHandler<SSALMessage> {
	private static final Logger logger = LoggerFactory.getLogger(SSALErrorHandler.class);
	private SSALErrorListener ssalErrorListener;


    public SSALErrorHandler(SSALErrorListener ssalErrorListener) {
        this.ssalErrorListener = ssalErrorListener;
    }

    @Override
	protected void channelRead0(ChannelHandlerContext ctx, SSALMessage ssalMessage) throws Exception {
		SSALErrorHexEnum backCode = ssalMessage.getLinkUserData().getBackCode();
		if(backCode == null || SSALErrorHexEnum.SUCCESS.equals(backCode)) {
			logger.trace("received {} msg.", backCode != null ?  "success downStream" : "upStream");
			ctx.fireChannelRead(DefaultSSALParser.instance().removeSSAL(ssalMessage));
		}else {
			logger.error("received error msg from iGate, errorCode: {}", backCode);
			this.handlerSSALError(ctx, ssalMessage);
		}
	}

	/**
	 * SSAL错误的处理方法，所有的安全网关返回的错误信息，在这里做出自动处理
	 * */
	private void handlerSSALError(ChannelHandlerContext ctx, SSALMessage ssalMessage) {
        Attribute<String> protocolAttr = ctx.channel().attr(SSALConst.PROTOCOL_TAG_KEY);
        ProtocolEnum bizProtocol = ProtocolEnum.valueOf(protocolAttr.get());
        SSALErrorHexEnum errorCode = ssalMessage.getLinkUserData().getBackCode();
		switch (errorCode) {
            case PSP_DECRYPTION_ERROR:
                ssalErrorListener.onPspDecryptionError(ssalMessage, ctx, bizProtocol);
                break;
            case PSP_CHECK_SIGN_FAILED:
                ssalErrorListener.onPspCheckSignFailed(ssalMessage, ctx, bizProtocol);
                break;
            case PSP_CHECK_MAC_FAILED:
                ssalErrorListener.onPspCheckMacFailed(ssalMessage, ctx, bizProtocol);
                break;
            case SESSION_COUNTER_ERROR:
                ssalErrorListener.onSessionCounterError(ssalMessage, ctx, bizProtocol);
                break;
            case GATE_DECRYPTION_ERROR:
                ssalErrorListener.onGateDecryptionError(ssalMessage, ctx, bizProtocol);
                break;
            case GATE_CHECK_SIGN_FAILED:
                ssalErrorListener.onGateCheckSignFailed(ssalMessage, ctx, bizProtocol);
                break;
            case GATE_CHECK_MAC_FAILED:
                ssalErrorListener.onGateCheckMacFailed(ssalMessage, ctx, bizProtocol);
                break;
            case GATE_KEY_UNIT_ISSUE:
                ssalErrorListener.onGateKeyUnitIssue(ssalMessage, ctx, bizProtocol);
                break;
            case LINK_EQUIPMENT_KEY_UNIT_ERROR:
                ssalErrorListener.onLinkEquipmentKeyUnitError(ssalMessage, ctx, bizProtocol);
                break;

            case PROTOCOL_VERSION_ERROR:
                ssalErrorListener.onProtocolVersionError(ssalMessage, ctx, bizProtocol);
                break;
            case ENCRYPTION_ALGORITHM_FLAG_MISMATCH:
                ssalErrorListener.onEncryptionAlgorithmFlagMismatch(ssalMessage, ctx, bizProtocol);
                break;
            case DEVICE_TYPE_NOT_FOUND:
                ssalErrorListener.onDeviceTypeNotFound(ssalMessage, ctx, bizProtocol);
                break;
            case CONTROL_CODE_NOT_FOUND:
                ssalErrorListener.onControlCodeNotFound(ssalMessage, ctx, bizProtocol);
                break;
            case STARTUP_DIRECTION_ERROR:
                ssalErrorListener.onStartupDirectionError(ssalMessage, ctx, bizProtocol);
                break;
            case LUD_LENGTH_ABNORMAL_SHORTER_THAN_4_BYTES:
                ssalErrorListener.onLudLengthAbnormalShorterThan4Bytes(ssalMessage, ctx, bizProtocol);
                break;
            case LUD_LENGTH_MISMATCH:
                ssalErrorListener.onLudLengthMismatch(ssalMessage, ctx, bizProtocol);
                break;

            case TARGET_ADDRESS_NOT_EXIST:
                ssalErrorListener.onTargetAddressNotExist(ssalMessage, ctx, bizProtocol);
                break;
            case COMMUNICATE_CHANNEL_NOT_CREATED:
                ssalErrorListener.onCommunicateChannelNotCreated(ssalMessage, ctx, bizProtocol);
                break;
            case MSG_SEND_ERROR:
                ssalErrorListener.onMsgSendError(ssalMessage, ctx, bizProtocol);
                break;
            case CHANNEL_ERROR:
                ssalErrorListener.onChannelError(ssalMessage, ctx, bizProtocol);
                break;
            case CHANNEL_KEY_NEGOTIATION_FAILED:
                ssalErrorListener.onChannelKeyNegotiationFailed(ssalMessage, ctx, bizProtocol);
                break;

                default:
                    ctx.fireChannelRead(DefaultSSALParser.instance().removeSSAL(ssalMessage));
		}
	}

}
