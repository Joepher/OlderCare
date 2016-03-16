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

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
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
import com.mapfinger.joepher.oldercare.entity.msg.RealMsg;
import com.mapfinger.joepher.oldercare.services.MessageService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/20.
 */
public class RealInfoFragment extends Fragment {
    private View mRealInfoFragment;
    private TextView tv_realInfo_content;
    private MapView mBmapView;
    private BaiduMap mBmap;
    private RealMsg realMsg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SDKInitializer.initialize(getActivity().getApplicationContext());
        mRealInfoFragment = inflater.inflate(R.layout.new_real_info, container, false);

        initUI();
        new Thread(new Runnable() {
            @Override
            public void run() {
                realMsg = new RealMsg();
                realMsg = MessageService.getInstance().getRealMsg(Config.getInstance().getUserData());
                initMap();
            }
        }).start();

        return mRealInfoFragment;
    }

    private void initUI() {
        tv_realInfo_content = (TextView) mRealInfoFragment.findViewById(R.id.tv_real_info_content);

        mBmapView = (MapView) mRealInfoFragment.findViewById(R.id.bmapView_real_info);
        mBmapView.showScaleControl(false);
        mBmapView.showZoomControls(false);
    }

    private void initMap() {
        handler.sendEmptyMessage(0);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (realMsg != null) {
                tv_realInfo_content.setText(getRealInfoContent(realMsg));

                mBmap = mBmapView.getMap();
                mBmap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                mBmap.setMapStatus(getMapStatusUpdate(realMsg));

                mBmap.addOverlay(getCOverlayOptions(realMsg));
                mBmap.addOverlay(getSOverlayOptions(realMsg));
                mBmap.addOverlay(getEOverlayoptions(realMsg));

                List<OverlayOptions> path = getPathOverlayOptions(realMsg);
                for (int i = 0; i < path.size(); ++i) {
                    mBmap.addOverlay(path.get(i));
                }
            } else {
                Toast.makeText(mRealInfoFragment.getContext(), "暂无任何数据", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private String getRealInfoContent(RealMsg realMsg) {
        String content = realMsg.getTime() + " 目标正离开起点，";
        if (realMsg.getRatio() != null) {
            content += (realMsg.getRatio() + "可能性下");
        }
        content += ("于" + realMsg.getStime() + "至" + realMsg.getEtime() + "到达目的地");

        return content;
    }

    private MapStatusUpdate getMapStatusUpdate(RealMsg realMsg) {
        double lat = Double.parseDouble(realMsg.getCcoord().getLatitude());
        double lon = Double.parseDouble(realMsg.getCcoord().getLongitude());
        LatLng point = new LatLng(lat, lon);

        MapStatus mapStatus = new MapStatus.Builder().target(point).zoom(18).build();
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);

        return mapStatusUpdate;
    }

    private OverlayOptions getCOverlayOptions(RealMsg realMsg) {
        return getOverlayOptions(realMsg.getCcoord(), R.drawable.icon_geo);
    }

    private OverlayOptions getSOverlayOptions(RealMsg realMsg) {
        return getOverlayOptions(realMsg.getScoord(), R.drawable.icon_st);
    }

    private OverlayOptions getEOverlayoptions(RealMsg realMsg) {
        return getOverlayOptions(realMsg.getEcoord(), R.drawable.icon_en);
    }

    private List<OverlayOptions> getPathOverlayOptions(RealMsg realMsg) {
        List<OverlayOptions> path = new ArrayList<>();

        List<Coord> coords = realMsg.getPath();
        for (int i = 0; i < coords.size(); ++i) {
            path.add(getOverlayOptions(coords.get(i), R.drawable.icon_gcoding));
        }

        return path;
    }

    private OverlayOptions getOverlayOptions(Coord coord, int id) {
        double lat = Double.parseDouble(coord.getLatitude());
        double lon = Double.parseDouble(coord.getLongitude());
        LatLng point = new LatLng(lat, lon);

        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(id);
        OverlayOptions overlayOptions = new MarkerOptions().position(point).icon(bitmap);

        return overlayOptions;
    }
}
