package com.itstep.myrestapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.itstep.myrestapp.adapters.UserAdapter;
import com.itstep.myrestapp.models.UserModel;
import com.itstep.myrestapp.repositories.UserRepository;


public class CreateUserActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user); // Замените на свой layout-файл

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ((EditText) findViewById(R.id.usernameEditText)).getText().toString();
                String avatarUrl = ((EditText) findViewById(R.id.avatarUrlEditText)).getText().toString();

                // Создание нового пользователя
                UserModel user = new UserModel();
                user.setName(username);
                user.setAvatar(avatarUrl);

                // Сохранение пользователя в репозитории
                UserRepository.getInstance().create(user);

                // Обновление списка пользователей в адаптере
                updateUserList();

                // После сохранения пользователя вы можете выполнить какие-либо дополнительные действия, например, вернуться на предыдущий экран
                finish();
            }
        });



    }

    private void updateUserList() {
        // Получение ссылки на RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        // Получение адаптера, связанного с RecyclerView
        UserAdapter adapter = (UserAdapter) recyclerView.getAdapter();

        // Проверка, чтобы убедиться, что адаптер не пустой
        if (adapter != null) {
            // Обновление списка пользователей в адаптере
            adapter.notifyDataSetChanged();
        }
    }
}
