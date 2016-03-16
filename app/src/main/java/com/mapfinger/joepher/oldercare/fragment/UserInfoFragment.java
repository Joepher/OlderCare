package com.mapfinger.joepher.oldercare.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mapfinger.joepher.oldercare.R;
import com.mapfinger.joepher.oldercare.entity.Config;
import com.mapfinger.joepher.oldercare.entity.User;
import com.mapfinger.joepher.oldercare.services.UserService;

/**
 * Created by Administrator on 2016/2/20.
 */
public class UserInfoFragment extends Fragment implements View.OnClickListener {
    private View mUserInfoFragment;
    private ImageView user_info_head;
    private TextView tv_user_info_id, tv_user_info_email, tv_user_info_pwd;
    private Button btn_user_info_save;

    private String uid = null, email = null, pwd = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mUserInfoFragment = inflater.inflate(R.layout.new_user_info, container, false);
        initUI();

        return mUserInfoFragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_info_head:
                Toast.makeText(mUserInfoFragment.getContext(), "暂不支持修改头像", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_user_info_save:
                uid = tv_user_info_id.getText().toString().trim();
                email = tv_user_info_email.getText().toString().trim();
                pwd = tv_user_info_pwd.getText().toString().trim();

                if (email == null || "".equals(email)) {
                    Toast.makeText(mUserInfoFragment.getContext(), "邮箱不能为空", Toast.LENGTH_SHORT).show();
                } else if (pwd == null || "".equals(pwd)) {
                    Toast.makeText(mUserInfoFragment.getContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            doUpdate();
                        }
                    }).start();
                }
                break;
        }
    }

    private void initUI() {
        final User user = Config.getInstance().getUserData();
        uid = user.getUserid();
        pwd = user.getPassword();

        user_info_head = (ImageView) mUserInfoFragment.findViewById(R.id.user_info_head);
        user_info_head.setClickable(true);
        user_info_head.setOnClickListener(this);

        tv_user_info_id = (TextView) mUserInfoFragment.findViewById(R.id.user_info_id);
        tv_user_info_email = (TextView) mUserInfoFragment.findViewById(R.id.user_info_email);
        tv_user_info_pwd = (TextView) mUserInfoFragment.findViewById(R.id.user_info_pwd);
        btn_user_info_save = (Button) mUserInfoFragment.findViewById(R.id.btn_user_info_save);

        tv_user_info_id.setText(uid);
        tv_user_info_pwd.setText(pwd);

        new Thread(new Runnable() {
            @Override
            public void run() {
                email = UserService.getInstance().info(user);
                if (email == null) {
                    handler.sendEmptyMessage(0);
                } else {
                    handler.sendEmptyMessage(1);
                }
            }
        }).start();

        btn_user_info_save.setOnClickListener(this);
    }

    private void doUpdate() {
        User user = new User();
        user.setUserid(uid);
        user.setPassword(pwd);
        user.setEmail(email);

        int status = UserService.getInstance().update(user);
        if (status==UserService.STATUS_OK){
            handler.sendEmptyMessage(2);
            Config.getInstance().saveUserData(user);
        }else if (status==UserService.STATUS_FAIL){
            handler.sendEmptyMessage(3);
        }else if (status==UserService.STATUS_SERVER_ERROR){
            handler.sendEmptyMessage(4);
        }

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                tv_user_info_email.setHint("获取失败");
            } else if (msg.what == 1) {
                tv_user_info_email.setText(email);
            }else if (msg.what==2){
                Toast.makeText(mUserInfoFragment.getContext(), "修改成功", Toast.LENGTH_SHORT).show();
            }else if (msg.what==3){
                Toast.makeText(mUserInfoFragment.getContext(),"修改失败",Toast.LENGTH_SHORT).show();
            }else if (msg.what==4){
                Toast.makeText(mUserInfoFragment.getContext(),"无法连接到服务器",Toast.LENGTH_SHORT).show();
            } else {
                return;
            }
        }
    };
}
