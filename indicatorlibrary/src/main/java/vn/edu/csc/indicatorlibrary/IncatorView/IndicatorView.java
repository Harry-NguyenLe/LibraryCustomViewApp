package vn.edu.csc.indicatorlibrary.IncatorView;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import vn.edu.csc.indicatorlibrary.R;

public class IndicatorView extends View implements IndicatorInterface, ViewPager.OnPageChangeListener {

    private static final long DEFAULT_ANIMATE_DURATION = 200;

    private static final int DEFAULT_RADIUS_SELECTED = 20;

    private static final int DEFAULT_RADIUS_UNSELECTED = 15;

    private static final int DEFAULT_DISTANCE = 40;

    private ViewPager viewPager;

    private Dot[] dots;

    private long animateDuration = DEFAULT_ANIMATE_DURATION;

    private int radiusSelected = DEFAULT_RADIUS_SELECTED;

    private int radiusUnselected = DEFAULT_RADIUS_UNSELECTED;

    private int distance = DEFAULT_DISTANCE;

    private int colorSelected;

    private int colorUnselected;

    private int currentPosition;

    private int beforePosition;

    private ValueAnimator animatorZoomIn;

    private ValueAnimator animatorZoomOut;

    public IndicatorView(Context context) {
        super(context);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IndicatorView);
        radiusSelected = typedArray.getDimensionPixelSize(R.styleable.IndicatorView_harry_dot_radius_selected, DEFAULT_RADIUS_SELECTED);
        radiusUnselected = typedArray.getDimensionPixelSize(R.styleable.IndicatorView_harry_dot_radius_unselected, DEFAULT_RADIUS_UNSELECTED);

        colorSelected = typedArray.getColor(R.styleable.IndicatorView_harry_dot_color_selcted, Color.parseColor("#FFFFFF"));
        colorUnselected = typedArray.getColor(R.styleable.IndicatorView_harry_dot_color_unselcted, Color.parseColor("#FFFFFF"));

        distance = typedArray.getInt(R.styleable.IndicatorView_harry_dot_distance, DEFAULT_DISTANCE);

        typedArray.recycle();
    }

    private void initDot(int pageCount) throws PageLessException {
        if (pageCount < 2) throw new PageLessException();

        dots = new Dot[pageCount];
        for (int i = 0; i < dots.length; i ++){
            dots[i] = new Dot();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        try {
            initDot(IndicatorPagerAdapter.NUM_PAGES);
        } catch (PageLessException e) {
            e.printStackTrace();
        }

        float yCenter = getHeight() / 2;

        int d = distance + 2 * radiusUnselected;

        float firstXCenter = ((getWidth() / 2) - (dots.length - 1) * d / 2);

        for (int i = 0; i < dots.length ; i ++){
            dots[i].setCenter(i == 0 ? firstXCenter : firstXCenter + d * i, yCenter);
            dots[i].setCurrentRadius(i == currentPosition ? radiusSelected : radiusUnselected);
            dots[i].setColor(i == currentPosition ? colorSelected : colorUnselected);
            dots[i].setAlpha(i == currentPosition ? 255 : radiusUnselected * 255 / radiusSelected);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //disredHeight là chiều cao mong muốn có được, trong trường hợp này ta muốn chiều cao bằng 2 lần bán kính chấm tròn lúc được chọn
        int desiredHeight = 2 * radiusSelected;
        int width;
        int height;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(widthMeasureSpec);

        //DesireWidth: dựa vào nội dung muốn hiển thị mà bạn sẽ tính ra bạn cần tối thiểu bao nhiêu không gian để bạn hiển thị
        if (widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }else if (widthMode == MeasureSpec.AT_MOST){
            width = widthSize;
        }else {
            width = 0;
        }

        if (heightMode == MeasureSpec.EXACTLY || heightMode == MeasureSpec.AT_MOST){
            height = desiredHeight;
        }else {
            height = desiredHeight;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Dot dot : dots){
            dot.draw(canvas);
        }
    }

    @Override
    public void setViewPager(ViewPager viewPager) throws PageLessException {
        this.viewPager = viewPager;
        viewPager.addOnPageChangeListener(this);
        initDot(viewPager.getAdapter().getCount());
        onPageSelected(0);
    }

    @Override
    public void setAnimateDuration(long animateDuration) {
        this.animateDuration = animateDuration;
    }

    @Override
    public void setRadiusSelected(int radiusSelected) {
        this.radiusSelected = radiusSelected;
    }

    @Override
    public void setRadisusUnselected(int radiusUnselected) {
        this.radiusUnselected = radiusUnselected;
    }

    @Override
    public void setDistanceDot(int distance) {
        this.distance = distance;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        beforePosition = currentPosition;
        currentPosition = position;

        if (beforePosition == currentPosition) {
            beforePosition = currentPosition + 1;
        }

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(animateDuration);

        animatorZoomIn = ValueAnimator.ofInt(radiusUnselected, radiusSelected);
        animatorZoomIn.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            int positionPerform = currentPosition;

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int newRadius = (int) valueAnimator.getAnimatedValue();
                changeNewRadius(positionPerform, newRadius);
            }
        });

        animatorZoomOut = ValueAnimator.ofInt(radiusSelected, radiusUnselected);
        animatorZoomOut.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            int positionPerform = beforePosition;

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int newRadius = (int) valueAnimator.getAnimatedValue();
                changeNewRadius(positionPerform, newRadius);
            }
        });

        animatorSet.play(animatorZoomIn).with(animatorZoomOut);
        animatorSet.start();

    }

    private void changeNewRadius(int positionPerform, int newRadius) {
        if (dots[positionPerform].getCurrentRadius() != newRadius) {
            dots[positionPerform].setCurrentRadius(newRadius);
            dots[positionPerform].setAlpha(newRadius * 255 / radiusSelected);
            invalidate();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
