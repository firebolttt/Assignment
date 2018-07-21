package com.firebolt.assignment.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;


import com.bumptech.glide.Glide;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;


/**
 * Created by ashwinir on 10-03-2017.
 */
public class ImageUtil {

    private static String TAG = ImageUtil.class.getSimpleName();
    private Context context = null;
    private String strURL = "";


    public ImageUtil(Context context, View view, String strURL) {

        this.context = context;
        this.strURL = strURL;
        //       imageLoader = ImageLoader.getInstance();

        setImage(view);
    }


    public void setImage(final View view) {
        try {
            Glide.with(context)
                    .load(strURL)
                    .crossFade()
                    .into((ImageView)view);



        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}