package com.firebolt.assignment.mainmodule.dataproviders;

import android.content.Context;

import com.firebolt.assignment.App;
import com.firebolt.assignment.models.ResponseBean;
import com.firebolt.assignment.models.UserDetails;
import com.firebolt.assignment.networking.RestClient;
import com.firebolt.assignment.utils.NetworkUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NetworkDataProvider implements DataProvider {
    private static NetworkDataProvider providerInstance;
    private MainServices services;

    private NetworkDataProvider() {
        services = RestClient.getClient().create(MainServices.class);

    }

    public static synchronized NetworkDataProvider getInstance() {
        if (providerInstance == null) {
            providerInstance = new NetworkDataProvider();
        }
        return providerInstance;
    }

    @Override
    public Disposable getUser(int pageIndex, Consumer<ResponseBean> success, Consumer<Throwable> error) {
        if(NetworkUtils.canConnect(App.getContext())){
            return services.getUsers(pageIndex).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(success, error);
        }
        return Observable.just(0).subscribe();
    }
}
