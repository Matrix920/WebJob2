package com.wms.mwt.e_wejob.view;

import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.wms.mwt.e_wejob.R;
import com.wms.mwt.e_wejob.utility.adapter.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminstratorActivity extends HomeActivity {

    @Override
    public void AddNewItem() {
//        ActivityOptions options= ActivityOptions.makeSceneTransitionAnimation(this);
        Intent i=new Intent(this,AddCompanyActivity.class);
        startActivity(i);
    }

    @Override
    public void setupTabIcons(TabLayout tabLayout) {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_admin);

    }

    @Override
    public SectionsPagerAdapter getSectionPagerAdapter() {
        List<Fragment> fragmentList=new ArrayList<Fragment>(Arrays.asList(new CompaniesFragment()));
        List<String>titles=new ArrayList<>(Arrays.asList("Companies"));

        return new SectionsPagerAdapter(this,getSupportFragmentManager(),fragmentList,titles);
    }
}
