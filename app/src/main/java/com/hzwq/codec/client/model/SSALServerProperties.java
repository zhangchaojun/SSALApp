package com.hzwq.codec.client.model;

import com.hzwq.codec.config.SSALConfigInitProperties;
import org.apache.commons.lang.StringUtils;

import java.util.regex.Pattern;

public class SSALServerProperties {

    private static final String PSP_MAC_ADDR_ILLEGAL_MSG = "[pspMacAddress] is empty or too long than 15 characters, please check and modify your param.";
    private static final String POST_PROXY_IP_ILLEGAL_MSG = "[postProxyIp] is empty or not a valid IPV4 address, please check and modify your param.";
    private static final String I_GATE_IP_ILLEGAL_MSG = "[iGateIp] is not a valid IPV4 address, please check and modify your param.";
    private static final String LOCAL_IP_ILLEGAL_MSG = "[localIp] is empty or not a valid IPV4 address, please check and modify your param.";

    private String pspMacAddress;
    private String iGateIp;
    private Integer iGatePort = 80;
    private String postProxyIp;
    private Integer postProxyPort = 80;
    private String localIp;
    private Integer localPort = 80;
    private boolean encryptEnabled = false;

    /**
     * @param pspMacAddress                           客户端设备序列号，不超过15个字符, 不允许为空
     * @throws IllegalArgumentException           当参数pspMacAddress为空或长度超过15个字符会抛出非法参数异常
     * @param iGateIp                                        I型网关ip地址, 允许为空, 为空则视为没有I型网关, 将对后置代理服务发起请求
     * @throws IllegalArgumentException          不为空且不是有效ipv4地址将抛出非法参数异常
     * @param iGatePort                                     iGateIp不为空且不是有效ipv4地址将抛出非法参数异常
     * @throws IllegalArgumentException            iGatePort不为空且不是有效端口将抛出非法参数异常
     * @param postProxyIp                                 后置代理服务ip地址, 不允许为空
     * @throws IllegalArgumentException           当参数postProxyIp为空或不是有效ipv4地址会抛出非法参数异常
     * @param postProxyPort                             后置代理服务端口, 允许为空, 为空则取80
     * @throws IllegalArgumentException            postProxyPort不为空且不是有效端口将抛出非法参数异常
     * @param localIp                                         本地ip，及客户端ip地址, 不允许为空
     * @throws IllegalArgumentException           localIp为空或不是有效ipv4地址会抛出非法参数异常
     * @param localPort                                     本地端口，及客户端端口, 不允许为空
     * @throws IllegalArgumentException            localPort不为空且不是有效端口将抛出非法参数异常
     * */
    public SSALServerProperties(String pspMacAddress, String iGateIp, Integer iGatePort, String postProxyIp, Integer postProxyPort, String localIp, Integer localPort, boolean encryptEnabled) {
        paramValidate(pspMacAddress, iGateIp, iGatePort, postProxyIp, postProxyPort, localIp, localPort);
        this.pspMacAddress = pspMacAddress;
        this.iGateIp = iGateIp;
        if (iGatePort != null) this.iGatePort = iGatePort;
        this.postProxyIp = postProxyIp;
        if (postProxyPort != null) this.postProxyPort = postProxyPort;
        this.localIp = localIp;
        if (localPort != null) this.localPort = localPort;
        initSSALStaticProperties();
        this.encryptEnabled = encryptEnabled;
    }

    private void paramValidate(String pspMacAddress, String iGateIp, Integer iGatePort, String postProxyIp, Integer postProxyPort, String localIp, Integer localPort) {
        pspMacAddressValidate(pspMacAddress);
        iGateIpValidate(iGateIp);
        portValidate(iGatePort);
        postProxyIpValidate(postProxyIp);
        portValidate(postProxyPort);
        localIpValidate(localIp);
        portValidate(localPort);
    }

    private void localIpValidate(String localIp) {
        if (StringUtils.isEmpty(localIp) || !ipValidate(localIp)) throw new IllegalArgumentException("value [" + localIp + "] for param " + LOCAL_IP_ILLEGAL_MSG);
    }

    private void iGateIpValidate(String iGateIp) {
        if (!StringUtils.isEmpty(iGateIp) && !ipValidate(iGateIp)) throw new IllegalArgumentException("value [" + iGateIp + "] for param " + I_GATE_IP_ILLEGAL_MSG);
    }

    private void postProxyIpValidate(String postProxyIp) {
        if (StringUtils.isEmpty(postProxyIp) || !ipValidate(postProxyIp)) throw new IllegalArgumentException("value [" + postProxyIp + "] for param " + POST_PROXY_IP_ILLEGAL_MSG);
    }

    private static void pspMacAddressValidate(String pspMacAddress) {
        if (StringUtils.isEmpty(pspMacAddress) || pspMacAddress.length() > 15) throw new IllegalArgumentException("value [" + pspMacAddress + "] for param " + PSP_MAC_ADDR_ILLEGAL_MSG);
    }

    private static void portValidate(Integer port) {
        if (port != null && (port <= 0 || port > 65535)) throw new IllegalArgumentException("[" + port + "] is not a valid port, please check and ensure your port number between 0 ~ 65535");
    }

    private void initSSALStaticProperties() {
        SSALConfigInitProperties.pspMACAddress = pspMacAddress;

        SSALConfigInitProperties.gateIp = iGateIp;
        SSALConfigInitProperties.gatePort = iGatePort;

        SSALConfigInitProperties.targetIp = postProxyIp;
        SSALConfigInitProperties.targetPort = postProxyPort;

        SSALConfigInitProperties.localIp = localIp;
        SSALConfigInitProperties.localPort = localPort;

        SSALConfigInitProperties.encryptEnabled = encryptEnabled;

        if (StringUtils.isEmpty(SSALConfigInitProperties.gateIp)) {
            SSALConfigInitProperties.serverIp = postProxyIp;
            SSALConfigInitProperties.serverPort = postProxyPort;
        }else {
            SSALConfigInitProperties.serverIp = iGateIp;
            SSALConfigInitProperties.serverPort = iGatePort;
        }
    }


    private static boolean ipValidate(String ip) {
        final String ipRegex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        Pattern pattern = Pattern.compile(ipRegex);
        return pattern.matcher(ip).matches();
    }

    public boolean isEncryptEnabled() {
        return encryptEnabled;
    }

    public String getPspMacAddress() {
        return pspMacAddress;
    }

    public String getiGateIp() {
        return iGateIp;
    }

    public Integer getiGatePort() {
        return iGatePort;
    }

    public String getPostProxyIp() {
        return postProxyIp;
    }

    public Integer getPostProxyPort() {
        return postProxyPort;
    }

    public String getLocalIp() {
        return localIp;
    }

    public Integer getLocalPort() {
        return localPort;
    }

    @Override
    public String toString() {
        return "SSALServerProperties{" +
                "pspMacAddress='" + pspMacAddress + '\'' +
                ", iGateIp='" + iGateIp + '\'' +
                ", iGatePort=" + iGatePort +
                ", postProxyIp='" + postProxyIp + '\'' +
                ", postProxyPort=" + postProxyPort +
                ", localIp='" + localIp + '\'' +
                ", localPort=" + localPort +
                ", encryptEnabled=" + encryptEnabled +
                '}';
    }
}
