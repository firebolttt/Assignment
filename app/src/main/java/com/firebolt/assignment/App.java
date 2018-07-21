package com.firebolt.assignment;

import android.app.Application;
import android.content.Context;

import com.firebolt.assignment.networking.APIConstants;

import java.lang.ref.WeakReference;


public class App extends Application {
    private String TAG = App.class.getSimpleName();
    private static WeakReference<App> weakReference = new WeakReference<>(null);


    public static App getInstance(){
        return weakReference.get();
    }

    public static Context getContext(){
        App app = weakReference.get();
        return app!=null?app.getApplicationContext():new App().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
weakReference.clear();
weakReference = new WeakReference<>(App.this);
        APIConstants.setAPIEnvironment();

    }

}
