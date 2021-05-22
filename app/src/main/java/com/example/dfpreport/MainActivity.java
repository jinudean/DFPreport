package com.example.dfpreport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SetProfileHandler {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Profile currentProfile;
    private long pressedTime;

    String [] permission_list = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    String dir_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permission_list,0);
        } else {
            init();
        }
        RecyclerView profileRecyclerView = findViewById(R.id.profile_recyclerview);
        final ProfileAdapter profileAdapter = new ProfileAdapter(this, this);

        profileRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        profileRecyclerView.setAdapter(profileAdapter);
        MainViewModel mainViewModel = new ViewModelProvider((ViewModelStoreOwner) this).get(MainViewModel.class);
        mainViewModel.getAllProfiles().observe(this, new Observer<List<Profile>>() {
            @Override
            public void onChanged(List<Profile> profiles) {
                profileAdapter.setProfiles(profiles);
            }
        });

    }

    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }
    public void init(){
        String temp_path = getExternalFilesDir(null).getAbsolutePath();
        dir_path = temp_path + "/Android/data/" + getPackageName();
        File file = new File(dir_path);
        if(file.exists() == false){
            file.mkdir();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for(int a1 : grantResults){
            if(a1 == PackageManager.PERMISSION_DENIED){
                return;
            }
        }
        init();
    }

    public void goToSignUp(View view){
        Intent intent = new Intent(MainActivity.this, SignUp.class);
        startActivity(intent);
    }

    public void goToDash(View view) {
        if (currentProfile != null) {
            Intent intent = new Intent(MainActivity.this, AppActivity.class);
            intent.putExtra(String.valueOf(R.string.userProfile), this.currentProfile);
            finish();
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, getString(R.string.please_select_insp), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void setCurrentProfile(Profile currentProfile) {
        this.currentProfile = currentProfile;
    }

    @Override
    public void noProfileCheck(boolean profileCheck) {
        TextView userTitle = findViewById(R.id.users);
        Button button = findViewById(R.id.get_started_button);
        if (profileCheck) {
            userTitle.setVisibility(View.GONE);
            button.setVisibility(View.GONE);
        } else {
            userTitle.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
        }
    }
}