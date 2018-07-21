package com.firebolt.assignment.navigation;


import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;

import android.view.MenuItem;

import io.reactivex.disposables.Disposable;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    private AppCompatDialog dialog;
    private Disposable logoutDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new AppCompatDialog(this);
      //  SetImageUtil.setGifForLoader((ImageView) dialog.findViewById(R.id.loaderGif));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
     /*       default:
                return super.onOptionsItemSelected(item);*/
        }
        return super.onOptionsItemSelected(item);
    }


    public void showProgressDialog() {
        showProgressDialog(false, "");
    }

    public void showProgressDialog(boolean showProgressLabel, String lable) {

        dialog.show();
    }


    public void dismissProgressDialog() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    public AppCompatDialog getDialog() {
        return dialog;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
        dialog = null;
    }
}