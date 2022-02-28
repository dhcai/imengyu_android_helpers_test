package com.imengyu.android_helpers;

import androidx.annotation.Keep;

import com.blankj.utilcode.util.NetworkUtils;
import com.taobao.weex.common.WXModule;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;

@Keep
public class NetworkModule extends WXModule {

    /**
     * 获取网络是否连接
     */
    @Keep
    @UniJSMethod
    public boolean isConnected() {
        return NetworkUtils.isConnected();
    }

    /**
     * 获取是否是4G网络
     */
    @Keep
    @UniJSMethod
    public boolean is4G() {
        return NetworkUtils.is4G();
    }
    /**
     * 获取是否是5G网络
     */
    @Keep
    @UniJSMethod
    public boolean is5G() {
        return NetworkUtils.is5G();
    }
    /**
     * 获取手机是否有网络功能
     */
    @Keep
    @UniJSMethod
    public boolean isAvailable() {
        return NetworkUtils.isAvailable();
    }
    /**
     * 使用PING获取网络是否可用
     * @param ip 指定要ping的默认地址
     */
    @Keep
    @UniJSMethod
    public boolean isAvailableByPing(String ip) {
        return NetworkUtils.isAvailableByPing(ip);
    }
    /**
     * 使用PING获取网络是否可用
     */
    @Keep
    @UniJSMethod
    public boolean isAvailableByPing() {
        return NetworkUtils.isAvailableByPing();
    }
    /**
     * 使用Dns查询获取网络是否可用
     * @param dns 指定要查询的DNS记录
     */
    @Keep
    @UniJSMethod
    public boolean isAvailableByDns(String dns) {
        return NetworkUtils.isAvailableByDns(dns);
    }
    /**
     * 使用Dns查询获取网络是否可用
     */
    @Keep
    @UniJSMethod
    public boolean isAvailableByDns() {
        return NetworkUtils.isAvailableByDns();
    }
    /**
     * 检查网络是否存在代理
     */
    @Keep
    @UniJSMethod
    public boolean isBehindProxy() {
        return NetworkUtils.isBehindProxy();
    }
    /**
     * 检查是否是移动数据网络
     */
    @Keep
    @UniJSMethod
    public boolean isMobileData() {
        return NetworkUtils.isMobileData();
    }
    /**
     * 检查是否是使用了VPN
     */
    @Keep
    @UniJSMethod
    public boolean isUsingVPN() {
        return NetworkUtils.isUsingVPN();
    }
    /**
     * 检查WIFI是否可用
     */
    @Keep
    @UniJSMethod
    public boolean isWifiAvailable() {
        return NetworkUtils.isWifiAvailable();
    }
    /**
     * 检查WIFI是否连接
     */
    @Keep
    @UniJSMethod
    public boolean isWifiConnected() {
        return NetworkUtils.isWifiConnected();
    }
    /**
     * 获取Wifi的网关地址
     */
    @Keep
    @UniJSMethod
    public String getGatewayByWifi() {
        return NetworkUtils.getGatewayByWifi();
    }
    /**
     * 获取Wifi的当前IP地址
     */
    @Keep
    @UniJSMethod
    public String getIpAddressByWifi() {
        return NetworkUtils.getIpAddressByWifi();
    }

    @Keep
    @UniJSMethod
    public String getServerAddressByWifi() {
        return NetworkUtils.getServerAddressByWifi();
    }

    @Keep
    @UniJSMethod
    public String getNetMaskByWifi() {
        return NetworkUtils.getNetMaskByWifi();
    }

    @Keep
    @UniJSMethod
    public String getNetworkOperatorName() {
        return NetworkUtils.getNetworkOperatorName();
    }

    /**
     * 获取当前IP地址
     * @param ipv4 是否获取IPV4地址
     */
    @Keep
    @UniJSMethod
    public String getIPAddress(boolean ipv4) {
        return NetworkUtils.getIPAddress(ipv4);
    }

    /**
     * 获取当前网络的广播地址
     */
    @Keep
    @UniJSMethod
    public String getBroadcastIpAddress() {
        return NetworkUtils.getBroadcastIpAddress();
    }

    /**
     * 获取已连接Wifi的SSID。
     */
    @Keep
    @UniJSMethod
    public String getSSID() {
        return NetworkUtils.getSSID();
    }

    /**
     * 获取Wifi是否启用
     */
    @Keep
    @UniJSMethod
    public boolean getWifiEnabled() {
        return NetworkUtils.getWifiEnabled();
    }

    /**
     * 获取网络类型
     * @return 有以下几种返回：
     * ETHERNET 有线网络连接
     * WIFI WIFI连接
     * 5G 5G网络连接
     * 4G 4G网络连接
     * 3G 3G网络连接
     * 2G 2G网络连接
     * UNKNOWN 未知连接
     * NO 无网络
     */
    @Keep
    @UniJSMethod
    public String getNetworkType() {
        NetworkUtils.NetworkType type = NetworkUtils.getNetworkType();
        switch (type) {
            case NETWORK_ETHERNET: return "ETHERNET";
            case NETWORK_WIFI: return "WIFI";
            case NETWORK_5G: return "5G";
            case NETWORK_4G: return "4G";
            case NETWORK_3G: return "3G";
            case NETWORK_2G: return "2G";
            default:
            case NETWORK_UNKNOWN: return "UNKNOWN";
            case NETWORK_NO: return "NO";
        }
    }
}

