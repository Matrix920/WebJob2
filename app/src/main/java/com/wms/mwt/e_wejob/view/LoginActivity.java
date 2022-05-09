package com.wms.mwt.e_wejob.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wms.mwt.e_wejob.R;
import com.wms.mwt.e_wejob.utility.entity.Candidate;
import com.wms.mwt.e_wejob.utility.entity.Login;
import com.wms.mwt.e_wejob.utility.manage.SessionManagement;
import com.wms.mwt.e_wejob.utility.connection.HttpApis;
import com.wms.mwt.e_wejob.utility.WeJobUtil;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG=LoginActivity.class.getSimpleName();

    SessionManagement sessionManager;

    EditText edtLogin,edtPassword;
    Button btnLogin;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager= SessionManagement.getInstance(getApplicationContext());
        sessionManager.checkLogin(this);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Signing in");

        edtLogin=findViewById(R.id.edtLogin);
        edtPassword=findViewById(R.id.edtPassword);
        btnLogin=findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(! WeJobUtil.isEmpty(edtLogin,edtPassword))
                    login();
                else
                    WeJobUtil.showToast(LoginActivity.this,getResources().getString(R.string.empty));
            }
        });
    }

    private void login(){
            AsyncHttpClient httpClient=new AsyncHttpClient();

            RequestParams params=new RequestParams();
            params.add(Candidate.LOGIN,edtLogin.getText().toString().trim());
            params.add(Candidate.PASSWORD,edtPassword.getText().toString());

            httpClient.post(getApplicationContext(), HttpApis.POST_LOGIN,params, new  AsyncHttpResponseHandler() {

                @Override
                public void onStart() {
                    progressDialog.show();
                    super.onStart();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if(responseBody!=null){
                        Log.e(TAG,responseBody.toString());
                        try {
                            JSONObject object = new JSONObject(new String(responseBody));

                            boolean success=object.getBoolean(HttpApis.SUCCESS);
                            if(success) {
                                String role = object.getString(Login.ROLE);
                                int id = object.getInt(Login.ID);
                                String name=object.getString(Login.NAME);

                                sessionManager.login(id, role,name, LoginActivity.this);
                            }else{
                                WeJobUtil.showToast(LoginActivity.this,"Authentication Error");
                            }

                        }catch (JSONException e){
                            Log.e(TAG,e.getMessage());
                        }
                    }

                    progressDialog.hide();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    progressDialog.hide();
                    error.printStackTrace();
                }

            });

        }

        public void register(View v){
            Intent i=new Intent(this,RegisterCandidateActivity.class);
            startActivity(i);
            finish();
        }

}
