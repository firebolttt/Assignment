package com.firebolt.assignment.utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.firebolt.assignment.R;

import static com.firebolt.assignment.utils.Constants.NO_NETWORK_ACTION;

public class NetworkUtils {

    public static boolean canConnect(Context mContext) {
        boolean ret = false;
        ConnectivityManager connectMgr = (ConnectivityManager) mContext.getSystemService(Service.CONNECTIVITY_SERVICE);
        if(null != connectMgr){
            NetworkInfo networkinfo = connectMgr.getActiveNetworkInfo();

            if (networkinfo != null && networkinfo.isConnected() && networkinfo.getState() == NetworkInfo.State.CONNECTED)
                ret = true;
            else {
                Toast.makeText(mContext, mContext.getResources().getString(R.string.connecttointernet), Toast.LENGTH_SHORT).show();
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent(NO_NETWORK_ACTION));
            }
        }
        Log.v("component coreutils", "can connect : " + ret);

        return ret;
    }

}
