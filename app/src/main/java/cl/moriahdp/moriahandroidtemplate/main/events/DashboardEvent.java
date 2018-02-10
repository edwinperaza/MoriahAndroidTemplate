package cl.moriahdp.moriahandroidtemplate.main.events;

import cl.moriahdp.moriahandroidtemplate.baseclasses.BaseJumboFragment;

public class DashboardEvent {

    private BaseJumboFragment mBaseJumboFragment;
    private int mLayoutContainerID;
    private String mFragmentTag;

    public DashboardEvent(BaseJumboFragment baseJumboFragment, int layoutContainerID, String fragmentTag) {
        mBaseJumboFragment = baseJumboFragment;
        mLayoutContainerID = layoutContainerID;
        mFragmentTag = fragmentTag;
    }

    public BaseJumboFragment getBaseJumboFragment() {
        return mBaseJumboFragment;
    }

    public int getLayoutContainerID() {
        return mLayoutContainerID;
    }

    public String getFragmentTag() {
        return mFragmentTag;
    }
}
