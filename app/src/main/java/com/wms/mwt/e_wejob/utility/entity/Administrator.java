package com.wms.mwt.e_wejob.utility.entity;

public class Administrator {
    public String Login,Password;

    public static final String ADMIN="Admin";

    public Administrator(String login, String password) {
        Login = login;
        Password = password;
    }
}
