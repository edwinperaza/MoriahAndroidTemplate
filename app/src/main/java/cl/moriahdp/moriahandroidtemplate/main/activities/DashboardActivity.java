package cl.moriahdp.moriahandroidtemplate.main.activities;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.squareup.otto.Subscribe;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import cl.moriahdp.moriahandroidtemplate.R;
import cl.moriahdp.moriahandroidtemplate.baseclasses.BaseJumboActivity;
import cl.moriahdp.moriahandroidtemplate.main.events.DashboardEvent;
import cl.moriahdp.moriahandroidtemplate.main.views.DashboardView;
import cl.moriahdp.moriahandroidtemplate.utils.BusProvider;

public class DashboardActivity extends BaseJumboActivity {

    private static final String DASHBOARD_ACTIVITY_TAG = DashboardActivity.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private Fragment mCurrentFragment;
    private DashboardView mDashboardView = null;
    private String mCurrentFragmentTag;
    private static DashboardActivity instance;

    private boolean mDoubleBackToExitPressedOnce;
    private Menu mMenu;


    // variable to track event time
    private static final int LONG_TIME_SECONDS_REDIRECT = 800;
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);
        ButterKnife.bind(this);
        instance = this;
        mDoubleBackToExitPressedOnce = false;
    }

    public static DashboardActivity getInstance() {
        return instance;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
        if (mDashboardView == null)
            mDashboardView = new DashboardView(this);
    }

    @Subscribe
    public void setCurrentFragmentInTab(DashboardEvent dashboardEvent) {
        mCurrentFragment = dashboardEvent.getBaseJumboFragment();
        mCurrentFragmentTag = dashboardEvent.getFragmentTag();
        goToFragmentWithStack(dashboardEvent.getLayoutContainerID(), mCurrentFragment, dashboardEvent.getFragmentTag());
    }


    public void goToFragment(Fragment fragment, String tag) {
        mCurrentFragmentTag = tag;
        mCurrentFragment = fragment;
        super.goToFragment(R.id.tab_container, fragment, tag);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    /**
     * Show a new fragment from Activity. Call super goToFragment at BaseJumboActivity and
     * update mCurrentFragment to be able to go back when user press Back at 2nd, 3rd or other
     * level fragments
     *
     * @param layout   to be replace by new fragment
     * @param fragment to be shown to user
     * @param tag      to customize process
     */
    @Override
    public void goToFragment(int layout, Fragment fragment, String tag) {
        super.goToFragment(layout, fragment, tag);
        mCurrentFragment = fragment;
        mCurrentFragmentTag = tag;
    }

    @Override
    public void goToFragmentWithStack(int fragmentContainer, Fragment fragment, String tag) {
        mCurrentFragment = fragment;
        setmCurrentFragmentTag(tag);
        super.goToFragmentWithStack(fragmentContainer, fragment, tag);
    }


    /**
     * When user is at first level fragment like myProfileFragment and press back the app
     * show initial fragment (Jumbo Now)clear
     */
    public void backToJumboNowFragment() {
        setSelectedTab(R.id.jumbo_now);
    }

    /**
     * When user need to come back to Shopping List Fragment
     */
    public void backToShoppingListFragment() {
        setSelectedTab(R.id.my_lists);
    }

    /**
     * Select specific tabs at bottomBar, also check if bottom bar is null.
     *
     * @param tabId id of tab to be selected
     */
    public void setSelectedTab(int tabId) {
        // Preventing multiple clicks, using threshold of 10 second
        if (SystemClock.elapsedRealtime() - mLastClickTime < LONG_TIME_SECONDS_REDIRECT) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        BottomBar bottomBar = mDashboardView.getmBottomBar();
        if (bottomBar != null) {
            bottomBar.selectTabWithId(tabId);
        } else {
            Log.d(DASHBOARD_ACTIVITY_TAG, "BottomBar is null");
            //TODO implement behavior when bottomBar is null
        }
    }

    public int getSelectedTab() {
        BottomBar bottomBar = mDashboardView.getmBottomBar();
        return bottomBar.getCurrentTabId();
    }

    public void hideBottomBar(boolean hide) {

        if (hide) {
            mDashboardView.getmBottomBar().setVisibility(View.GONE);
        } else {
            mDashboardView.getmBottomBar().setVisibility(View.VISIBLE);
        }
    }

    public String getmCurrentFragmentTag() {
        return mCurrentFragmentTag;
    }

    public void setmCurrentFragmentTag(String mCurrentFragmentTag) {
        this.mCurrentFragmentTag = mCurrentFragmentTag;
    }

    public Fragment getmCurrentFragment() {
        return mCurrentFragment;
    }

    public String getCurrentSelectedFragment() {
        return mCurrentFragmentTag;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.mMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void getLastFragmentFromStack() {
        int index = getSupportFragmentManager().getBackStackEntryCount() - 1;
        android.support.v4.app.FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(index);
        String previousTag = backEntry.getName();
        setmCurrentFragmentTag(previousTag);
        mCurrentFragment = getSupportFragmentManager().findFragmentByTag(previousTag);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void doBackPressed() {
        if (mDoubleBackToExitPressedOnce) {
            getSupportFragmentManager().popBackStackImmediate();
            super.onBackPressed();
            return;
        }
        mDoubleBackToExitPressedOnce = true;
        //mDashboardView.showExitSnackbar();
        Timer t = new Timer();
        t.schedule(new TimerTask() {

            @Override
            public void run() {
                mDoubleBackToExitPressedOnce = false;
            }
        }, 2500);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //mDashboardView.showCallAlertDialog();
        } else {
            //TODO: show snackbar with error
        }
    }

    public DashboardView getView() {
        return mDashboardView;
    }

}
