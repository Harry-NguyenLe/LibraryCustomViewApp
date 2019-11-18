package vn.edu.csc.indicatorlibrary;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import vn.edu.csc.indicatorlibrary.IncatorView.IndicatorPagerAdapter;
import vn.edu.csc.indicatorlibrary.IncatorView.IndicatorPagerAdapter;
import vn.edu.csc.indicatorlibrary.IncatorView.IndicatorView;
import vn.edu.csc.indicatorlibrary.IncatorView.PageLessException;

public class MainActivity extends AppCompatActivity {
    IndicatorPagerAdapter indicatorPagerAdapter;
    ViewPager view_pager;
    IndicatorView indicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view_pager = findViewById(R.id.view_pager);
        indicatorView = findViewById(R.id.indicator_view);
        FragmentManager fm = getSupportFragmentManager();

        indicatorPagerAdapter = new IndicatorPagerAdapter(fm);
        view_pager.setAdapter(indicatorPagerAdapter);
        try {
            indicatorView.setViewPager(view_pager);
        } catch (PageLessException e) {
            e.printStackTrace();
        }

    }

}
