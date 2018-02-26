package com.leothosthoren.mynews.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.leothosthoren.mynews.controler.fragments.PageFragment;

/**
 * Created by Sofiane M. alias Leothos Thoren on 31/01/2018
 */
public class ViewPageAdapter extends FragmentStatePagerAdapter {
    public static final String[] topStoriesSection = {"home", "arts",};
    private String tabTitle[] = {"TOP STORIES", "MOST POPULAR",};

    protected ViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return tabTitle.length;
    }

    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }


}
