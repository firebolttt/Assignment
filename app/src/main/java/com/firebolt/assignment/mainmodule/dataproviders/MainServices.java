package com.firebolt.assignment.mainmodule.dataproviders;

import com.firebolt.assignment.models.ResponseBean;
import com.firebolt.assignment.models.UserDetails;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


import static com.firebolt.assignment.networking.APIConstants.GET_USERS;

public interface MainServices {
    @GET(GET_USERS)
    Observable<ResponseBean> getUsers(@Query("page")int pageIndex);

}
