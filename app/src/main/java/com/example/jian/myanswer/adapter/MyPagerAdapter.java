package com.example.jian.myanswer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by jian on 2017/2/2.
 */

public class MyPagerAdapter<T extends Fragment> extends FragmentStatePagerAdapter {
   private List<T> mFragments;
    public MyPagerAdapter(FragmentManager fm,List<T> fragments) {
        super(fm);
        this.mFragments = fragments;
    }
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }
    @Override
    public int getCount() {
        return mFragments.size();
    }
}
