package com.firebolt.assignment.mainmodule;

import android.content.Context;

import com.firebolt.assignment.mainmodule.dataproviders.DataProvider;
import com.firebolt.assignment.mainmodule.dataproviders.NetworkDataProvider;
import com.firebolt.assignment.models.ResponseBean;
import com.firebolt.assignment.models.UserDetails;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class MainManager {
    private CompositeDisposable compositeDisposable;
    private DataProvider networkDataProvider;

    MainManager(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
        networkDataProvider = NetworkDataProvider.getInstance();
    }

    public void clear() {
        if (compositeDisposable != null)
            compositeDisposable.clear();
    }

    public void dispose() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable = null;
        }
        networkDataProvider = null;
        networkDataProvider = null;
    }

    public void getData(int pageIndex, final Consumer<ResponseBean> success, final Consumer<Throwable> error) {
        compositeDisposable.add(networkDataProvider.getUser(pageIndex, success,error));
    }
}
