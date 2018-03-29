package com.leothosthoren.mynews.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.leothosthoren.mynews.controler.fragments.MostPopularFragment;
import com.leothosthoren.mynews.controler.fragments.TopStoriesFragment;

/**
 * Created by Sofiane M. alias Leothos Thoren on 31/01/2018
 */
public class ViewPageAdapter extends FragmentStatePagerAdapter {
    public static final String[] topStoriesSection = {"home", "arts", "science"};
    private String tabTitle[] = {"TOP STORIES", "MOST POPULAR", "SIENCES",};

    protected ViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
//        switch (position) {
//            case 1:
//                return MostPopularFragment.newInstance(position);
//            default:
                return TopStoriesFragment.newInstance(position);
//        }

    }

    @Override
    public int getCount() {
        return tabTitle.length;
    }

    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }


}
