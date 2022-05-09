package com.wms.mwt.e_wejob.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wms.mwt.e_wejob.R;
import com.wms.mwt.e_wejob.utility.entity.Candidate;
import com.wms.mwt.e_wejob.utility.entity.Company;
import com.wms.mwt.e_wejob.utility.manage.SessionManagement;
import com.wms.mwt.e_wejob.utility.connection.HttpApis;
import com.wms.mwt.e_wejob.utility.WeJobUtil;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class AddCompanyActivity extends AppCompatActivity {

    SessionManagement sessionManager;

    public static final String TAG=AddCompanyActivity.class.getSimpleName();

    EditText edtLogin,edtPassword,edtCName,edtTel;

    ProgressDialog progressDialog;

    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);

//        initTransition();

        sessionManager= SessionManagement.getInstance(getApplicationContext());
        sessionManager.checkLogout(this);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Saving product...");

        edtLogin=findViewById(R.id.edtLogin);
        edtPassword=findViewById(R.id.edtPassword);
        edtCName=findViewById(R.id.edtCName);
        edtTel=findViewById(R.id.edtTel);

        btnSave=findViewById(R.id.btnSaveCompany);

    }

    public void saveCompany(View v){
        if(! WeJobUtil.isEmpty(edtLogin,edtPassword,edtCName,edtTel)) {
            register();
        }
        else
            WeJobUtil.showToast(AddCompanyActivity.this, getResources().getString(R.string.empty));
    }

    void initTransition(){

        android.transition.Slide enterTransition=new android.transition.Slide();
        enterTransition.setSlideEdge(Gravity.LEFT);
        enterTransition.setDuration(500);
        enterTransition.setInterpolator(new LinearInterpolator());
        getWindow().setEnterTransition(enterTransition);

    }

    private void register(){
        AsyncHttpClient httpClient=new AsyncHttpClient();

        RequestParams params=new RequestParams();
        params.add(Candidate.LOGIN,edtLogin.getText().toString().trim());
        params.add(Candidate.PASSWORD,edtPassword.getText().toString());
        params.add(Company.C_NAME,edtCName.getText().toString());
        params.add(Candidate.TEL,edtTel.getText().toString());

        httpClient.post(getApplicationContext(), HttpApis.POST_ADD_COMPANY,params, new  AsyncHttpResponseHandler() {

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

                        if(success){
                            WeJobUtil.showToast(AddCompanyActivity.this,getResources().getString(R.string.success));
                            finish();
                        }else{
                            WeJobUtil.showToast(AddCompanyActivity.this,getResources().getString(R.string.error));
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
}
