package com.leothosthoren.mynews.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.leothosthoren.mynews.controler.fragments.TopStoriesFragment;

/**
 * Created by Sofiane M. alias Leothos Thoren on 31/01/2018
 */
public class PageAdapter extends FragmentPagerAdapter {
    private int[] colors;

    public PageAdapter(FragmentManager fm, int[] colors) {
        super(fm);
        this.colors = colors;
    }

    @Override
    public Fragment getItem(int position) {

        return TopStoriesFragment.newInstance(position, this.colors[position]);
    }

    @Override
    public int getCount() {
        return (5);
    }

    public CharSequence getPageTitle(int position) {

        return "Top stories"+ position;
    }
}
