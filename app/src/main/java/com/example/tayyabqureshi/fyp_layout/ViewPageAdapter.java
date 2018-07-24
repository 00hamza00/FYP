package com.example.tayyabqureshi.fyp_layout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tayyab Qureshi on 29-Mar-18.
 */

public class ViewPageAdapter extends FragmentPagerAdapter {

    private final List<Fragment> lstFragment=new ArrayList<>();
    private final List<String> lstTiles=new ArrayList<>();

    public ViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return lstFragment.get(position);
    }

    @Override
    public int getCount() {
        return lstTiles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return lstTiles.get(position);
    }

    public void AddFragment(Fragment fragment, String title)
    {
        lstFragment.add(fragment);
        lstTiles.add(title);
    }



}


