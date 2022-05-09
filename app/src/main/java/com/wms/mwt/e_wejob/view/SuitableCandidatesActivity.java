package com.wms.mwt.e_wejob.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wms.mwt.e_wejob.R;
import com.wms.mwt.e_wejob.utility.adapter.CandidateAdapter;
import com.wms.mwt.e_wejob.utility.entity.Candidate;
import com.wms.mwt.e_wejob.utility.entity.Job;
import com.wms.mwt.e_wejob.utility.connection.HttpApis;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SuitableCandidatesActivity extends AppCompatActivity {

    public static final  String TAG=SuitableCandidatesActivity.class.getSimpleName();

    ListView listView;
    CandidateAdapter adapter;
    List<Candidate>list;
    int jobID;
    TextView txtNoData;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suitable_candidates);

//        initTransition();

        txtNoData=findViewById(R.id.txtNodata);

        listView=findViewById(R.id.list);

        progressDialog=new ProgressDialog(this);

        list=new ArrayList<>();
        adapter=new CandidateAdapter(this,list);
        listView.setAdapter(adapter);

        jobID=getIntent().getIntExtra(Job.JOB_ID,0);

        getCandidates();
    }

    private void getCandidates(){
        AsyncHttpClient httpClient=new AsyncHttpClient();

        RequestParams params=new RequestParams();
        params.put(Job.JOB_ID,String.valueOf(jobID));

        httpClient.post(this, HttpApis.POST_GET_SUITABLE_CANDIDATES,params, new  AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                progressDialog.show();
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(responseBody!=null) {
                    Log.e(TAG, responseBody.toString());
                    list = new Gson().fromJson(new String(responseBody), new TypeToken<ArrayList<Candidate>>() {}.getType());

                    adapter.updateList(list);

                    if(list.isEmpty())
                        viewText();
                    else
                        hideText();
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
    public void hideText(){
        txtNoData.setVisibility(View.GONE);
    }

    public void viewText(){
        txtNoData.setVisibility(View.VISIBLE);
    }

    void initTransition(){

        android.transition.Slide enterTransition=new android.transition.Slide();
        enterTransition.setSlideEdge(Gravity.LEFT);
        enterTransition.setDuration(500);
        enterTransition.setInterpolator(new LinearInterpolator());
        getWindow().setEnterTransition(enterTransition);

    }
}
