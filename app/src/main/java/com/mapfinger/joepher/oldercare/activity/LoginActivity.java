package com.mapfinger.joepher.oldercare.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mapfinger.joepher.oldercare.R;
import com.mapfinger.joepher.oldercare.entity.Config;
import com.mapfinger.joepher.oldercare.entity.User;
import com.mapfinger.joepher.oldercare.services.UserService;

/**
 * Created by Joepher on 2015-12-10.
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    private EditText login_id, login_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        login_id = (EditText) findViewById(R.id.et_login_id);
        login_pwd = (EditText) findViewById(R.id.et_login_pwd);
        findViewById(R.id.btn_login_login).setOnClickListener(this);
        findViewById(R.id.btn_login_reg).setOnClickListener(this);

        if (Config.getInstance().isRegister()) {
            User user = Config.getInstance().getUserData();
            if (user != null) {
                login_id.setText(user.getUserid());
                Toast.makeText(LoginActivity.this, "注册成功。您的ID为" + user.getUserid(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_login:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        doLogin();
                    }
                }).start();
                break;
            case R.id.btn_login_reg:
                jumpToRegister();
                break;
        }
    }

    private void doLogin() {
        String userId = login_id.getText().toString().trim();
        String password = login_pwd.getText().toString().trim();
        if ("".equals(userId)) {
            handler.sendEmptyMessage(0);
        } else if ("".equals(password)) {
            handler.sendEmptyMessage(1);
        } else {
            User user = new User();
            user.setUserid(userId);
            user.setPassword(password);
            int status = UserService.getInstance().login(user);
            if (status == UserService.STATUS_OK) {
                Config.getInstance().saveUserData(user);
                login();
            } else if (status == UserService.STATUS_FAIL) {
                handler.sendEmptyMessage(2);
            } else if (status == UserService.STATUS_SERVER_ERROR) {
                handler.sendEmptyMessage(3);
            } else {
                handler.sendEmptyMessage(5);
            }
        }
    }

    private void login() {
        handler.sendEmptyMessage(4);
        Intent intent = new Intent(LoginActivity.this, MainNewActivity.class);
        startActivity(intent);
    }

    private void jumpToRegister() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                Toast.makeText(LoginActivity.this, "用户ID不能为空", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 1) {
                Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 2) {
                Toast.makeText(LoginActivity.this, "密码不正确", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 3) {
                Toast.makeText(LoginActivity.this, "无法连接到服务器", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 4) {
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
            } else {
                return;
            }
        }
    };

}
