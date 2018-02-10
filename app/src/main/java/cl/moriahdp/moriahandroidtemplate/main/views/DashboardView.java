package cl.moriahdp.moriahandroidtemplate.main.views;

import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.widget.FrameLayout;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import cl.moriahdp.moriahandroidtemplate.R;
import cl.moriahdp.moriahandroidtemplate.baseclasses.BaseJumboActivity;
import cl.moriahdp.moriahandroidtemplate.baseclasses.BaseJumboActivityView;
import cl.moriahdp.moriahandroidtemplate.baseclasses.BaseJumboFragment;
import cl.moriahdp.moriahandroidtemplate.main.JumboNowFragment;
import cl.moriahdp.moriahandroidtemplate.main.activities.DashboardActivity;
import cl.moriahdp.moriahandroidtemplate.main.events.DashboardEvent;

import static android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

public class DashboardView extends BaseJumboActivityView {

    private static final String TAG = "DashboardView";

    @BindView(R.id.tab_container)
    FrameLayout tabContainer;

    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;

    private int mUpdateSalesChannelAttemps = 0;

    public DashboardView(BaseJumboActivity activity) {
        super(activity);
        mBottomBar.setDefaultTab(R.id.jumbo_now);
        mBottomBar.setActiveTabColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark));
        addBottomTabsBarListener();
    }

    @Override
    public void setDashboardEvent(BaseJumboFragment baseJumboFragment, String tag) {
        super.setDashboardEvent(baseJumboFragment, tag);
    }


    private void addBottomTabsBarListener() {
        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                activityRef.get().getSupportFragmentManager().popBackStack(null, POP_BACK_STACK_INCLUSIVE);

                switch (tabId) {
                    case R.id.jumbo_now:
                        if (getDashboardEvent() != null) {
                            sendDashboardEvent(getDashboardEvent());
                            cleanDashboardEvent();
                        } else {
                            sendDashboardEvent(JumboNowFragment.newInstance(), JumboNowFragment.getJumboNowTag());
                        }
                        break;
                    case R.id.catalog:
                        if (getDashboardEvent() != null) {
                            sendDashboardEvent(getDashboardEvent());
                            cleanDashboardEvent();
                        } else {
                            sendDashboardEvent(JumboNowFragment.newInstance(), JumboNowFragment.getJumboNowTag());
                        }
                        break;

                    case R.id.my_lists: {
                        if (getDashboardEvent() != null) {
                            sendDashboardEvent(getDashboardEvent());
                            cleanDashboardEvent();
                        } else {
                            sendDashboardEvent(JumboNowFragment.newInstance(), JumboNowFragment.getJumboNowTag());
                        }
                        break;
                    }

                    case R.id.my_profile: {
                        if (getDashboardEvent() != null) {
                            sendDashboardEvent(getDashboardEvent());
                            cleanDashboardEvent();
                        } else {
                            sendDashboardEvent(JumboNowFragment.newInstance(), JumboNowFragment.getJumboNowTag());
                        }
                        break;
                    }
                }
            }
        });

        mBottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                if (isLoadingOverlayShowing()) {
                    hideLoadingOverlay();
                }

                switch (tabId) {
                    case R.id.catalog:
                        if (!((DashboardActivity) activityRef.get()).getmCurrentFragmentTag().equals(JumboNowFragment.getJumboNowTag())) {
                            activityRef.get().getSupportFragmentManager().popBackStack(null, POP_BACK_STACK_INCLUSIVE);
                            sendDashboardEvent(JumboNowFragment.newInstance(), JumboNowFragment.getJumboNowTag());
                        }
                        break;
                    case R.id.jumbo_now:
                        if (!((DashboardActivity) activityRef.get()).getmCurrentFragmentTag().equals(JumboNowFragment.getJumboNowTag())) {
                            activityRef.get().getSupportFragmentManager().popBackStack(null, POP_BACK_STACK_INCLUSIVE);
                            sendDashboardEvent(JumboNowFragment.newInstance(), JumboNowFragment.getJumboNowTag());
                        }
                        break;
                    case R.id.my_lists:
                        if (!((DashboardActivity) activityRef.get()).getmCurrentFragmentTag().equals(JumboNowFragment.getJumboNowTag())) {
                            activityRef.get().getSupportFragmentManager().popBackStack(null, POP_BACK_STACK_INCLUSIVE);
                            sendDashboardEvent(JumboNowFragment.newInstance(), JumboNowFragment.getJumboNowTag());
                        }
                        break;
                    case R.id.my_profile:
                        if (!((DashboardActivity) activityRef.get()).getmCurrentFragmentTag().equals(JumboNowFragment.getJumboNowTag())) {
                            activityRef.get().getSupportFragmentManager().popBackStack(null, POP_BACK_STACK_INCLUSIVE);
                            sendDashboardEvent(JumboNowFragment.newInstance(), JumboNowFragment.getJumboNowTag());
                        }
                        break;
                }
            }
        });
    }

    public void setSelectedTab(int tabId) {
        mBottomBar.selectTabWithId(tabId);
    }

    public boolean checkCurrentTabSelected(int tabId) {
        return tabId == mBottomBar.getCurrentTab().getId();
    }

    public BottomBar getmBottomBar() {
        return mBottomBar;
    }

    private void sendDashboardEvent(BaseJumboFragment baseJumboFragment, String tag) {
        mBus.post(new DashboardEvent(baseJumboFragment, R.id.tab_container, tag));
    }

    private void sendDashboardEvent(DashboardEvent dashboardEvent) {
        mBus.post(dashboardEvent);
    }
}