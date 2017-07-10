package net.artgamestudio.charadesapp.util;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Ailton on 22/06/2017 for artGS.<br><br>
 *
 * An auxiliar class for pager Swipe. Use this class to automatically flip between viewpager items.<br>
 * After create your viewpager and adapter, instantiate this class passing the viewpager, item count and time to flip.
 */
public class PagerSwipe implements Runnable, ViewPager.OnPageChangeListener, View.OnTouchListener {

    /***** VARIABLES *****/
    private ViewPager mViewPager;
    private Handler mHandler;
    private int mCount;
    private long mTimeToSwipe;
    private int mCurrentPage;
    private boolean mStop;

    /**
     * Create a pager swipe to a specific viewpager.
     * @param viewPager Viewpager to flip
     * @param count Item count
     * @param timeToSwipe Time in milliseconds to change page
     */
    public PagerSwipe(ViewPager viewPager, int count, long timeToSwipe) {
        mViewPager = viewPager;
        mCount = count;
        mTimeToSwipe = timeToSwipe;
        mStop = false;

        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOnTouchListener(this);
    }

    /**
     * Starts flipping
     * @throws Exception
     */
    public void start() {
        mHandler = new Handler();
        mHandler.postDelayed(this, mTimeToSwipe);
    }

    /**
     * Stop flipping
     */
    public void stop() {
        mStop = true;
    }

    @Override
    public void run() {
        try
        {
            //if was stoped or doesn't exists, returns
            if (mStop || mViewPager == null)
                return;

            //Get the current to move to next
            mCurrentPage = mViewPager.getCurrentItem();

            //Move to next page
            mCurrentPage++;
            if (mCurrentPage == mCount)
                mCurrentPage = 0;

            mViewPager.setCurrentItem(mCurrentPage);
        }
        catch (Exception error)
        {
            Log.e("Error", "Error at run() in " + getClass().getName() + ". " + error.getMessage());
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        try
        {
            //If the viewpager is touched, stop flipping
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                mHandler.removeCallbacks(this);
            }
            //When its released, flipps again
            else if (event.getAction() == MotionEvent.ACTION_UP) {
                mHandler.postDelayed(this, mTimeToSwipe);
            }
        }
        catch (Exception error)
        {
            Log.e("Error", "Error at onTouch() in " + getClass().getName() + ". " + error.getMessage());
        }

        return false;
    }

    @Override
    public void onPageSelected(int position) {
        try
        {
            //Restarts automatic flip
            mHandler.removeCallbacks(this);
            mHandler.postDelayed(this, mTimeToSwipe);
        }
        catch (Exception error)
        {
            Log.e("Error", "Error at run() in " + getClass().getName() + ". " + error.getMessage());
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}


    @Override
    public void onPageScrollStateChanged(int state) {}
}
