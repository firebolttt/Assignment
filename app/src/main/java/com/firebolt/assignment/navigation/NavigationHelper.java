package com.firebolt.assignment.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.firebolt.assignment.R;

/**
 * Created by Firebolt on 07/21/18.
 */

public class NavigationHelper {

    public static void addFragment(FragmentManager fragmentManager, Fragment fragment, int containerId, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(containerId, fragment, tag);
        transaction.commitNowAllowingStateLoss();
    }

    public static void replaceFragment(FragmentManager fragmentManager, Fragment fragment, int containerId, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(containerId, fragment, tag);
        transaction.commitNowAllowingStateLoss();
    }

    public static void navigateTo(Context activity, String fragmentId, Bundle bundle) {
        if(null != activity){
            Intent intent = TemplateActivity.createIntent(activity, fragmentId, bundle);
            activity.startActivity(intent);
        }
    }

    public static void navigateWithDataTo(Context activity, String fragmentId, Bundle bundle, Uri uriData) {
        if(null != activity) {
            Intent intent = TemplateActivity.createIntent(activity, fragmentId, bundle);
            if (null != uriData)
                intent.setData(uriData);
            activity.startActivity(intent);
        }
    }

    public static void navigateWithAnimation(Context activity, String fragmentId, Bundle bundle, View view) {
        if(null != activity) {
            if (null != view) {
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((AppCompatActivity) activity,
                        view, activity.getString(R.string.str_appcompact_transaction));
                ActivityCompat.startActivity(activity, TemplateActivity.createIntent(activity, fragmentId, bundle), optionsCompat.toBundle());
            } else {
                ActivityCompat.startActivity(activity, TemplateActivity.createIntent(activity, fragmentId, bundle), null);
            }
        }
    }


    public static void navigateWithResult(Context activity, String fragmentKey, int requestCode, Bundle bundle, View view) {
        if(null != activity) {
            if (null != view) {
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((AppCompatActivity) activity, view, activity.getString(R.string.str_appcompact_transaction));
                ActivityCompat.startActivityForResult(((AppCompatActivity) activity), TemplateActivity.createIntent(activity, fragmentKey, bundle), requestCode, optionsCompat.toBundle());
            } else {
                ((AppCompatActivity) activity).startActivityForResult(TemplateActivity.createIntent(activity, fragmentKey, bundle), requestCode);
            }
        }
    }

}