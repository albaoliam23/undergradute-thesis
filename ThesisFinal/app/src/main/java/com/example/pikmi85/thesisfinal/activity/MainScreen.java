package com.example.pikmi85.thesisfinal.activity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pikmi85.thesisfinal.R;

import com.example.pikmi85.thesisfinal.classes;
import com.example.pikmi85.thesisfinal.fragment.CatalogFragment;
import com.example.pikmi85.thesisfinal.fragment.ProfileFragment;
import com.example.pikmi85.thesisfinal.fragment.NotificationsFragment;
import com.example.pikmi85.thesisfinal.fragment.PhotosFragment;
import com.example.pikmi85.thesisfinal.globalVariables;
import com.example.pikmi85.thesisfinal.other.CircleTransform;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kosalgeek.android.caching.FileCacheManager;
import com.kosalgeek.android.caching.FileCacher;
import com.kosalgeek.android.caching.MainActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class MainScreen extends AppCompatActivity {
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dref, dref2;
    private static final String urlNavHeaderBg = "https://firebasestorage.googleapis.com/v0/b/ezbio-98be1.appspot.com/o/forapp.png?alt=media&token=16045b5f-89f8-4a03-af20-44eac3829ea8";
    private static final String urlProfileImg = "https://firebasestorage.googleapis.com/v0/b/ezbio-98be1.appspot.com/o/user_default.png?alt=media&token=5057ffd1-dd08-4f84-a13e-499fc71a8a05";
    public static int navItemIndex = 0;
    private static final String TAG_DASHBOARD = "dashboard";
    private static final String TAG_CLASSES = "classes";
    public static String CURRENT_TAG = TAG_DASHBOARD;
    String curr_email, id, grpCode;
    String meronna;
    List<String> groupcodes;
    int cnt = 0;
    private String[] activityTitles;
    private FirebaseAuth auth;
    private boolean shouldLoadCatFragOnBackPress = true;
    private Handler mHandler;
    public static final String Name = "nameKey";
    public static final String Email = "emailKey";
    FileCacher<String> name = new FileCacher<String>(MainScreen.this, "name");
    FileCacher<Object> email = new FileCacher<Object>(MainScreen.this, "email");
    FileCacher<String> studkey = new FileCacher<String>(MainScreen.this, "studkey");
    FileCacheManager manager = new FileCacheManager(MainScreen.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(MainScreen.this, LoginScreen.class));
            finish();
        }
        isInternetOn();
        setContentView(R.layout.activity_main_screen);
        curr_email = auth.getCurrentUser().getEmail();
        try {
            globalVariables.setstudentKey(studkey.readCache());
        } catch (IOException e) {
            e.printStackTrace();
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mHandler = new Handler();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);
        loadNavHeader();
        setUpNavigationView();
        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_DASHBOARD;
            loadHomeFragment();
        }
    }
    private void loadNavHeader() {
        // name, website
        if(name.hasCache()){
            try{
                txtName.setText(name.readCache());
            }catch(IOException e){
                e.printStackTrace();
            }

        }
        if(email.hasCache()){
            try{
                Object email2 = email.readCache();
                txtWebsite.setText(email2.toString());
            }catch(IOException e){
                e.printStackTrace();
            }

        }
        // loading header background image
        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);
        // Loading profile image
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);
        // showing dot next to notifications label
        navigationView.getMenu().getItem(0).setActionView(R.layout.menu_dot);
    }
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();
        // set toolbar title
        setToolbarTitle();
        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };
        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
        // show or hide the fab button
        //Closing drawer on item click
        drawer.closeDrawers();
        // refresh toolbar menu
        invalidateOptionsMenu();
    }
    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                CatalogFragment catalogFragment = new CatalogFragment();
                return catalogFragment;
            case 1:
                // photos
                PhotosFragment photosFragment = new PhotosFragment();
                return photosFragment;
            case 2:
                // notifications fragment
                NotificationsFragment notificationsFragment = new NotificationsFragment();
                return notificationsFragment;
            case 3:
                // movies fragment
                ProfileFragment profileFragment = new ProfileFragment();
                return profileFragment;
            default:
                return new CatalogFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }
    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }
    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_catalog:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_DASHBOARD;
                        break;
                    case R.id.nav_classes:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_CLASSES;
                        break;
                    case R.id.nav_joingroup:
                        dref = FirebaseDatabase.getInstance().getReference();
                        groupcodes = new ArrayList<String>();
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainScreen.this);
                        builder.setTitle("Enter Group Code");
                        final EditText input = new EditText(MainScreen.this);

                        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                        input.setInputType(InputType.TYPE_CLASS_TEXT);
                        builder.setView(input);
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                grpCode = input.getText().toString();
                                dref2 = database.getReference().child("classes");
                                dref2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.exists()){
                                            for(DataSnapshot d : dataSnapshot.getChildren()){
                                                groupcodes.add(d.getKey());
                                                if (Objects.equals(d.getKey(), grpCode)) {
                                                    cnt++;
                                                }
                                            }
                                            if(cnt == 0){
                                                AlertDialog.Builder builder2 = new AlertDialog.Builder(MainScreen.this);
                                                builder2.setMessage("Group Code Not Found")
                                                        .setCancelable(false)
                                                        .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                dialog.cancel();
                                                            }
                                                        })
                                                        //Set your icon here
                                                        .setTitle("Information")
                                                        .setIcon(R.mipmap.ic_logo);
                                                AlertDialog alert = builder2.create();
                                                alert.show();//showing the dialog
                                            }else{
                                                for(int k = 0 ; k<= groupcodes.size()-1 ; k++){
                                                    if (Objects.equals(groupcodes.get(k), grpCode)) {
                                                        id = globalVariables.getstudentKey();
                                                        joingroup(id, grpCode);
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_about_us:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MainScreen.this, AboutUsActivity.class));
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_privacy_policy:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MainScreen.this, PrivacyPolicyActivity.class));
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_log_out:
                        // launch new intent instead of loading fragment
                        auth.signOut();
                        Intent intent = new Intent(MainScreen.this,LoginScreen.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        drawer.closeDrawers();
                        if(manager.hasCacheFiles()){
                            manager.deleteAllCaches();
                        }
                        startActivity(intent);
                        finish();
                        return true;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };
        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);
        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }
        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadCatFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_DASHBOARD;
                loadHomeFragment();
                return;
            }
        }
        super.onBackPressed();
    }
    @Override
    protected void onStop(){
        super.onStop();
    }
    @Override
    protected void onResume(){
        super.onResume();
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(MainScreen.this, LoginScreen.class));
            finish();
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
    public final boolean isInternetOn() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
            // if connected with internet
            return true;
        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
            Toast.makeText(this, " No internet connection", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainScreen.this,OfflineScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return false;
        }
        return false;
    }
    private void joingroup(final String id, final String grpCode) {
        dref2 = database.getReference().child("classes").child(grpCode);
        dref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                final String grpcapacity = newPost.get("grp_capacity").toString();
                dref2 = database.getReference().child("classes").child(grpCode).child("member");
                dref2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.e("Check", ""+dataSnapshot.getChildrenCount());
                        if(Integer.parseInt(grpcapacity) > dataSnapshot.getChildrenCount()){
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
                            String date_requested = simpleDateFormat.format(new Date());
                            classes group = new classes(id, grpCode, date_requested);
                            dref.child("classes").child(grpCode).child("pending_members").child(id).setValue(group);
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainScreen.this);
                            builder.setTitle("Wait For Approval");
                            builder.setMessage("Please wait for your teacher's approval");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    recreate();
                                }
                            });
                            builder.show();
                        }else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainScreen.this);
                            builder.setTitle("Information");
                            builder.setMessage("Group is already full");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    recreate();
                                }
                            });
                            builder.show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}