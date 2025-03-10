package com.apu.apstay.auth.models.inputmodels;

/**
 *
 * @author alexc
 */
public class LoginInputModel {
    private String loginKey;
    private String password;

    public String getLoginKey() {
        return loginKey;
    }

    public void setLoginKey(String loginKey) {
        this.loginKey = loginKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
