package com.firebolt.assignment.mainmodule;

import android.content.Context;
import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;

import com.firebolt.assignment.models.ResponseBean;
import com.firebolt.assignment.models.UserDetails;
import com.firebolt.assignment.navigation.BaseViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class MainViewModel extends BaseViewModel {
    private MainManager manager;
    private int totalItemCount, thresholdVisibleItems = 3, pageIndex = 1;
    private boolean callApi;
    public ObservableInt txtVisibility, listVisibility;

    public MainViewModel(CompositeDisposable compositeDisposable) {
        manager = new MainManager(compositeDisposable);
        txtVisibility = new ObservableInt(View.VISIBLE);
        listVisibility = new ObservableInt(View.GONE);
    }

    public void getData(final Consumer<ResponseBean>success, final Consumer<Throwable>error) {
        callApi = false;
        dialogVisibility.onNext(true);
        manager.getData(pageIndex, new Consumer<ResponseBean>() {
            @Override
            public void accept(ResponseBean userDetails) throws Exception {
                dialogVisibility.onNext(false);
                txtVisibility.set(View.GONE);
                listVisibility.set(View.VISIBLE);
                Log.d("", "userDetails: " + userDetails);
                totalItemCount = userDetails.getTotal();
              //  thresholdVisibleItems = userDetails.getPerPage();
                callApi = true;
                pageIndex++;
                success.accept(userDetails);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                dialogVisibility.onNext(false);
                throwable.printStackTrace();
                error.accept(throwable);
            }
        });
    }

    public boolean loadMoreData(int totalItemCount, int lastVisibleItemPosition) {
        return this.totalItemCount != totalItemCount && totalItemCount <= (lastVisibleItemPosition + thresholdVisibleItems) && callApi;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void clear() {
        manager.clear();

    }

    @Override
    public void dispose() {
        manager.dispose();

    }
}
