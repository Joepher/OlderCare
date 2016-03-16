package com.mapfinger.joepher.oldercare.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by Administrator on 2016/1/10.
 */
public class User implements KvmSerializable {
    private String email;
    private String userid;
    private String password;

    public User() {
        this.email = null;
        this.userid = null;
        this.password = null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Object getProperty(int i) {
        if (i == 0) {
            return getEmail();
        } else if (i == 1) {
            return getUserid();
        } else if (i == 2) {
            return getPassword();
        } else {
            return null;
        }
    }

    @Override
    public int getPropertyCount() {
        return 3;
    }

    @Override
    public void setProperty(int i, Object o) {
        if (i == 0) {
            setEmail((String) o);
        } else if (i == 1) {
            setUserid((String) o);
        } else if (i == 2) {
            setPassword((String) o);
        } else {
            return;
        }
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        if (i == 0) {
            propertyInfo.name = "email";
        } else if (i == 1) {
            propertyInfo.name = "userid";
        } else if (i == 2) {
            propertyInfo.name = "password";
        } else {
            return;
        }

        propertyInfo.type = String.class;
    }
}
