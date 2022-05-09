package com.wms.mwt.e_wejob.utility.entity;

public class Company {

    public int CompanyID;
    public String CName;
    public Long Tel;
    public String Login;
    public String Password;

    public static final String COMPANY_ID="CompanyID";
    public static final String C_NAME="CName";
    public static final String TEL="Tel";
    public static final String LOGIN="Login";
    public static final String PASSWORD="Password";

    public static final String COMPANY="Company";

    public Company(int companyID, String CName, Long tel, String login, String password) {
        CompanyID = companyID;
        this.CName = CName;
        Tel = tel;
        Login = login;
        Password = password;
    }
}
