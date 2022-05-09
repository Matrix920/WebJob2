package com.wms.mwt.e_wejob.utility.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wms.mwt.e_wejob.R;
import com.wms.mwt.e_wejob.utility.entity.Diploma;

import java.util.List;

public class DiplomaAdapter extends BaseAdapter {
public List<Diploma>diplomas;
public Context mContext;

    public DiplomaAdapter(List<Diploma> diplomas, Context mContext) {
        this.diplomas = diplomas;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return diplomas.size();
    }
    public void updateList(List<Diploma> diplomas){
        this.diplomas=diplomas;
        notifyDataSetChanged();
    }
    @Override
    public Diploma getItem(int position) {
        return diplomas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).DiplomaID;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if(v==null)
            v= LayoutInflater.from(mContext).inflate(R.layout.diploma, parent,false);

        Diploma d=getItem(position);

        TextView title=v.findViewById(R.id.txtDiplomaTitle);
        title.setText(d.DiplomaTitle);

        return v;
    }
}
