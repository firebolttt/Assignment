package com.firebolt.assignment.mainmodule.dataproviders;

import com.firebolt.assignment.models.ResponseBean;
import com.firebolt.assignment.models.UserDetails;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public interface DataProvider {
    Disposable getUser(int pageIndex, Consumer<ResponseBean>success, Consumer<Throwable>error);
}
