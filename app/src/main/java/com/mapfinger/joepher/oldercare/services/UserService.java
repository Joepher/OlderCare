package com.mapfinger.joepher.oldercare.services;

import com.mapfinger.joepher.oldercare.entity.MyLog;
import com.mapfinger.joepher.oldercare.entity.User;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by Administrator on 2016/3/14.
 */
public class UserService implements MyService{
    private SoapObject request, response;
    private HttpTransportSE service;
    private SoapSerializationEnvelope envelope;

    private static UserService serviceInstance;

    private static final String USER_SERVICE_URL = SERVICE_BASE_URL + "UserInfoImplPort?wsdl";
    private static final String USER_SERVICE_METHOD_LOGIN = "login";
    private static final String USER_SERVICE_METHOD_REGISTER = "register";
    private static final String USER_SERVICE_METHOD_INFO = "info";
    private static final String USER_SERVICE_METHOD_UPDATE = "update";

    private UserService() {
        service = new HttpTransportSE(USER_SERVICE_URL);
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
    }

    public static UserService getInstance() {
        if (serviceInstance == null) {
            synchronized (UserService.class) {
                if (serviceInstance == null) {
                    serviceInstance = new UserService();
                }
            }
        }
        return serviceInstance;
    }

    public String register(User user) {
        String userid = null;
        if (user.getEmail() != null && user.getPassword() != null) {
            request = new SoapObject(SERVICE_NAMESPACE, USER_SERVICE_METHOD_REGISTER);
            request.addProperty("arg0", user);
            envelope.bodyOut = request;
            try {
                service.call(null, envelope);
                if (envelope.getResponse() != null) {
                    response = (SoapObject) envelope.bodyIn;
                    userid = response.getProperty(0).toString();
                }
            } catch (Exception e) {
                MyLog.e("注册失败:无法连接到服务器");
                e.printStackTrace();
            }
        }
        return userid;
    }

    public int login(User user) {
        int status = STATUS_INIT;
        if (user.getUserid() != null && user.getPassword() != null) {
            request = new SoapObject(SERVICE_NAMESPACE, USER_SERVICE_METHOD_LOGIN);
            request.addProperty("arg0", user);
            envelope.bodyOut = request;
            try {
                service.call(null, envelope);
                if (envelope.getResponse() != null) {
                    response = (SoapObject) envelope.bodyIn;
                    if (Boolean.parseBoolean(response.getProperty(0).toString())) {
                        status = STATUS_OK;
                    } else {
                        status = STATUS_FAIL;
                    }
                } else {
                    status = STATUS_SERVER_ERROR;
                }
            } catch (Exception e) {
                MyLog.e("用户验证失败:无法连接到服务器");
                status = STATUS_SERVER_ERROR;
            }
        }
        return status;
    }

    public int update(User user) {
        int status;
        if (user.getPassword() == null && user.getEmail() == null) {
            status = STATUS_INIT;
        } else {
            request=new SoapObject(SERVICE_NAMESPACE,USER_SERVICE_METHOD_UPDATE);
            request.addProperty("arg0",user);
            envelope.bodyOut=request;
            try{
                service.call(null,envelope);
                if (envelope.getResponse()!=null){
                    response= (SoapObject) envelope.bodyIn;
                    if (Boolean.parseBoolean(response.getProperty(0).toString())){
                        status=STATUS_OK;
                    }else {
                        status=STATUS_FAIL;
                    }
                }else {
                    status=STATUS_SERVER_ERROR;
                }
            }catch (Exception e){
                MyLog.e("更改用户信息失败:无法连接到服务器");
                status=STATUS_SERVER_ERROR;
            }
        }
        return status;
    }

    public String info(User user) {
        String email = null;
        if (user.getUserid() != null) {
            request = new SoapObject(SERVICE_NAMESPACE, USER_SERVICE_METHOD_INFO);
            request.addProperty("arg0", user);
            envelope.bodyOut = request;
        }
        try {
            service.call(null, envelope);
            if (envelope.getResponse() != null) {
                response = (SoapObject) envelope.bodyIn;
                email = response.getProperty(0).toString();
            }
        } catch (Exception e) {
            MyLog.e("获取用户信息失败:无法连接到服务器");
            e.printStackTrace();
        }
        return email;
    }
}
