package com.mapfinger.joepher.oldercare.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.mapfinger.joepher.oldercare.R;
import com.mapfinger.joepher.oldercare.entity.Config;
import com.mapfinger.joepher.oldercare.entity.Coord;
import com.mapfinger.joepher.oldercare.entity.msg.AlarmMsg;
import com.mapfinger.joepher.oldercare.services.MessageService;

import java.nio.channels.OverlappingFileLockException;

/**
 * Created by Administrator on 2016/2/20.
 */
public class AlarmInfoFragment extends Fragment {
    private View mAlarmInfoFragment;
    private TextView tv_alarmInfo_content;
    private MapView mBmapView;
    private BaiduMap mBmap;
    private AlarmMsg alarmMsg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SDKInitializer.initialize(getActivity().getApplicationContext());
        mAlarmInfoFragment = inflater.inflate(R.layout.new_alarm_info, container, false);

        initUI();
        new Thread(new Runnable() {
            @Override
            public void run() {
                alarmMsg = new AlarmMsg();
                alarmMsg = MessageService.getInstance().getAlarmMsg(Config.getInstance().getUserData());
                initMap();
            }
        }).start();

        return mAlarmInfoFragment;
    }

    private void initUI() {
        tv_alarmInfo_content = (TextView) mAlarmInfoFragment.findViewById(R.id.tv_alarm_info_content);

        mBmapView = (MapView) mAlarmInfoFragment.findViewById(R.id.bmapView_alarm_info);
        mBmapView.showScaleControl(false);
        mBmapView.showZoomControls(false);
    }

    private void initMap() {
        handler.sendEmptyMessage(0);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (alarmMsg != null) {
                tv_alarmInfo_content.setText(getAlarmInfoContent(alarmMsg));

                mBmap = mBmapView.getMap();
                mBmap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                mBmap.setMapStatus(getMapStatusUpdate(alarmMsg));
                mBmap.addOverlay(getOverlayOptions(alarmMsg));
            } else {
                tv_alarmInfo_content.setText("暂无预警消息");
                Toast.makeText(mAlarmInfoFragment.getContext(), "暂无预警消息", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private String getAlarmInfoContent(AlarmMsg alarmMsg) {
        String content = alarmMsg.getTime() + " 目标当前活动行为已偏离常规行为";

        return content;
    }

    private MapStatusUpdate getMapStatusUpdate(AlarmMsg alarmMsg) {
        double lat = Double.parseDouble(alarmMsg.getLatitude());
        double lon = Double.parseDouble(alarmMsg.getLongitude());
        LatLng point = new LatLng(lat, lon);

        MapStatus mapStatus = new MapStatus.Builder().target(point).zoom(18).build();
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);

        return mapStatusUpdate;
    }

    private OverlayOptions getOverlayOptions(AlarmMsg alarmMsg) {
        double lat = Double.parseDouble(alarmMsg.getLatitude());
        double lon = Double.parseDouble(alarmMsg.getLongitude());
        LatLng point = new LatLng(lat, lon);

        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);
        OverlayOptions overlayOptions = new MarkerOptions().position(point).icon(bitmap);

        return overlayOptions;
    }
}
