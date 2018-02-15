package com.leothosthoren.mynews.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.leothosthoren.mynews.controler.fragments.PageFragment;

/**
 * Created by Sofiane M. alias Leothos Thoren on 31/01/2018
 */
public class ViewPageAdapter extends FragmentStatePagerAdapter {
    private int[] colors;
    private String tabTitle[] = {"TOP STORIES", "MOST POPULAR","BUSINESS","OTHERS",};

    public ViewPageAdapter(FragmentManager fm, int[] colors) {
        super(fm);
        this.colors = colors;
    }

    @Override
    public Fragment getItem(int position) {

        return PageFragment.newInstance(position, this.colors[position]);
    }

    @Override
    public int getCount() {
        return tabTitle.length;
    }

    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }

}
