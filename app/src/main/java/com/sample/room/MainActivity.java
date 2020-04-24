package com.sample.room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sample.room.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UserProfileDatabase db;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = Room.databaseBuilder(this, UserProfileDatabase.class, "userprofile").allowMainThreadQueries().build();

        fetchUserProfileList();
    }

    private void fetchUserProfileList() {
        List<UserProfile> userProfileList = db.getUserProfileDao().getAll();
        String userListText = "사용자 목록";
        for (UserProfile userProfile : userProfileList) {
            userListText += "\n" + userProfile.id + ", " + userProfile.name + ", " + userProfile.phone + ", " + userProfile.address;
        }

        binding.userList.setText(userListText);
    }

    public void addUserProfile(View view) {
        UserProfile userProfile = new UserProfile();
        userProfile.name = binding.name.getText().toString();
        userProfile.phone = binding.phone.getText().toString();
        userProfile.address = binding.address.getText().toString();
        db.getUserProfileDao().insert(userProfile);
        fetchUserProfileList();
    }


}
