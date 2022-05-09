package com.wms.mwt.e_wejob.utility.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wms.mwt.e_wejob.R;
import com.wms.mwt.e_wejob.utility.entity.Company;

import java.util.List;

public class CompanyAdapter extends BaseAdapter {

    Context mContext;
    List<Company>companies;

    public CompanyAdapter(Context mContext,List<Company>companies){
        this.mContext=mContext;
        this.companies=companies;
    }

    @Override
    public int getCount() {
        return companies.size();
    }

    @Override
    public Company getItem(int position) {
        return companies.get(position);
    }

    public void updateList(List<Company> companies){
        this.companies=companies;
        notifyDataSetChanged();
    }
    @Override
    public long getItemId(int position) {
        return getItem(position).CompanyID;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if(v==null)
            v= LayoutInflater.from(mContext).inflate(R.layout.company, parent,false);

        Company c=getItem(position);
        TextView cName=v.findViewById(R.id.txtCName);
        TextView tel=v.findViewById(R.id.txtTel);
        TextView login=v.findViewById(R.id.txtLogin);
        TextView password=v.findViewById(R.id.txtPassword);

        login.setText(c.Login);
        password.setText(c.Password);
        cName.setText(c.CName);
        tel.setText(String.valueOf(c.Tel));

        return v;
    }
}
