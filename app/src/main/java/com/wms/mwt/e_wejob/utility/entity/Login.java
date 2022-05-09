package com.wms.mwt.e_wejob.utility.entity;

public class Login {
    public String Role;
    public String Name;
    public String Success;
    public int Id;


    public static final String ROLE="Role";
    public static final String ID="ID";
    public static final String NAME="Name";
    public static final String SUCCESS="Success";

    public Login(String role, String name, String success, int ID) {
        Role = role;
        Name = name;
        Success = success;
        Id = ID;
    }
}
