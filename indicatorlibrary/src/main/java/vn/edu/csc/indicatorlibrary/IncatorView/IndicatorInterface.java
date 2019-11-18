package vn.edu.csc.indicatorlibrary.IncatorView;

import androidx.viewpager.widget.ViewPager;

public interface IndicatorInterface{
    void setViewPager(ViewPager viewPager) throws PageLessException;

    void setAnimateDuration(long animateDuration);

    void setRadiusSelected(int radiusSelected);

    void setRadisusUnselected(int radisusUnselected);

    void setDistanceDot(int distanceDot);


}


