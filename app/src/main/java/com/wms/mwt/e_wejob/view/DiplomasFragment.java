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
import com.wms.mwt.e_wejob.utility.adapter.DiplomaAdapter;
import com.wms.mwt.e_wejob.utility.entity.Candidate;
import com.wms.mwt.e_wejob.utility.entity.Diploma;
import com.wms.mwt.e_wejob.utility.manage.SessionManagement;
import com.wms.mwt.e_wejob.utility.connection.HttpApis;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class DiplomasFragment extends MainFragment {

    SessionManagement sessionManager;

    ProgressDialog progressDialog;

    List<Diploma> diplomaList;

    public DiplomaAdapter adapter;

    int id;

    public static final String TAG= DiplomasFragment.class.getSimpleName();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sessionManager= SessionManagement.getInstance(getContext());
        sessionManager.checkLogout(getActivity());
        id=sessionManager.getID();

        progressDialog=new ProgressDialog(getContext());

        diplomaList=new ArrayList<>();
        adapter=new DiplomaAdapter(diplomaList,getContext());
        list.setAdapter(adapter);

        getDiplomas();
    }

    private void getDiplomas(){
        AsyncHttpClient httpClient=new AsyncHttpClient();

        RequestParams params=new RequestParams();
        params.put(Candidate.CANDIDATE_ID,String.valueOf(id));

        httpClient.post(getContext(), HttpApis.POST_DIPLOMAS,params, new  AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                progressDialog.show();
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(responseBody!=null) {
                    Log.e(TAG, responseBody.toString());
                    diplomaList = new Gson().fromJson(new String(responseBody), new TypeToken<ArrayList<Diploma>>() {}.getType());

                    adapter.updateList(diplomaList);

                    if(diplomaList.isEmpty())
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

    @Override
    public void onStart() {
        super.onStart();
        getDiplomas();
    }
}
