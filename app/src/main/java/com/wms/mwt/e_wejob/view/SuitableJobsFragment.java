package com.wms.mwt.e_wejob.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wms.mwt.e_wejob.utility.adapter.JobAdapter;
import com.wms.mwt.e_wejob.utility.entity.Candidate;
import com.wms.mwt.e_wejob.utility.entity.Job;
import com.wms.mwt.e_wejob.utility.manage.SessionManagement;
import com.wms.mwt.e_wejob.utility.connection.HttpApis;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SuitableJobsFragment extends MainFragment {

    SessionManagement sessionManager;

    ProgressDialog progressDialog;

    List<Job> jobList;

    public JobAdapter adapter;


    int id;

    public static final String TAG= OfferedJobsFragment.class.getSimpleName();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sessionManager= SessionManagement.getInstance(getContext());
        sessionManager.checkLogout(getActivity());
        id=sessionManager.getID();

        progressDialog=new ProgressDialog(getContext());

        jobList=new ArrayList<>();
        adapter=new JobAdapter(jobList,getContext());
        list.setAdapter(adapter);

        getJobs();
    }

    private void getJobs(){
        AsyncHttpClient httpClient=new AsyncHttpClient();

        RequestParams params=new RequestParams();
        params.put(Candidate.CANDIDATE_ID,String.valueOf(id));

        httpClient.post(getContext(), HttpApis.POST_SUITABLE_JOBS,params, new  AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                progressDialog.show();
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(responseBody!=null) {
                    Log.e(TAG, responseBody.toString());
                    jobList = new Gson().fromJson(new String(responseBody), new TypeToken<ArrayList<Job>>() {}.getType());

                    adapter.updateList(jobList);


                    if(jobList.isEmpty())
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
}
