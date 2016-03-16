package com.mapfinger.joepher.oldercare.entity;

import android.util.Log;

/**
 * Created by Administrator on 2016/1/10.
 */
public class MyLog {
	private static final String MYTAG = "MapFinger";

	public static int d(String msg) {
		return Log.d(MYTAG, msg);
	}

	public static int e(String msg) {
		return Log.e(MYTAG, msg);
	}

	public static int i(String msg) {
		return Log.i(MYTAG, msg);
	}

	public static int v(String msg) {
		return Log.v(MYTAG, msg);
	}

	public static int w(String msg) {
		return Log.w(MYTAG, msg);
	}

	public static int w(String msg, Throwable t) {
		return Log.w(MYTAG, msg, t);
	}
}
