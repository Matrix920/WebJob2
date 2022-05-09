package com.wms.mwt.e_wejob.utility.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wms.mwt.e_wejob.R;
import com.wms.mwt.e_wejob.utility.entity.Job;

import java.util.List;

public class JobAdapter extends BaseAdapter {

    List<Job> jobs;
    Context mContext;

    TextView title,salary,education,experience;

    public JobAdapter(List<Job> jobs, Context mContext) {
        this.jobs = jobs;
        this.mContext = mContext;
    }

    public void updateList(List<Job> jobs){
        this.jobs=jobs;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return jobs.size();
    }

    @Override
    public Job getItem(int position) {
        return jobs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).JobID;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if(v==null)
            v= LayoutInflater.from(mContext).inflate(R.layout.job, parent,false);

        Job j=getItem(position);

        title=v.findViewById(R.id.txtTitle);
        salary=v.findViewById(R.id.txtSalary);
        education=v.findViewById(R.id.txtRequiredEducationLevel);
        experience=v.findViewById(R.id.txtRequiredExperienceLevel);

        title.setText(j.Title);
        salary.setText(String.valueOf(j.Salary));
        education.setText(j.RequiredEducationLevel);
        experience.setText(String.valueOf(j.RequiredExperienceYears));

        return v;
    }
}
