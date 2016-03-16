package com.mapfinger.joepher.oldercare.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mapfinger.joepher.oldercare.R;
import com.mapfinger.joepher.oldercare.activity.UserInfoActivity;
import com.mapfinger.joepher.oldercare.entity.Config;

/**
 * Created by Joepher on 2015-12-09.
 */
public class LeftFragment extends Fragment {
	public View onCreateView(final LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.left, null);

		final TextView tv_id = (TextView) view.findViewById(R.id.tv_id);
		tv_id.setText(Config.getInstance().getUserData().getUserid());

		ImageView head = (ImageView) view.findViewById(R.id.head);
		head.setClickable(true);
		head.setFocusable(true);
		head.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(inflater.getContext(), UserInfoActivity.class);
				startActivity(intent);
			}
		});

		TextView tv_info = (TextView) view.findViewById(R.id.tv_info);
		tv_info.setClickable(true);
		tv_info.setFocusable(true);
		tv_info.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//TODO 获取用户实时行为决策结果
			}
		});

		TextView tv_alarm = (TextView) view.findViewById(R.id.tv_alarm);
		tv_alarm.setClickable(true);
		tv_alarm.setFocusable(true);
		tv_alarm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//TODO 获取用户实时行为预警结果
			}
		});

		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
}
