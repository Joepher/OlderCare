package com.mapfinger.joepher.oldercare.services;

import android.widget.Toast;

import com.mapfinger.joepher.oldercare.activity.MainNewActivity;
import com.mapfinger.joepher.oldercare.entity.Coord;
import com.mapfinger.joepher.oldercare.entity.MyLog;
import com.mapfinger.joepher.oldercare.entity.User;
import com.mapfinger.joepher.oldercare.entity.msg.AlarmMsg;
import com.mapfinger.joepher.oldercare.entity.msg.RealMsg;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2016/3/14.
 */
public class MessageService implements MyService {
    private SoapObject request, response;
    private HttpTransportSE service;
    private SoapSerializationEnvelope envelope;

    private static MessageService serviceInstance;

    private static final String MSG_SERVICE_URL = SERVICE_BASE_URL + "ActivityMessageImplPort?wsdl";
    private static final String MSG_SERVICE_METHOD_GET = "getRealMsg";
    private static final String MSG_SERVICE_METHOD_PUSH = "pushAlarmMsg";

    private MessageService() {
        service = new HttpTransportSE(MSG_SERVICE_URL);
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
    }

    public static MessageService getInstance() {
        if (serviceInstance == null) {
            synchronized (MessageService.class) {
                if (serviceInstance == null) {
                    serviceInstance = new MessageService();
                }
            }
        }
        return serviceInstance;
    }

    public RealMsg getRealMsg(User user) {
        RealMsg msg = null;
        if (user.getUserid() != null) {
            request = new SoapObject(SERVICE_NAMESPACE, MSG_SERVICE_METHOD_GET);
            request.addProperty("arg0", user);
            envelope.bodyOut = request;
        }
        try {
            service.call(null, envelope);
            if (envelope.getResponse() != null) {
                response = (SoapObject) envelope.bodyIn;
                SoapObject so = (SoapObject) response.getProperty(0);

                msg = new RealMsg();
                msg.setTime(so.getProperty("time").toString());
                msg.setStime(so.getProperty("stime").toString());
                msg.setEtime(so.getProperty("etime").toString());

                try {
                    msg.setRatio(so.getProperty("ratio").toString());
                }catch (Exception e){
                    //do nothing
                    MyLog.d("无ratio属性");
                }

                SoapObject so_ccoord = (SoapObject) so.getProperty("ccoord");
                Coord ccoord = new Coord();
                ccoord.setLatitude(so_ccoord.getProperty("latitude").toString());
                ccoord.setLongitude(so_ccoord.getProperty("longitude").toString());
                msg.setCcoord(ccoord);

                SoapObject so_scoord = (SoapObject) so.getProperty("scoord");
                Coord scoord = new Coord();
                scoord.setLatitude(so_scoord.getProperty("latitude").toString());
                scoord.setLongitude(so_scoord.getProperty("longitude").toString());
                msg.setScoord(scoord);

                SoapObject so_ecoord = (SoapObject) so.getProperty("ecoord");
                Coord ecoord = new Coord();
                ecoord.setLatitude(so_ecoord.getProperty("latitude").toString());
                ecoord.setLongitude(so_ecoord.getProperty("longitude").toString());
                msg.setEcoord(ecoord);

                List<Coord> paths = new ArrayList<>();
                SoapObject so_paths = (SoapObject) so.getProperty("path");
                for (int i = 0; i < so_paths.getPropertyCount(); ++i) {
                    SoapObject so_path = (SoapObject) so_paths.getProperty(i);
                    Coord pcoord = new Coord();
                    pcoord.setLatitude(so_path.getProperty("latitude").toString());
                    pcoord.setLongitude(so_path.getProperty("longitude").toString());
                    paths.add(pcoord);
                }
                msg.setPath(paths);
            }
        } catch (Exception e) {
            MyLog.e("获取用户实时行为失败.无法连接到服务器");
            e.printStackTrace();
        }
        return msg;
    }

    public AlarmMsg getAlarmMsg(User user) {
        AlarmMsg msg = null;
        if (user.getUserid() != null) {
            request = new SoapObject(SERVICE_NAMESPACE, MSG_SERVICE_METHOD_PUSH);
            request.addProperty("arg0", user);
            envelope.bodyOut = request;
        }
        try {
            service.call(null, envelope);
            if (envelope.getResponse() != null) {
                response = (SoapObject) envelope.bodyIn;
                SoapObject so = (SoapObject) response.getProperty(0);

                msg = new AlarmMsg();
                msg.setTime(so.getProperty("time").toString());
                msg.setLatitude(so.getProperty("latitude").toString());
                msg.setLongitude(so.getProperty("longitude").toString());
            }
        } catch (Exception e) {
            MyLog.e("接收预警消息失败.无法连接到服务器");
            e.printStackTrace();
        }
        return msg;
    }
}
