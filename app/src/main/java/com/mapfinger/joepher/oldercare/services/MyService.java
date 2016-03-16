package com.mapfinger.joepher.oldercare.services;

/**
 * Created by Administrator on 2016/3/15.
 */
public interface MyService {
    String SERVICE_NAMESPACE = "http://impl.services.server.mapfinger.com/";
    String SERVICE_BASE_URL = "http://192.168.1.101:8080/MapFinger-Server/";

    int STATUS_INIT = 200;     //初始状态码
    int STATUS_OK = 201;       //登录校验成功
    int STATUS_FAIL = 202;     //登录校验失败
    int STATUS_SERVER_ERROR = 203;   //与服务器通信失败
}
