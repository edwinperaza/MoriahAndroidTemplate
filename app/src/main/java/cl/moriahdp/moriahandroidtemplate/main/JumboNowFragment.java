package cl.moriahdp.moriahandroidtemplate.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cl.moriahdp.moriahandroidtemplate.R;
import cl.moriahdp.moriahandroidtemplate.baseclasses.BaseJumboFragment;
import cl.moriahdp.moriahandroidtemplate.baseclasses.IBackPressedCallback;
import cl.moriahdp.moriahandroidtemplate.main.activities.DashboardActivity;
import cl.moriahdp.moriahandroidtemplate.utils.BusProvider;

public class JumboNowFragment extends BaseJumboFragment implements IBackPressedCallback {

    private static String JUMBO_NOW_TAG = "JumboNowFragment";
    private View mRoot;
//    private JumboNowPresenter mJumboNowPresenter;
//    private JumboNowView mJumboNowView;

    public static JumboNowFragment newInstance() {
        Bundle args = new Bundle();
        JumboNowFragment fragment = new JumboNowFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static JumboNowFragment newInstance(int quantityAdded, String productId) {
        Bundle args = new Bundle();
        JumboNowFragment fragment = new JumboNowFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View onCreateEventView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.jumbo_now_fragment, container, false);
        //mJumboNowPresenter = new JumboNowPresenter(new JumboNowModel(this.getContext(), BusProvider.getInstance()), mJumboNowView);
        return mRoot;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    public static String getJumboNowTag() {
        return JUMBO_NOW_TAG;
    }

    @Override
    public void onFragmentBackPressed() {
        ((DashboardActivity) getActivity()).doBackPressed();
    }

}
