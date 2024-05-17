package com.itstep.myrestapp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.itstep.myrestapp.R;
import com.itstep.myrestapp.models.UserModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private ArrayList<UserModel> userList;

    public UserAdapter(ArrayList<UserModel> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserModel user = userList.get(position);
        holder.nameTextView.setText(user.getName());
        holder.createdAtTextView.setText(user.getCreatedAt().toString());
        Log.d("UserAdapter", "Loading image URL: " + user.getAvatar());

        if(user.getAvatar().isEmpty()) return;
        Picasso.get().load(user.getAvatar()).into(holder.avatarImageView, new Callback() {
            @Override
            public void onSuccess() {
                // Image loaded successfully
                Log.d("UserAdapter", "Image loaded successfully for user: " + user.getName());
            }

            @Override
            public void onError(Exception e) {
                // Error loading image
                Log.e("UserAdapter", "Error loading image for user: " + user.getName(), e);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarImageView;
        TextView nameTextView;
        TextView createdAtTextView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImageView = itemView.findViewById(R.id.avatarImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            createdAtTextView = itemView.findViewById(R.id.createdAtTextView);
        }
    }

    private void loadImageFromUrl(String url, ImageView imageView) {
        Picasso.get().load(url).into(imageView);
    }

}
