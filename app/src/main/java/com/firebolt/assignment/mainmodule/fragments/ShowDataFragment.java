package com.firebolt.assignment.mainmodule.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebolt.assignment.App;
import com.firebolt.assignment.R;
import com.firebolt.assignment.databinding.FragmentShowDataBinding;
import com.firebolt.assignment.mainmodule.MainViewModel;
import com.firebolt.assignment.mainmodule.UserDetailsAdapter;
import com.firebolt.assignment.models.ResponseBean;
import com.firebolt.assignment.models.UserDetails;
import com.firebolt.assignment.navigation.BaseFragment;
import com.firebolt.assignment.networking.APIConstants;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowDataFragment extends BaseFragment {
    FragmentShowDataBinding binding;
    private CompositeDisposable compositeDisposable;
    private MainViewModel viewModel;
    private UserDetailsAdapter adapter;

    public ShowDataFragment() {
        // Required empty public constructor
    }

    public static ShowDataFragment createInstance(Bundle bundle) {
        ShowDataFragment fragment = new ShowDataFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_show_data, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        compositeDisposable = new CompositeDisposable();
        viewModel = new MainViewModel(compositeDisposable);
        binding.setViewModel(viewModel);
        baseViewModel = viewModel;
        adapter = new UserDetailsAdapter(new ArrayList<UserDetails>());
        final RecyclerView usersListView = binding.commonList.findViewById(R.id.recyclerview);
        final GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        final LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        usersListView.setLayoutManager(mLayoutManager);
        usersListView.setAdapter(adapter);
        usersListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) { //for scrolling up
                    int totalItemCount = adapter.getItemCount();
                    int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                    if (viewModel.loadMoreData(totalItemCount, lastVisibleItem)) {
                        getData(totalItemCount);
                    }
                }
            }
        });
        //  swipeToRefresh();
    }

    private void swipeToRefresh() {
/*        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(1);
            }
        });*/
    }

    @Override
    public void onStart() {
        super.onStart();
        getData(1);
    }

    private void getData(int pageIndex) {

        viewModel.getData(new Consumer<ResponseBean>() {
            @Override
            public void accept(ResponseBean responseBean) {
//                binding.swipeRefresh.setRefreshing(false);
                adapter.updateList(responseBean.getUsers());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                Toast.makeText(App.getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
//                binding.swipeRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void releaseMemory() {
        super.releaseMemory();
        viewModel = null;
      //  adapter.onDetachedFromRecyclerView(binding.commonList);
        adapter = null;
        binding.setViewModel(null);
        binding = null;
    }
}
