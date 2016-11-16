package com.example.prateekjoshi.agri_app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Prateek Joshi on 11/15/2016.
 */

public class RegistrationAdapter extends FragmentPagerAdapter {

    public RegistrationAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {

            case 0: return Registration1.newInstance("FirstFragment, Instance 1");
            case 1: return Registration2.newInstance("SecondFragment, Instance 1");
            case 2: return Registration3.newInstance("ThirdFragment, Instance 1");
           // case 3: return ThirdFragment.newInstance("ThirdFragment, Instance 2");
           // case 4: return ThirdFragment.newInstance("ThirdFragment, Instance 3");
            default: return Registration1.newInstance("ThirdFragment, Default");
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
