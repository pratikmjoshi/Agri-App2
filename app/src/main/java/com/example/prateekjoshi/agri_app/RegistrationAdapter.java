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

            case 0: return FirstFragment.newInstance("FirstFragment, Instance 1");
            case 1: return SecondFragment.newInstance("SecondFragment, Instance 1");
            case 2: return ThirdFragment.newInstance("ThirdFragment, Instance 1");
            case 3: return ThirdFragment.newInstance("ThirdFragment, Instance 2");
            case 4: return ThirdFragment.newInstance("ThirdFragment, Instance 3");
            default: return ThirdFragment.newInstance("ThirdFragment, Default");
        }
    }

    @Override
    public int getCount() {
        return 0;
    }
}
