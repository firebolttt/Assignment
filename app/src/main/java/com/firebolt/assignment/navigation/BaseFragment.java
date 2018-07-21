package com.firebolt.assignment.navigation;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebolt.assignment.App;
import com.firebolt.assignment.R;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.firebolt.assignment.utils.Constants.NO_NETWORK_ACTION;

/**
 * Created by Firebolt on 07/21/18.
 */

public abstract class BaseFragment extends Fragment {

    private final static String TAG = BaseFragment.class.getSimpleName();
    protected BaseViewModel baseViewModel;
    public BroadcastReceiver networkReceiver;
    private AppCompatDialog dialog;
    private Disposable baseViewModelDisposable;
    private ProgressBar progressBar;

    public static void unbindView(View v) {

        if (v != null) {
            int count = ((ViewGroup) v).getChildCount();
            for (int i = 0; i < count; i++) {
                try {
                    View view = ((ViewGroup) v).getChildAt(i);
                    if (view instanceof ViewGroup && !(view instanceof AdapterView) && !(view instanceof ViewPager) && !(view instanceof RecyclerView))
                        unbindView(view);
                    else {
                        if (!(v instanceof SwipeRefreshLayout)) {
                            ((ViewGroup) v).removeViewAt(i);
                        }
                    }
                    clearView(view);
                } catch (Exception e) {
                    System.out.println("View Exception found" + e.getMessage());
                }
            }
        }
    }

    private static void clearView(View view) {
        if (view instanceof RecyclerView) {
            ((RecyclerView) view).setAdapter(null);
            ((RecyclerView) view).clearOnScrollListeners();
            ((RecyclerView) view).setLayoutManager(null);
        } else if (view instanceof ImageView) {
            ((ImageView) view).setImageDrawable(null);
        } else if (view instanceof WebView) {
//            ((WebView)restoreEntry).setOnTouchListener(null);
        } else if (view instanceof Toolbar) {
            int size = ((Toolbar) view).getMenu().size();
            for (int i = 0; i < size; i++) {
                MenuItem item = ((Toolbar) view).getMenu().getItem(i);
                item.setOnMenuItemClickListener(null);
                MenuItemCompat.setOnActionExpandListener(item, null);
                if (item.getActionView() != null && item.getActionView() instanceof SearchView) {
                    SearchView search = ((SearchView) item.getActionView());
                    search.setOnQueryTextListener(null);
                    search = null;
                }
                item = null;
            }
            ((Toolbar) view).setNavigationOnClickListener(null);
            ((Toolbar) view).setOnMenuItemClickListener(null);
            ((Toolbar) view).removeAllViews();
        } else if (view instanceof ViewPager) {
            ((ViewPager) view).setAdapter(null);
            ((ViewPager) view).removeAllViews();
            ((ViewPager) view).setOnPageChangeListener(null);
        } else if (view instanceof AdapterView) {
            ((AdapterView) view).setOnItemClickListener(null);
        } else {
            view.setOnClickListener(null);
            view.setOnFocusChangeListener(null);
        }
        view.setOnFocusChangeListener(null);
        view.setOnLongClickListener(null);
        view.setOnTouchListener(null);
        view = null;
        //restoreEntry = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpNetworkReceiver();
        dialog = new AppCompatDialog(getActivity());
        dialog.setContentView(R.layout.loader_dialog);
        //progressBar = new ProgressBar(App.getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        if (baseViewModel != null) {
            baseViewModel.subscribe();
            baseViewModelDisposable = baseViewModel.getDialogVisibility().subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean showDialog) {
                   // progressBar.invalidate();
                    if (null != dialog) {
                        if (showDialog)
                            dialog.show();
                        else if (dialog.isShowing())
                            dialog.dismiss();

                    }
                }
            });
        }
        super.onStart();
    }

    @Override
    public void onStop() {
        if (baseViewModel != null) {
            baseViewModel.clear();
        }
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbindView(getView());
        dialog = null;
        releaseMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != baseViewModelDisposable && !baseViewModelDisposable.isDisposed()) {
            baseViewModelDisposable.dispose();
        }

        if (baseViewModel != null) {
            baseViewModel.dispose();
            baseViewModel.getDialogVisibility().unsubscribeOn(AndroidSchedulers.mainThread());
            //   baseViewModel.dialogVisibility = null;
            baseViewModel = null;
        }

        if (networkReceiver != null) {
            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(networkReceiver);
            networkReceiver = null;
        }

    }

    public void releaseMemory() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                goBack();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void goBack() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }
    }


    private void setUpNetworkReceiver() {
        networkReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null && intent.getAction() != null) {
                    if (NO_NETWORK_ACTION.equalsIgnoreCase(intent.getAction()))
                        if (dialog != null && dialog.isShowing())
                            dialog.dismiss();
                }
            }
        };
        LocalBroadcastManager.getInstance(App.getContext()).registerReceiver(networkReceiver, new IntentFilter(NO_NETWORK_ACTION));
    }

    protected void showNonDismissedDialog() {
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            dialog.setCancelable(false);
        }
    }

    protected void dismissProgressDialog() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }
}
