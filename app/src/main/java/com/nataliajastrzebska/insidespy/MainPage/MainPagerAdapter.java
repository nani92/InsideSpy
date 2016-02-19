package com.nataliajastrzebska.insidespy.mainpage;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nataliajastrzebska.insidespy.mainpage.Codes.HelpFragment;
import com.nataliajastrzebska.insidespy.mainpage.SpyOnMe.SpyOnMeFragment;
import com.nataliajastrzebska.insidespy.mainpage.TrackOthers.TrackOthersFragment;
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
        return 3;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return new SpyOnMeFragment();
        }

        if (position == 1) {
            return new TrackOthersFragment();
        }

        return new HelpFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0) {
            return context.getResources().getString(R.string.spy_on_me);
        }

        if (position == 1) {
            return context.getResources().getString(R.string.track_others);
        }

        if (position == 2) {
            return context.getResources().getString(R.string.help);
        }

        return "";
    }

    public int getItemPosition(Object object){
        return POSITION_NONE;
    }
}
