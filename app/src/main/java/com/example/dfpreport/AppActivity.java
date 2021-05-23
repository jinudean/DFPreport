package com.example.dfpreport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.io.File;

public class AppActivity extends AppCompatActivity implements FragmentSwitcher {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private MainViewModel mainViewModel;
    private Profile currentProfile;
    private Report currentReport;
    String lName, fName, Enumber, Station, email, phone, photo;

    private final static String TAG = AppActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        Intent intent = getIntent();
        currentProfile = intent.getParcelableExtra(String.valueOf(R.string.userProfile));
        mainViewModel = new ViewModelProvider((ViewModelStoreOwner) this).get(MainViewModel.class);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle( this,drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.navigation_view);

        View header_view = navigationView.getHeaderView(0);


        String profile_pic_path;
        if (currentProfile.getPhoto() != null) {
            profile_pic_path = currentProfile.getPhoto();
            ImageView header_image = (ImageView)header_view.findViewById(R.id.profile_picture);
            File file = new File(profile_pic_path);
            Picasso.get().load(file).into(header_image);
        }


        //user_name & user_email

        TextView user_name = (TextView)header_view.findViewById(R.id.user_name);
        TextView user_email = (TextView)header_view.findViewById(R.id.user_email);

        String uName = currentProfile.getFullName();
        String uEmail = currentProfile.getEmail();

        user_name.setText(uName);
        user_email.setText(uEmail);

        user_name.setTextColor(Color.BLACK);
        user_email.setTextColor(Color.BLACK);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Fragment fragment = null;
                switch (id){
                    case R.id.profile:
                        fragment=new ProfileFragment(AppActivity.this);
                        loadFragment(fragment);
                        lName = currentProfile.getLastName();
                        fName = currentProfile.getFirstName();
                        Enumber = currentProfile.getEmployeeNumber();
                        Station = currentProfile.getStation();
                        email = currentProfile.getEmail();
                        phone = currentProfile.getPhone();
                        if (currentProfile != null) {
                            photo = currentProfile.getPhoto();
                        } else photo = null;


                        break;
                    case R.id.inspections:
                        fragment=new SweepCheckFragment(AppActivity.this);
                        loadFragment(fragment);
                        break;
                    case R.id.checklists:
                        fragment=new CheckListsFragment();
                        loadFragment(fragment);
                        break;
                    case R.id.pdfs:
                        fragment=new PDFsFragment(mainViewModel);
                        loadFragment(fragment);
                        break;
                    case R.id.logout:
                        //startActivity(new Intent(AppActivity.this, MainActivity.class));
                        Intent intent = new Intent(AppActivity.this, MainActivity.class);
                        startActivity(intent);
                    default:
                        return true;
                }
                return true;
            }
        });
        Fragment dashFragment = new DashFragment(this);
        loadFragment(dashFragment);
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
        fragmentTransaction.addToBackStack(null);

    }

    public Profile getProfile() {
        return currentProfile;
    }
    public void updateCurrentProfile(Profile profile) {
        this.currentProfile = profile;
        mainViewModel.updateProfileInfo(profile.getProfileId(), profile.getFirstName(), profile.getLastName(), profile.getEmail(), profile.getPhone(), profile.getStation(), profile.getEmployeeNumber(), profile.getPhoto());
    }
    public Report getCurrentReport() { return currentReport; }
    public void setCurrentReport(Report report) { this.currentReport = report; }

    @Override
    public MainViewModel getViewModel() {
        return mainViewModel;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}