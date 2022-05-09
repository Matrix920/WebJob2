package com.wms.mwt.e_wejob.view;

import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.wms.mwt.e_wejob.R;
import com.wms.mwt.e_wejob.utility.adapter.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CandidateActivity extends HomeActivity {

    @Override
    public SectionsPagerAdapter getSectionPagerAdapter() {
        List<String> titles=new ArrayList<>(Arrays.asList(sessionManager.getName(),"Offered","Suitable"));

        List<Fragment> fragments=new ArrayList<>(Arrays.asList(new DiplomasFragment(),new OfferedJobsFragment(),new SuitableJobsFragment()));

        return new SectionsPagerAdapter(this,getSupportFragmentManager(),fragments,titles);
    }

    @Override
    public void setupTabIcons(TabLayout tabLayout) {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_diploma);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_job);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_job);
    }

    @Override
    public void AddNewItem() {
//        ActivityOptions options= ActivityOptions.makeSceneTransitionAnimation(this);
        Intent i=new Intent(this,AddDiplomaActivity.class);
        startActivity(i);
    }


}
