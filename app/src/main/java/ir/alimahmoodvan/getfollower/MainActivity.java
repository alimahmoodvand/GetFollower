package ir.alimahmoodvan.getfollower;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


import org.bson.Document;

import AsyncTask.MainAsync;
import I4A.I4A;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    private final String TAG = "MainActivityTAG";
    public I4A instagram;
    public CircleImageView profileImage;
    public TextView usernameText;
    public TextView nameText;
    public ProgressBar pbar;
    public TextView infoText;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton drawerButton;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);
        setupWindowAnimations();
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        profileImage = findViewById(R.id.profile_image);
        usernameText = findViewById(R.id.username_txt);
        infoText = findViewById(R.id.info_txt);
        nameText = findViewById(R.id.name_txt);
        pbar = findViewById(R.id.pbar);

        new MainAsync(this).execute("");
        drawerLayout =  findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle=
                new ActionBarDrawerToggle(this,
                        drawerLayout,
                        toolbar,
                        R.string.open_drawer,
                        R.string.close_drawer
                );
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
            super.onBackPressed();
    }
    private void setupWindowAnimations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.d(TAG, "setupWindowAnimations: ");
            Slide slide = new Slide();
            slide.setDuration(1000);
            getWindow().setExitTransition(slide);
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        String id=String.valueOf(item.getTitle());
        if(item.getItemId()==R.id.setting_menu){
            Intent i=new Intent(this,SettingActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slideltr, R.anim.slidertl);

        }else if(item.getItemId()==R.id.logout_menu){
//            Intent i=new Intent(this,SettingActivity.class);
//            startActivity(i);
//            overridePendingTransition(R.anim.slideltr, R.anim.slidertl);

        }
//        Toast.makeText(this,String.valueOf(id),Toast.LENGTH_SHORT).show();
        drawerLayout.closeDrawer(GravityCompat.START);
        super.onOptionsItemSelected(item);
        return true;
    }
}