package com.wms.mwt.e_wejob.view;

import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.wms.mwt.e_wejob.R;
import com.wms.mwt.e_wejob.utility.adapter.SectionsPagerAdapter;
import com.wms.mwt.e_wejob.utility.manage.SessionManagement;

public abstract class HomeActivity extends AppCompatActivity {

    public FloatingActionButton fab,fabLogout;
    public SessionManagement sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sessionManager= SessionManagement.getInstance(getApplicationContext());
        sessionManager.checkLogout(this);

        fab=findViewById(R.id.fab);
        fabLogout=findViewById(R.id.fabLogout);
        fabLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout(HomeActivity.this);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewItem();
            }
        });

        SectionsPagerAdapter sectionsPagerAdapter = getSectionPagerAdapter();
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        setupTabIcons(tabs);

        setupWindowAnimation();

    }
    public  abstract void setupTabIcons(TabLayout tabLayout);

    public abstract void AddNewItem();

    public abstract SectionsPagerAdapter getSectionPagerAdapter();


    public void setupWindowAnimation(){
        android.transition.Slide slideTransition=new Slide();
        slideTransition.setSlideEdge(Gravity.RIGHT);
        slideTransition.setDuration(1000L);

        getWindow().setReenterTransition(slideTransition);
        getWindow().setExitTransition(slideTransition);

        getWindow().setAllowReturnTransitionOverlap(false);
    }


}