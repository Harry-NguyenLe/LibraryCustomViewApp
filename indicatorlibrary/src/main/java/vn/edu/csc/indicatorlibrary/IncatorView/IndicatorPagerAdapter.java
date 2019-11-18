package vn.edu.csc.indicatorlibrary.IncatorView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import vn.edu.csc.indicatorlibrary.IncatorView.FragmentOfViewPager.Page1;
import vn.edu.csc.indicatorlibrary.IncatorView.FragmentOfViewPager.Page2;
import vn.edu.csc.indicatorlibrary.IncatorView.FragmentOfViewPager.Page3;

public class IndicatorPagerAdapter extends FragmentStatePagerAdapter {
    public static final int NUM_PAGES = 3;


    public IndicatorPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Page1();
            case 1:
                return new Page2();
            case 2:
                return new Page3();
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
