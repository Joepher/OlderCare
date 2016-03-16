package com.mapfinger.joepher.oldercare.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mapfinger.joepher.oldercare.R;
import com.mapfinger.joepher.oldercare.entity.Config;
import com.mapfinger.joepher.oldercare.entity.User;

/**
 * Created by Joepher on 2015-12-13.
 */
public class UserInfoActivity extends Activity {
	private TextView tv_info_id,tv_info_email,tv_info_pwd;
	private Button btn_save,btn_cancle,btn_logout;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info);

		initEnv();
	}

	private void initEnv(){
		tv_info_id= (TextView) findViewById(R.id.tv_info_id);
		tv_info_email= (TextView) findViewById(R.id.tv_info_email);
		tv_info_pwd = (TextView) findViewById(R.id.tv_info_pwd);
		btn_save = (Button) findViewById(R.id.btn_save);
		btn_cancle= (Button) findViewById(R.id.btn_cancle);
		btn_logout = (Button) findViewById(R.id.btn_logout);

		user = Config.getInstance().getUserData();
		user.setEmail(getPasswordFromServer());

		tv_info_id.setText(user.getUserid());
		tv_info_email.setText(user.getEmail());
		tv_info_pwd.setText(user.getPassword());

		btn_save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String newPassword = tv_info_pwd.getText().toString().trim();
				if (!"".equals(newPassword) && newPassword != null) {
					if (savePassword(newPassword)) {
						Toast.makeText(UserInfoActivity.this, "密码修改成功", Toast.LENGTH_SHORT).show();

						Intent intent = new Intent(UserInfoActivity.this, MenuActivity.class);
						startActivity(intent);
					} else {
						Toast.makeText(UserInfoActivity.this, "密码修改失败", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(UserInfoActivity.this, "密码不合法", Toast.LENGTH_SHORT).show();
				}

			}
		});

		btn_cancle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(UserInfoActivity.this, MenuActivity.class);
				startActivity(intent);
			}
		});

		btn_logout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(UserInfoActivity.this, LoginActivity.class);
				startActivity(intent);
			}
		});
	}

	private boolean savePassword(String password) {
		//TODO 修改密码
		user.setPassword(password);

		 return Config.getInstance().saveUserData(user);
	}

	private String getPasswordFromServer(){
		return "test@123.com";
	}
}
