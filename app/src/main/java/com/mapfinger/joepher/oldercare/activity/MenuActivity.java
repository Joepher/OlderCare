package com.mapfinger.joepher.oldercare.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.mapfinger.joepher.oldercare.R;
import com.mapfinger.joepher.oldercare.fragment.LeftFragment;
import com.mapfinger.joepher.oldercare.fragment.ViewPageFragment;
import com.mapfinger.joepher.oldercare.view.SlidingMenu;


/**
 * Created by Joepher on 2015-12-09.
 */
public class MenuActivity extends FragmentActivity {
	SlidingMenu mSlidingMenu;
	LeftFragment leftFragment;
	ViewPageFragment viewPageFragment;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		init();
		initListener();
		Toast.makeText(MenuActivity.this,"logined",Toast.LENGTH_SHORT);
	}

	private void init() {
		mSlidingMenu = (SlidingMenu) findViewById(R.id.slidingMenu);
		mSlidingMenu.setLeftView(getLayoutInflater().inflate(R.layout.left_frame, null));
		mSlidingMenu.setCenterView(getLayoutInflater().inflate(R.layout.center_frame, null));

		FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
		leftFragment = new LeftFragment();
		t.replace(R.id.left_frame, leftFragment);

		viewPageFragment = new ViewPageFragment();
		t.replace(R.id.center_frame, viewPageFragment);
		t.commit();
	}

	private void initListener() {
		viewPageFragment.setMyPageChangeListener(new ViewPageFragment.MyPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				if (viewPageFragment.isFirst()) {
					mSlidingMenu.setCanSliding(true, false);
				} else if (viewPageFragment.isEnd()) {
					mSlidingMenu.setCanSliding(false, true);
				} else {
					mSlidingMenu.setCanSliding(false, false);
				}
			}
		});
	}
}
