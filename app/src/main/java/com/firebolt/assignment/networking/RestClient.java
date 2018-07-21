package com.firebolt.assignment.networking;

import android.widget.Toast;

import com.firebolt.assignment.App;
import com.firebolt.assignment.BuildConfig;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * For REST over HTTP(S). Holds the client for other services to put interfaces against.
 */
public class RestClient {
    private static final String TAG = RestClient.class.toString();
/*
    public RestClient() {
        getClient();
    }*/

    /**
     * @return
     */
    public static Retrofit getClient() {
        return RetrofitAPI.retrofit;
    }



    private static class RetrofitAPI {
        private static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

        static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(BuildConfig.DEBUG ? httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY) : httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE))
              //  .addInterceptor(new ConnectivityInterceptor())
                .retryOnConnectionFailure(false)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        private static Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIConstants.getBaseUrl())//APIConstants.getBaseUrl()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();



    }
}
