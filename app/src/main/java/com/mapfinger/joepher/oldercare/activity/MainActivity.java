package com.mapfinger.joepher.oldercare.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.mapfinger.joepher.oldercare.entity.Config;
import com.mapfinger.joepher.oldercare.entity.User;
import com.mapfinger.joepher.oldercare.services.UserService;

/**
 * Created by Administrator on 2016/1/7.
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginCheck();
    }

    private void loginCheck() {
        if (Config.getInstance().getUserData() != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    login();
                }
            }).start();
        } else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void login() {
        int status = UserService.getInstance().login(Config.getInstance().getUserData());
        if (status == UserService.STATUS_OK) {
            handler.sendEmptyMessage(0);
            Intent intent = new Intent(MainActivity.this, MainNewActivity.class);
            startActivity(intent);
        } else if (status == UserService.STATUS_FAIL) {
            handler.sendEmptyMessage(1);
        } else if (status == UserService.STATUS_SERVER_ERROR) {
            handler.sendEmptyMessage(2);
        } else {
            handler.sendEmptyMessage(3);
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 1) {
                Toast.makeText(MainActivity.this, "密码不正确.请重新输入", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 2) {
                Toast.makeText(MainActivity.this, "无法连接到服务器", Toast.LENGTH_SHORT).show();
            } else {
                return;
            }
        }
    };
}
