package com.firebolt.assignment.mainmodule;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.firebolt.assignment.App;
import com.firebolt.assignment.R;
import com.firebolt.assignment.databinding.ActivityMainBinding;
import com.firebolt.assignment.mainmodule.fragments.ShowDataFragment;
import com.firebolt.assignment.models.ResponseBean;
import com.firebolt.assignment.models.UserDetails;
import com.firebolt.assignment.navigation.BaseActivity;
import com.firebolt.assignment.navigation.FragmentFactory;
import com.firebolt.assignment.navigation.NavigationHelper;
import com.firebolt.assignment.networking.APIConstants;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                NavigationHelper.navigateTo(MainActivity.this, FragmentFactory.SHOW_DATA, null);
                finish();
            }
        }, 3000);

    }


}
