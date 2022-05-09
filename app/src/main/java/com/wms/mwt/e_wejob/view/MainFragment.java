package com.wms.mwt.e_wejob.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wms.mwt.e_wejob.R;

public class MainFragment extends Fragment {

    public TextView txtNoData;
    public ListView list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_main,container,false);

        list=v.findViewById(R.id.list);
        txtNoData=v.findViewById(R.id.txtNodata);

        return v;
    }

    public void hideText(){
        txtNoData.setVisibility(View.GONE);
    }

    public void viewText(){
        txtNoData.setVisibility(View.VISIBLE);
    }
}
