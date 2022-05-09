package com.wms.mwt.e_wejob.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wms.mwt.e_wejob.R;
import com.wms.mwt.e_wejob.utility.entity.Job;
import com.wms.mwt.e_wejob.utility.manage.SessionManagement;
import com.wms.mwt.e_wejob.utility.connection.HttpApis;
import com.wms.mwt.e_wejob.utility.WeJobUtil;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class AddJobActivity extends AppCompatActivity {

    SessionManagement sessionManager;

    public static final String TAG=AddJobActivity.class.getSimpleName();

    EditText edtTitle,edtSalary,edtExperience,edtEduaction;

    int id;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);

        sessionManager= SessionManagement.getInstance(getApplicationContext());
        sessionManager.checkLogout(this);
        id=sessionManager.getID();

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("adding");

        edtTitle=findViewById(R.id.edtTitle);
        edtSalary=findViewById(R.id.edtSalary);
        edtExperience=findViewById(R.id.edtRequiredExperienceLevel);
        edtEduaction=findViewById(R.id.edtRequiredEducationLevel);
    }

    public void saveCompany(View v){
        if(! WeJobUtil.isEmpty(edtTitle,edtSalary,edtEduaction,edtExperience))
            save();
        else
            WeJobUtil.showToast(AddJobActivity.this, getResources().getString(R.string.empty));
    }

    private void save(){
        AsyncHttpClient httpClient=new AsyncHttpClient();

        RequestParams params=new RequestParams();
        params.add(Job.TITLE,edtTitle.getText().toString().trim());
        params.add(Job.SALARY,edtSalary.getText().toString().trim());
        params.add(Job.REQUIRED_EDUCATION_LEVEL,edtEduaction.getText().toString().trim());
        params.add(Job.REQUIRED_EXPERIENCE_LEVEL,edtExperience.getText().toString().trim());
        params.add(Job.COMPANY_ID,String.valueOf(id));

        httpClient.post(getApplicationContext(), HttpApis.POST_ADD_JOB,params, new  AsyncHttpResponseHandler() {

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
                            WeJobUtil.showToast(AddJobActivity.this,getResources().getString(R.string.success));
                            finish();
                        }else{
                            WeJobUtil.showToast(AddJobActivity.this,getResources().getString(R.string.error));
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
