package com.itstep.myrestapp.repositories;

import com.itstep.myrestapp.clients.MockApiClient;
import com.itstep.myrestapp.models.UserModel;
import com.itstep.myrestapp.services.UserApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository implements RepositoryInterface<UserModel>
{
    ArrayList<UserModel> data;
    UserApiService apiService;
    private static UserRepository instance;
    private UserRepository(){
        data = new ArrayList<>();
        apiService = MockApiClient.getInstance().create(UserApiService.class);
    }
    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public void getAll(DataLoadCallback<UserModel> callback) {
        Call<ArrayList<UserModel>> call = apiService.getAll();
        call.enqueue(new Callback<ArrayList<UserModel>>() {
            @Override
            public void onResponse(Call<ArrayList<UserModel>> call, Response<ArrayList<UserModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.addAll(response.body());
                    callback.onDataLoaded(data);
                } else {
                    callback.onError(new Exception("Response not successful or body is null"));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserModel>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    @Override
    public UserModel create(UserModel newModel) {
        data.add(newModel);
        Call<UserModel> call = apiService.create(newModel);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()) {
                    UserModel createdUser = response.body();
                    System.out.println("User created: " + createdUser);
                } else {
                    System.out.println("Error creating user: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                System.out.println("Request error: " + t.getMessage());
            }
        });
        return newModel;
    }

    @Override
    public UserModel createModel() {
        return new UserModel();
    }

    public ArrayList<UserModel> getDataFromRepo() {
        return data;
    }

    public int getSize() {
        return data.size();
    }

    public void delete(UserModel user, DataUpdateCallback callback) {
        Call<Void> call = apiService.delete(user.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    data.remove(user);
                    callback.onDataUpdated();
                } else {
                    callback.onError(new Exception("Error deleting user: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onError((Exception) t);
            }
        });
    }


    public interface DataUpdateCallback {
        void onDataUpdated();
        void onError(Exception e);
    }

}
