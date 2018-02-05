package com.leothosthoren.mynews.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.leothosthoren.mynews.controler.fragments.PageFragment;

/**
 * Created by Sofiane M. alias Leothos Thoren on 31/01/2018
 */
public class ViewPageAdapter extends FragmentPagerAdapter {
    private int[] colors;

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
        return (3);
    }

    public CharSequence getPageTitle(int position) {

        return "Top stories"+ position;
    }
}
