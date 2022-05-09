package com.wms.mwt.e_wejob.utility.manage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.wms.mwt.e_wejob.utility.entity.Login;
import com.wms.mwt.e_wejob.view.AdminstratorActivity;
import com.wms.mwt.e_wejob.view.CandidateActivity;
import com.wms.mwt.e_wejob.view.CompanyActivity;
import com.wms.mwt.e_wejob.view.LoginActivity;
import com.wms.mwt.e_wejob.utility.entity.Administrator;
import com.wms.mwt.e_wejob.utility.entity.Candidate;
import com.wms.mwt.e_wejob.utility.entity.Company;


public class SessionManagement {
    private Context mContext;
    private SharedPreferences sharedPref;
    public static final String SHARED_PREF_NAME="e_we_job";
    private static final int PRIVATE_MODE=0;

    private static final String IS_LOGIN="login";
    SharedPreferences.Editor editor;

    private static SessionManagement sessionManager;

    private SessionManagement(Context context){
        this.mContext=context;
        sharedPref=mContext.getSharedPreferences(SHARED_PREF_NAME,PRIVATE_MODE);
        editor=sharedPref.edit();
    }

    public static SessionManagement getInstance(Context context){
        if(sessionManager==null)
            sessionManager=new SessionManagement(context);
        return sessionManager;
    }

    public void logout(Activity activity){
        //clear session
        editor.clear();
        editor.commit();

        StartLoginActivity(activity);
    }

    public String getName(){
        return sharedPref.getString(Login.NAME,"");
    }

    public int getID(){
        return sharedPref.getInt(Login.ID,0);
    }


    public void login(int id, String role,String name, Activity activity){
        editor.putBoolean(IS_LOGIN,true);
        editor.putInt(Login.ID,id);
        editor.putString(Login.ROLE,role);
        editor.putString(Login.NAME,name);

        editor.commit();

        StartHomeActivity(activity);
    }

    private void StartLoginActivity(Activity activity){
        Intent i=new Intent(mContext, LoginActivity.class);

        //closing all activities
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //start new activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mContext.startActivity(i);
        activity.finish();
    }

    public String getRole(){
        return sharedPref.getString(Login.ROLE,"");
    }

    private void StartHomeActivity(Activity activity){

        Intent i=new Intent();
        switch (getRole()){
            case Administrator.ADMIN:{
                i=new Intent(mContext, AdminstratorActivity.class);
                break;
            }
            case Company.COMPANY:{
                i=new Intent(mContext, CompanyActivity.class);
                break;
            }
            case Candidate.CANDIDATE:{
                i=new Intent(mContext, CandidateActivity.class);
                break;
            }

        }

        //closing all activities
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //start new activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mContext.startActivity(i);
        activity.finish();
    }

    public void checkLogin(Activity activity){
        boolean isLogin= sharedPref.getBoolean(IS_LOGIN,false);

        if(isLogin){
            StartHomeActivity(activity);
        }
    }

    public void checkLogout(Activity activity){
        boolean isLogin= sharedPref.getBoolean(IS_LOGIN,false);

        if(! isLogin){
            StartLoginActivity(activity);
        }
    }

}
