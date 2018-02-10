package cl.moriahdp.moriahandroidtemplate.baseclasses;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import com.squareup.otto.Bus;

import java.lang.ref.WeakReference;

/**
 * Created by raulstriglio on 6/8/17.
 */

public class BaseJumboFragmentView {

    public WeakReference<BaseJumboFragment> mFragmentRef;
    protected Bus mBus;
    protected View mRootView;

    public BaseJumboFragmentView(BaseJumboFragment fragment, View rootView, Bus bus) {
        mFragmentRef = new WeakReference<>(fragment);
        mBus = bus;
        mRootView = rootView;
    }

    public static boolean isAirplaneModeOn(Context context) {
        return BaseJumboActivityView.isAirplaneModeOn(context);
    }

    public static boolean isConnectionOff(Context context) {
        return BaseJumboActivityView.isConnectionOff(context);
    }


    public void showLoadingOverlay() {
        mFragmentRef.get().showLoadingOverlay();
    }

    public void hideLoadingOverlay() {
        mFragmentRef.get().hideLoadingOverlay();
    }


    public void hideKeyboard() {
        // Check if no view has focus:
        View view = mFragmentRef.get().getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) mFragmentRef.get().getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        mFragmentRef.get().getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return Math.round(px);
    }


    public void showErrorConectionFullScreen(BaseJumboFragment.OnErrorConectionCallback onErrorConectionCallback) {
        mFragmentRef.get().showErrorConectionFullScreen(onErrorConectionCallback);
    }

    public void hideErrorConectionFullScreen() {
        mFragmentRef.get().hideErrorConectionFullScreen();
    }

    public void showErrorLoadingDataFullScreen(BaseJumboFragment.OnErrorLoadingDataCallback onErrorLoadingDataCallback) {
        mFragmentRef.get().showErrorLoadingDataFullScreen(onErrorLoadingDataCallback);
    }

    public void hideErrorLoadingDataFullScreen() {
        mFragmentRef.get().hideErrorLoadingDataFullScreen();
    }

    public View getParentView() {
        return mRootView;
    }
}
