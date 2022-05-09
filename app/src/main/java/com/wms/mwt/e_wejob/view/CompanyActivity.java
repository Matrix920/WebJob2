package com.wms.mwt.e_wejob.view;

import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.wms.mwt.e_wejob.R;
import com.wms.mwt.e_wejob.utility.adapter.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompanyActivity extends HomeActivity {

    @Override
    public SectionsPagerAdapter getSectionPagerAdapter() {
        List<Fragment>fragments=new ArrayList<Fragment>(Arrays.asList(new CompanyJobsFragment()));
        List<String>titles=new ArrayList<>(Arrays.asList(sessionManager.getName()));

        return  new SectionsPagerAdapter(this,getSupportFragmentManager(),fragments,titles);

    }
    @Override
    public void setupTabIcons(TabLayout tabLayout) {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_company);

    }
    @Override
    public void AddNewItem() {
//        ActivityOptions options= ActivityOptions.makeSceneTransitionAnimation(this);
        Intent i=new Intent(this,AddJobActivity.class);
        startActivity(i);
    }
}
