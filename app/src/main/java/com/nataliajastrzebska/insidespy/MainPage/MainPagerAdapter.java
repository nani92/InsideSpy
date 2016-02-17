package com.nataliajastrzebska.insidespy.MainPage;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nataliajastrzebska.insidespy.MainPage.SpyOnMe.SpyOnMeFragment;
import com.nataliajastrzebska.insidespy.R;

/**
 * Created by nataliajastrzebska on 11/02/16.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {
    Context context;

    public MainPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new SpyOnMeFragment();
        }

        return new TrackOthersFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return context.getResources().getString(R.string.spy_on_me);
        }

        return context.getResources().getString(R.string.track_others);
    }

    public int getItemPosition(Object object){
        return POSITION_NONE;
    }
}
