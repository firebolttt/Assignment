package com.firebolt.assignment.navigation;

import android.databinding.BaseObservable;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Firebolt on 07/21/18.
 */

public abstract class BaseViewModel extends BaseObservable {

    public PublishSubject<Boolean> dialogVisibility = PublishSubject.create();

    public abstract void subscribe();

    public abstract void clear();

    public abstract void dispose();

    public Observable<Boolean> getDialogVisibility() {
        return dialogVisibility.hide();
    }

}
