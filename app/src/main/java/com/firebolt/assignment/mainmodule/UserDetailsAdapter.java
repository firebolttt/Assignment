package com.firebolt.assignment.mainmodule;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebolt.assignment.App;
import com.firebolt.assignment.R;
import com.firebolt.assignment.databinding.UserDetailsChildBinding;
import com.firebolt.assignment.models.UserDetails;
import com.firebolt.assignment.utils.ImageUtil;


import java.util.ArrayList;


public class UserDetailsAdapter extends RecyclerView.Adapter<UserDetailsAdapter.ViewHolder> {

    private ArrayList<UserDetails> usersList;

    public UserDetailsAdapter(ArrayList<UserDetails> usersList) {
        this.usersList = usersList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_details_child, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final UserDetails bean = usersList.get(position);
        if (null != bean) {
            holder.setData(bean);
        }

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public void updateList(ArrayList<UserDetails> users) {
        if (users.size() != 0) {
            this.usersList.addAll(users);
            notifyDataSetChanged();
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        UserDetailsChildBinding binding;

        ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public void setData(UserDetails data) {
            binding.txtName.setText(String.format("%s %s", data.getfName(), data.getlName()));
            new ImageUtil(App.getContext(), binding.img, data.getImgUrl());
        }
    }
}
