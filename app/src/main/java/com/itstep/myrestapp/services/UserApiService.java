package com.itstep.myrestapp.services;

import com.itstep.myrestapp.models.UserModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApiService {

    @POST("/vpu111/users")
    Call<UserModel> create(@Body UserModel newUser);

    @GET("/vpu111/users")
    Call<ArrayList<UserModel>> getAll();

    @DELETE("/vpu111/users/{id}")
    Call<Void> delete(@Path("id") int id);

}
