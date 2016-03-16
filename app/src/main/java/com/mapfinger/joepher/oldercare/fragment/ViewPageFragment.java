package com.mapfinger.joepher.oldercare.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mapfinger.joepher.oldercare.R;

import java.util.ArrayList;
/**
 * Created by Joepher on 2015-12-09.
 */
public class ViewPageFragment extends Fragment {
	private MyAdapter mAdapter;
	private ViewPager mPager;
	private ArrayList<Fragment> pagerItemList = new ArrayList<>();
	private MyPageChangeListener myPageChangeListener;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View mView = inflater.inflate(R.layout.view_pager, null);
		mPager = (ViewPager) mView.findViewById(R.id.pager);

		PageFragment page = new PageFragment();
		pagerItemList.add(page);

		mAdapter = new MyAdapter(getFragmentManager());
		mPager.setAdapter(mAdapter);
		mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				if (myPageChangeListener != null) {
					myPageChangeListener.onPageSelected(position);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {}

			@Override
			public void onPageScrollStateChanged(int position) {}
		});

		return mView;
	}

	public boolean isFirst() {
		return (mPager.getCurrentItem() == 0);
	}

	public boolean isEnd() {
		return  (mPager.getCurrentItem() == pagerItemList.size() - 1);
	}

	public interface MyPageChangeListener {
		void onPageSelected(int position);
	}

	public class MyAdapter extends FragmentPagerAdapter {
		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return pagerItemList.size();
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment;
			if (position < pagerItemList.size())
				fragment = pagerItemList.get(position);
			else
				fragment = pagerItemList.get(0);

			return fragment;
		}
	}


	public void setMyPageChangeListener(MyPageChangeListener l) {
		myPageChangeListener = l;
	}
}
