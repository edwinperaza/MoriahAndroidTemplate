package cl.moriahdp.moriahandroidtemplate.baseclasses;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.squareup.otto.Bus;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import cl.moriahdp.moriahandroidtemplate.R;
import cl.moriahdp.moriahandroidtemplate.main.events.DashboardEvent;
import cl.moriahdp.moriahandroidtemplate.utils.BusProvider;


public class BaseJumboActivityView {

    protected WeakReference<BaseJumboActivity> activityRef;
    protected Bus mBus;
    protected DashboardEvent mDashboardEvent;

    @BindView(R.id.pb_base)
    View loadingOverlay;

    private boolean mKeyboardIsVisible;

    public BaseJumboActivityView(BaseJumboActivity activity) {
        activityRef = new WeakReference<>(activity);
        ButterKnife.bind(this, activity);
        mBus = BusProvider.getInstance();
    }


    public BaseJumboActivityView(BaseJumboActivity activity, Bus bus) {
        activityRef = new WeakReference<>(activity);
        this.mBus = bus;
        ButterKnife.bind(this, activity);
    }

    @Nullable
    public AppCompatActivity getActivity() {
        return activityRef.get();
    }

    @Nullable
    public Context getContext() {
        return getActivity();
    }

    public void showLoadingOverlay() {
        loadingOverlay.setVisibility(View.VISIBLE);
    }

    public void hideLoadingOverlay() {
        loadingOverlay.setVisibility(View.GONE);
    }

    public void hideKeyboard() {
        // Check if no view has focus:
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mKeyboardIsVisible = false;
    }

    protected void showKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(view, 0);
        }
        mKeyboardIsVisible = true;
    }

    protected boolean isKeyboardOpen() {
        return mKeyboardIsVisible;
    }

    protected boolean isLoadingOverlayShowing() {
        return loadingOverlay.getVisibility() == View.VISIBLE;
    }

    /**
     * Gets the state of Airplane Mode.
     *
     * @param context
     * @return true if enabled.
     */
    public static boolean isAirplaneModeOn(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(context.getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
        } else {
            return Settings.System.getInt(context.getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 0) != 0;
        }

    }

    public static boolean isConnectionOff(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();
        return info == null || !info.isConnectedOrConnecting();
    }


    protected DashboardEvent getDashboardEvent() {
        return mDashboardEvent;
    }

    public void setDashboardEvent(BaseJumboFragment baseJumboFragment, String tag) {
        this.mDashboardEvent = new DashboardEvent(baseJumboFragment, R.id.tab_container, tag);
    }

    protected void cleanDashboardEvent() {
        this.mDashboardEvent = null;
    }
}
