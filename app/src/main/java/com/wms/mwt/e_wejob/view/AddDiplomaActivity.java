package com.wms.mwt.e_wejob.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wms.mwt.e_wejob.R;
import com.wms.mwt.e_wejob.utility.entity.Diploma;
import com.wms.mwt.e_wejob.utility.manage.SessionManagement;
import com.wms.mwt.e_wejob.utility.connection.HttpApis;
import com.wms.mwt.e_wejob.utility.WeJobUtil;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class AddDiplomaActivity extends AppCompatActivity {

    SessionManagement sessionManager;

    public static final String TAG=AddCompanyActivity.class.getSimpleName();

    EditText edtDiplomaTitle;

    int id;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diploma);

//        initTransition();

        sessionManager= SessionManagement.getInstance(getApplicationContext());
        sessionManager.checkLogout(this);
        id=sessionManager.getID();

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("adding");

        edtDiplomaTitle=findViewById(R.id.edtDiplomaTitle);
    }

    public void saveDiploma(View v){
        if(! WeJobUtil.isEmpty(edtDiplomaTitle))
            register();
        else
            WeJobUtil.showToast(AddDiplomaActivity.this, getResources().getString(R.string.empty));
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
        params.add(Diploma.DIPLOMA_TITLE,edtDiplomaTitle.getText().toString().trim());
        params.add(Diploma.CANDIDATE_ID,String.valueOf(id));

        httpClient.post(getApplicationContext(), HttpApis.POST_ADD_DIPLOMA,params, new  AsyncHttpResponseHandler() {

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
                            WeJobUtil.showToast(AddDiplomaActivity.this,getResources().getString(R.string.success));
                            finish();
                        }else{
                            WeJobUtil.showToast(AddDiplomaActivity.this,getResources().getString(R.string.error));
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
