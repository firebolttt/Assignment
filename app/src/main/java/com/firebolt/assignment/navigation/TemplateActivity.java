package com.firebolt.assignment.navigation;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.firebolt.assignment.R;
import com.firebolt.assignment.databinding.ActivityTemplateBinding;

/**
 * Created by Firebolt on 07/21/18.
 */

public class TemplateActivity extends BaseActivity {

    public static final String FRAGMENT_ID = "fragment_id";
    private Fragment fragment;
    private ActivityTemplateBinding binding;
    private String TAG = TemplateActivity.class.getSimpleName();

    public static Intent createIntent(Context context, String fragmentKey, Bundle extras) {
        return TemplateActivityClass.createIntent(context, fragmentKey, extras);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView();
        setupFragment();
    }

    private void initContentView() {
        binding = DataBindingUtil.setContentView(TemplateActivity.this, R.layout.activity_template);
    }

    private void setupFragment() {
        String fragmentId = getFragmentId();
        if (TextUtils.isEmpty(fragmentId)) return;

        fragment = createFragment(fragmentId);
        if (fragment == null) return;

        displayFragment(fragmentId, fragment);
    }


    public void displayFragment(String fragmentKey, Fragment fragment) {
        NavigationHelper.replaceFragment(getSupportFragmentManager(), fragment, R.id.fragment_container, String.valueOf(fragmentKey));
    }

    private Fragment createFragment(String fragmentKey) {
        return FragmentFactory.create(fragmentKey, getIntent().getExtras());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fragment.setArguments(new Bundle());
        fragment = null;
        binding = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    public String getFragmentId() {
        /*String fragId = getIntent().getStringExtra(FRAGMENT_ID);
        if(TextUtils.isEmpty(fragId)){
            fragId = getIntent().getBundleExtra()
        }*/
        return getIntent().getStringExtra(FRAGMENT_ID);
    }

    private static class TemplateActivityClass {
        private static Intent createIntent(Context context, String fragmentKey, Bundle extras) {
            return new Intent(context, TemplateActivity.class).putExtra(TemplateActivity.FRAGMENT_ID, fragmentKey).putExtras(extras != null ? extras : new Bundle());
        }
    }

}
