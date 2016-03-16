package com.mapfinger.joepher.oldercare.activity;

import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.mapfinger.joepher.oldercare.R;
import com.mapfinger.joepher.oldercare.fragment.AlarmInfoFragment;
import com.mapfinger.joepher.oldercare.fragment.RealInfoFragment;
import com.mapfinger.joepher.oldercare.fragment.UserInfoFragment;

/**
 * Created by Administrator on 2016/2/20.
 */
public class MainNewActivity extends FragmentActivity implements View.OnClickListener{
    private TextView tv_real_info, tv_alarm_info, tv_user_info;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.new_main);
        initUI();
    }

    private void initUI() {
        tv_real_info = (TextView) findViewById(R.id.tv_real_info);
        tv_alarm_info = (TextView) findViewById(R.id.tv_alarm_info);
        tv_user_info = (TextView) findViewById(R.id.tv_user_info);

        tv_real_info.setClickable(true);
        tv_alarm_info.setClickable(true);
        tv_user_info.setClickable(true);

        tv_real_info.setOnClickListener(this);
        tv_alarm_info.setOnClickListener(this);
        tv_user_info.setOnClickListener(this);

        fragmentManager = getSupportFragmentManager();

        setDefaultFragment();
    }

    private void setDefaultFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragment = new RealInfoFragment();
        transaction.replace(R.id.content_frame,fragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        switch (v.getId()) {
            case R.id.tv_real_info:
                tv_real_info.setTextColor(Color.WHITE);
                tv_alarm_info.setTextColor(Color.BLACK);
                tv_user_info.setTextColor(Color.BLACK);
                fragment = new RealInfoFragment();
                transaction.replace(R.id.content_frame, fragment);
                break;
            case R.id.tv_alarm_info:
                tv_real_info.setTextColor(Color.BLACK);
                tv_alarm_info.setTextColor(Color.WHITE);
                tv_user_info.setTextColor(Color.BLACK);
                fragment = new AlarmInfoFragment();
                transaction.replace(R.id.content_frame, fragment);
                break;
            case R.id.tv_user_info:
                tv_real_info.setTextColor(Color.BLACK);
                tv_alarm_info.setTextColor(Color.BLACK);
                tv_user_info.setTextColor(Color.WHITE);
                fragment = new UserInfoFragment();
                transaction.replace(R.id.content_frame, fragment);
                break;
            default:
                break;
        }

        transaction.commit();
    }
}
