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
import com.wms.mwt.e_wejob.utility.adapter.CompanyAdapter;
import com.wms.mwt.e_wejob.utility.entity.Company;
import com.wms.mwt.e_wejob.utility.manage.SessionManagement;
import com.wms.mwt.e_wejob.utility.connection.HttpApis;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class CompaniesFragment extends MainFragment {

    SessionManagement sessionManager;

    ProgressDialog progressDialog;

    List<Company> companyList;

    public CompanyAdapter adapter;

    public static final String TAG= CompaniesFragment.class.getSimpleName();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sessionManager= SessionManagement.getInstance(getContext());
        sessionManager.checkLogout(getActivity());

        progressDialog=new ProgressDialog(getContext());

        companyList=new ArrayList<>();
        adapter=new CompanyAdapter(getContext(),companyList);
        list.setAdapter(adapter);

        getCompanies();
    }

    @Override
    public void onStart() {
        super.onStart();
        getCompanies();
    }

    private void getCompanies(){
        AsyncHttpClient httpClient=new AsyncHttpClient();

        RequestParams params=new RequestParams();

        httpClient.post(getContext(), HttpApis.GET_COMPANIES,params, new  AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                progressDialog.show();
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(responseBody!=null) {
                    Log.e(TAG, responseBody.toString());
                    companyList = new Gson().fromJson(new String(responseBody), new TypeToken<ArrayList<Company>>() {}.getType());

                    adapter.updateList(companyList);
                    if(companyList.isEmpty())
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
