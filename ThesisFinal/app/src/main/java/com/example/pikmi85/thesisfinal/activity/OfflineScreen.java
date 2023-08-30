package com.example.pikmi85.thesisfinal.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pikmi85.thesisfinal.R;
import com.example.pikmi85.thesisfinal.globalVariables;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.kosalgeek.android.caching.FileCacher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class OfflineScreen extends AppCompatActivity {
    LinearLayout.LayoutParams params;
    LinearLayout rl;
    ListView listview;
    TextView nopdf;
    ArrayList<Object> list = new ArrayList<>();
    ArrayAdapter<Object> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_screen);
        isInternetOn();
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,10,0,0);
        rl = (LinearLayout) findViewById(R.id.fragment_photos_layout);
        nopdf = (TextView) findViewById(R.id.txt_nogroup);
        listview = (ListView) findViewById(R.id.listview);
        adapter = new ArrayAdapter<Object>(OfflineScreen.this, R.layout.listview_text2, list);
        listview.setAdapter(adapter);
        FileCacher cacher = new FileCacher(OfflineScreen.this, "topics");
        if(cacher.hasCache()){
        try {
            String topic = cacher.readCache().toString();
            StringBuilder sb = new StringBuilder(topic);
            sb.deleteCharAt(0);
            sb.deleteCharAt(sb.length()-1);
            list.add(sb.toString());
            adapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        }}else{
            nopdf.setVisibility(View.VISIBLE);
        }
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String pickedTopic = String.valueOf(parent.getItemAtPosition(position));
                Intent intent = new Intent(OfflineScreen.this, ReviewLessonsScreen.class);
                Bundle extras = new Bundle();
                extras.putString("pickedTopic",pickedTopic);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
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
            Toast.makeText(this, " Internet Connection Established", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(OfflineScreen.this,LoginScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return true;
        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
            return false;
        }
        return false;
    }
    public void onBackPressed() {
        if(isInternetOn()){

            Intent intent = new Intent(OfflineScreen.this,MainScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);

        }else{
            new AlertDialog.Builder(this)
                    .setTitle("Information")
                    .setMessage("Connection Not Yet Established")
                    .setPositiveButton(android.R.string.yes, null).create().show();
        }
    }
}
