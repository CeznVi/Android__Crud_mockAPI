package com.itstep.myrestapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itstep.myrestapp.adapters.UserAdapter;
import com.itstep.myrestapp.models.UserModel;
import com.itstep.myrestapp.repositories.DataLoadCallback;
import com.itstep.myrestapp.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button addButton = findViewById(R.id.user_btn_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateUserActivity.class);
                startActivity(intent);
            }
        });


        userAdapter = new UserAdapter(new ArrayList<>());
        recyclerView.setAdapter(userAdapter);

        loadData();
    }

    private void loadData() {
        UserRepository userRepository = UserRepository.getInstance();
        userRepository.getAll(new DataLoadCallback<UserModel>() {
            @Override
            public void onDataLoaded(List<UserModel> data) {
                userAdapter = new UserAdapter((ArrayList<UserModel>) data);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onError(Throwable t) {
                Toast.makeText(MainActivity.this, "Error loading data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}