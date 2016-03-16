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
public class RegisterActivity extends Activity implements View.OnClickListener {
    private EditText reg_email, reg_pwd, reg_rpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register);
        reg_email = (EditText) findViewById(R.id.et_reg_email);
        reg_pwd = (EditText) findViewById(R.id.et_reg_pwd);
        reg_rpwd = (EditText) findViewById(R.id.et_reg_rpwd);
        findViewById(R.id.btn_reg).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reg:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        doRegister();
                    }
                }).start();
                break;
        }
    }

    private void doRegister() {
        String email = reg_email.getText().toString().trim();
        String password = reg_pwd.getText().toString().trim();
        String repassword = reg_rpwd.getText().toString().trim();

        if ("".equals(email)) {
            handler.sendEmptyMessage(0);
        } else if ("".equals(password)) {
            handler.sendEmptyMessage(1);
        } else if ("".equals(repassword)) {
            handler.sendEmptyMessage(2);
        } else if (!password.equals(repassword)) {
            handler.sendEmptyMessage(3);
        } else {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            String userId = UserService.getInstance().register(user);
            if (userId != null) {
                user.setUserid(userId);
                if (Config.getInstance().saveUserData(user)) {
                    Config.getInstance().setRegisterSignal();
                    jumpToLogin();
                } else {
                    handler.sendEmptyMessage(4);
                }
            } else {
                handler.sendEmptyMessage(5);
            }
        }
    }

    private void jumpToLogin() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                Toast.makeText(RegisterActivity.this, "注册邮箱不能为空", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 1) {
                Toast.makeText(RegisterActivity.this, "注册密码不能为空", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 2) {
                Toast.makeText(RegisterActivity.this, "确认密码不能为空", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 3) {
                Toast.makeText(RegisterActivity.this, "两次密码不相同", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 4) {
                Toast.makeText(RegisterActivity.this, "注册失败,请稍候再试", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 5) {
                Toast.makeText(RegisterActivity.this, "无法连接到服务器.请稍后再试", Toast.LENGTH_SHORT).show();
            } else {
                return;
            }
        }
    };

}
