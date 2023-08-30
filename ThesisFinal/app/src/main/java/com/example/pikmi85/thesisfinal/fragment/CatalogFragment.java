package com.example.pikmi85.thesisfinal.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pikmi85.thesisfinal.R;
import com.example.pikmi85.thesisfinal.activity.LoginScreen;
import com.example.pikmi85.thesisfinal.activity.MainScreen;
import com.example.pikmi85.thesisfinal.activity.OfflineScreen;
import com.example.pikmi85.thesisfinal.activity.ReviewLessonsScreen;
import com.example.pikmi85.thesisfinal.activity.SelectedCourseScreen;
import com.example.pikmi85.thesisfinal.classes;
import com.example.pikmi85.thesisfinal.globalVariables;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CatalogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CatalogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CatalogFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;
    private Button btnjoingroup;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String grpCode;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dref, dref2;
    String curr_email, id;
    int cnt = 0;
    int l;
    List<String> classes;
    List<String> groupcodes;
    List<String> activities;
    private FirebaseAuth auth;
    TableLayout assessment;
    TextView norecord;
   private OnFragmentInteractionListener mListener;
    public CatalogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CatalogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CatalogFragment newInstance(String param1, String param2) {
        CatalogFragment fragment = new CatalogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        isInternetOn();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        curr_email = auth.getCurrentUser().getEmail();
        classes = new ArrayList<String>();
        activities = new ArrayList<String>();
        globalVariables.clearClasses();
        classes.clear();
        activities.clear();
        getgroup();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_catalog, container, false);
        btnjoingroup = (Button) view.findViewById(R.id.btn_joingroup);
        norecord = (TextView) view.findViewById(R.id.norecords);
        assessment = (TableLayout) view.findViewById(R.id.recordstable);
        btnjoingroup.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_joingroup:
                //Start activity one
                dref = FirebaseDatabase.getInstance().getReference();
                groupcodes = new ArrayList<String>();
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Enter Group Code");
                final EditText input = new EditText(v.getContext());
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
                                        AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                                        builder2.setMessage("Group Code Not Found")
                                                .setCancelable(false)
                                                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                })
                                                //Set your icon here
                                                .setTitle("Warning")
                                                .setIcon(R.mipmap.ic_logo);
                                        AlertDialog alert = builder2.create();
                                        alert.show();//showing the dialog
                                    }else{
                                        for(int k = 0 ; k<= groupcodes.size()-1 ; k++){
                                            if (Objects.equals(groupcodes.get(k), grpCode)) {
                                                id = globalVariables.getstudentKey();
                                                // progressBar.setVisibility(View.VISIBLE);
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
                break;
            // Do this for all buttons.
        }
    }
    private void getyourclasses(int index){
            final int indexx = index;
            dref = database.getReference().child("classes").child(globalVariables.getclasses(indexx));
            dref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                    getrecords(globalVariables.getclasses(indexx), newPost.get("grp_teacher_key"));
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }
    private void getgroup(){
            classes.clear();
            dref = database.getReference().child("classes");
            dref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        for(DataSnapshot d : dataSnapshot.getChildren()){
                            classes.add(d.getKey());
                        }
                    }
                    for(int k = 0 ; k<=classes.size()-1 ; k++){
                        getyourgroup(classes.get(k));
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


    }
    private void getrecords(String groupcode, Object teacherkey){
        final String curr_group = groupcode;
        final String curr_teacherkey = teacherkey.toString();
        dref = database.getReference().child("assessment").child(curr_teacherkey).child(curr_group).child(globalVariables.getstudentKey());
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot d : dataSnapshot.getChildren()){
                        activities.add(d.getKey());
                    }
                }
                for(int k = 0 ; k<=activities.size()-1 ; k++){
                    getyourassessment(activities.get(k), curr_teacherkey,curr_group);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    private void getyourassessment(String activities, String curr_teacherkey, String curr_group){
        final String group = curr_group;
        final String teacherkey = curr_teacherkey;
        final String curr_activities = activities;
        dref = database.getReference().child("assessment").child(teacherkey).child(group).child(globalVariables.getstudentKey()).child(curr_activities);
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    norecord.setVisibility(View.GONE);
                    Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                    Object score = newPost.get("score");
                    Object timestamp = newPost.get("date_quiztaken");
                    TableRow tr = new TableRow(getContext());
                    tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.FILL_PARENT));
                    TextView youractivity = new TextView(getContext());
                    if (Objects.equals(curr_activities, "quiz")) {
                        youractivity.setText("Chapter Quiz");
                    } else {
                        youractivity.setText(curr_activities);
                    }
                    youractivity.setLayoutParams(new TableRow.LayoutParams(1));
                    youractivity.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            getResources().getDimension(R.dimen.records2));
                    youractivity.setTextColor(getResources().getColor(R.color.black));
                    TextView yourscore = new TextView(getContext());
                    yourscore.setText(score.toString());
                    yourscore.setLayoutParams(new TableRow.LayoutParams(1));
                    yourscore.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            getResources().getDimension(R.dimen.records2));
                    yourscore.setTextColor(getResources().getColor(R.color.black));
                    TextView yourtimestamp = new TextView(getContext());
                    yourtimestamp.setText(timestamp.toString());
                    yourtimestamp.setLayoutParams(new TableRow.LayoutParams(1));
                    yourtimestamp.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            getResources().getDimension(R.dimen.records2));
                    yourtimestamp.setTextColor(getResources().getColor(R.color.black));
                    tr.addView(youractivity);
                    tr.addView(yourscore);
                    tr.addView(yourtimestamp);
                    assessment.addView(tr, new TableLayout.LayoutParams(0, TableLayout.LayoutParams.FILL_PARENT, 1));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public final boolean isInternetOn() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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
            Toast.makeText(getActivity(), " No internet connection", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getActivity(),OfflineScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return false;
        }
        return false;
    }
    private void getyourgroup(String curr_group){
        final String group = curr_group;
        dref = database.getReference().child("classes").child(group).child("member");
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        globalVariables.setcurrclassmemcnt(dataSnapshot.getChildrenCount());
                        if (Objects.equals(globalVariables.getstudentKey(), d.getKey())) {
                            btnjoingroup.setVisibility(view.INVISIBLE);
                            globalVariables.setclasses(group);
                            for(l = 0 ; l<=globalVariables.getclassessize()-1 ; l++) {
                                getyourclasses(l);
                            }}}}}
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
        globalVariables.clearClasses();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        classes.clear();
        activities.clear();
    }
    @Override
    public void onResume() {
        super.onResume();
        mListener = null;
        classes.clear();
        activities.clear();
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
                        if(Integer.parseInt(grpcapacity) > dataSnapshot.getChildrenCount()){
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
                            String date_requested = simpleDateFormat.format(new Date());
                            classes group = new classes(id, grpCode, date_requested);
                            dref.child("classes").child(grpCode).child("pending_members").child(id).setValue(group);
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Wait For Approval");
                            builder.setMessage("Please wait for your teacher's approval");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    getActivity().recreate();
                                }
                            });
                            builder.show();
                        }else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Warning");
                            builder.setMessage("Group is already full");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    getActivity().recreate();
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
