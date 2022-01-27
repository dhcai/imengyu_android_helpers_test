package com.imengyu.android_helpers;

import androidx.annotation.Keep;

import com.blankj.utilcode.util.NetworkUtils;
import com.taobao.weex.common.WXModule;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;

@Keep
public class NetworkModule extends WXModule {

    @Keep
    @UniJSMethod
    public boolean isConnected() {
        return NetworkUtils.isConnected();
    }


    @Keep
    @UniJSMethod
    public boolean is4G() {
        return NetworkUtils.is4G();
    }

    @Keep
    @UniJSMethod
    public boolean is5G() {
        return NetworkUtils.is5G();
    }

    @Keep
    @UniJSMethod
    public boolean isAvailable() {
        return NetworkUtils.isAvailable();
    }

    @Keep
    @UniJSMethod
    public boolean isAvailableByPing(String ip) {
        return NetworkUtils.isAvailableByPing(ip);
    }

    @Keep
    @UniJSMethod
    public boolean isAvailableByPing() {
        return NetworkUtils.isAvailableByPing();
    }
    @Keep

    @UniJSMethod
    public boolean isAvailableByDns(String dns) {
        return NetworkUtils.isAvailableByDns(dns);
    }

    @Keep
    @UniJSMethod
    public boolean isAvailableByDns() {
        return NetworkUtils.isAvailableByDns();
    }

    @Keep
    @UniJSMethod
    public boolean isBehindProxy() {
        return NetworkUtils.isBehindProxy();
    }

    @Keep
    @UniJSMethod
    public boolean isMobileData() {
        return NetworkUtils.isMobileData();
    }

    @Keep
    @UniJSMethod
    public boolean isUsingVPN() {
        return NetworkUtils.isUsingVPN();
    }

    @Keep
    @UniJSMethod
    public boolean isWifiAvailable() {
        return NetworkUtils.isWifiAvailable();
    }

    @Keep
    @UniJSMethod
    public boolean isWifiConnected() {
        return NetworkUtils.isWifiConnected();
    }

    @Keep
    @UniJSMethod
    public String getGatewayByWifi() {
        return NetworkUtils.getGatewayByWifi();
    }

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

    @Keep
    @UniJSMethod
    public String getIPAddress(boolean ipv4) {
        return NetworkUtils.getIPAddress(ipv4);
    }

    @Keep
    @UniJSMethod
    public String getBroadcastIpAddress() {
        return NetworkUtils.getBroadcastIpAddress();
    }

    @Keep
    @UniJSMethod
    public String getSSID() {
        return NetworkUtils.getSSID();
    }

    @Keep
    @UniJSMethod
    public boolean getWifiEnabled() {
        return NetworkUtils.getWifiEnabled();
    }

    @Keep
    @UniJSMethod
    public String getNetworkType() {
        NetworkUtils.NetworkType type = NetworkUtils.getNetworkType();
        switch (type) {
            case NETWORK_ETHERNET: return "NETWORK_ETHERNET";
            case NETWORK_WIFI: return "NETWORK_WIFI";
            case NETWORK_5G: return "NETWORK_5G";
            case NETWORK_4G: return "NETWORK_4G";
            case NETWORK_3G: return "NETWORK_3G";
            case NETWORK_2G: return "NETWORK_2G";
            default:
            case NETWORK_UNKNOWN: return "NETWORK_UNKNOWN";
            case NETWORK_NO: return "NETWORK_NO";
        }
    }
}

