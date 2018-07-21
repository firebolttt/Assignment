package com.firebolt.assignment.navigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.firebolt.assignment.mainmodule.fragments.ShowDataFragment;

/**
 * Created by Firebolt on 07/21/18.
 */

public final class FragmentFactory {
    public static final String SHOW_DATA = "showDataFragment";


    public static Fragment create(String fragmentId, Bundle bundle) {
        switch (fragmentId) {


            case SHOW_DATA:
                return ShowDataFragment.createInstance(bundle);



            default:
                return new Fragment();
        }
    }
}
