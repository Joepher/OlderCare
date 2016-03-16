package com.mapfinger.joepher.oldercare.entity;

import android.os.Environment;

import com.mapfinger.joepher.oldercare.entity.crypto.DES;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2016/1/10.
 */
public class Config {
    private static Config configInstance;

    private User user;
    private boolean isRegister;

    private static final String DIR_HOME = "/mapfinger/oldercare";
    private static final String FILE_LOGIN = "/userdata.properties";
    private static final String KEY_USERID = "userid";
    private static final String KEY_PASSWORD = "password";
    private static final String CRYPTOKEY = "12345678";

    private Config() {
        this.user = null;
        this.isRegister = false;
    }

    public static Config getInstance() {
        if (configInstance == null) {
            configInstance = new Config();
        }
        return configInstance;
    }

    public User getUserData() {
        try {
            if (user == null) {
                File file = new File(getUserDataPath());
                InputStream in = new FileInputStream(file);
                Properties prop = new Properties();
                prop.load(in);

                user = new User();
                user.setEmail(null);
                user.setUserid(prop.getProperty(KEY_USERID));
                user.setPassword(decrypt(prop.getProperty(KEY_PASSWORD)));
                in.close();
            }
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean saveUserData(User user) {
        try {
            this.user = user;
            File file = new File(getUserDataPath());
            OutputStream out = new FileOutputStream(file);

            Properties prop = new Properties();
            prop.setProperty(KEY_USERID, user.getUserid());
            prop.setProperty(KEY_PASSWORD, encrypt(user.getPassword()));
            prop.store(out, null);
            out.close();

            return true;
        } catch (Exception e) {
            MyLog.w("Save user login data failed.", e);
            return false;
        }
    }

    public void setRegisterSignal() {
        this.isRegister = true;
    }

    public boolean isRegister() {
        return isRegister;
    }

    private String getHomePath() {
        String home = Environment.getExternalStorageDirectory().getPath() + DIR_HOME;
        File file = new File(home);
        if (!file.exists()) {
            file.mkdirs();
        }

        return home;
    }

    private String getUserDataPath() {
        return getHomePath() + FILE_LOGIN;

    }

    private String encrypt(String password) {
        try {
            return DES.encryptDES(password, CRYPTOKEY);
        } catch (Exception e) {
            e.printStackTrace();
            MyLog.w("Encrypt password failed.", e);
            return null;
        }
    }

    private String decrypt(String result) {
        try {
            return DES.decryptDES(result, CRYPTOKEY);
        } catch (Exception e) {
            return null;
        }
    }

}
