package com.example.pikmi85.thesisfinal.fragment;


import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.LinearLayout;
import android.widget.ListView;

import android.widget.TextView;


import com.example.pikmi85.thesisfinal.R;
import com.example.pikmi85.thesisfinal.activity.SelectedCourseScreen;
import com.example.pikmi85.thesisfinal.globalVariables;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PhotosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PhotosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhotosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dref;
    String curr_email, curr_group, subject;
    Object grp_name;
    int size;
    Button[] btngroup;
    List<String> sections;
    List<String> fromdbsections;
    List<String> grpname;
    List<String> grpsubject;
    List<String> grpteacherkey;
    LinearLayout.LayoutParams params;
    LinearLayout rl;
    ListView listview;
    ArrayList<Object> list = new ArrayList<>();
    ArrayList<Object> classes;
    ArrayAdapter<Object> adapter;
    TextView nogroup;
    int y = 0;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirebaseAuth auth;
    private OnFragmentInteractionListener mListener;
    public PhotosFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhotosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PhotosFragment newInstance(String param1, String param2) {
        PhotosFragment fragment = new PhotosFragment();
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        curr_email = auth.getCurrentUser().getEmail();
        getgroup();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_photos, container, false);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,10,0,0);
        rl = (LinearLayout) rootView.findViewById(R.id.fragment_photos_layout);
        nogroup = (TextView) rootView.findViewById(R.id.txt_nogroup);
        listview = (ListView) rootView.findViewById(R.id.listview);
        adapter = new ArrayAdapter<Object>(getContext(), R.layout.listview_text, list);
        listview.setAdapter(adapter);
        sections = new ArrayList<String>();
        fromdbsections = new ArrayList<String>();
        grpname = new ArrayList<String>();
        grpsubject = new ArrayList<String>();
        grpteacherkey = new ArrayList<String>();
        classes = new ArrayList<>();
        btngroup = new Button[size+1];
        if(globalVariables.getclassessize() == 0){
            nogroup.setVisibility(View.VISIBLE);
        }else {
            dref = database.getReference().child("classes");
            dref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            fromdbsections.add(d.getKey());
                        }
                        size = fromdbsections.size()-1;
                        for (int k = 0; k <= globalVariables.getclassessize() - 1; k++) {
                            groupcheck(globalVariables.getclasses(k));
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            // Inflate the layout for this fragment
        }
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String pickedTopic = String.valueOf(parent.getItemAtPosition(position));
                for(int j = 0 ; j<=globalVariables.getclassessize()-1 ; j++){
                    dref = database.getReference().child("classes").child(globalVariables.getclasses(j));
                    dref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                            grp_name = newPost.get("grp_name");
                            if(Objects.equals(pickedTopic, grp_name.toString())){
                                globalVariables.setcurr_subject(newPost.get("grp_subject").toString());
                                globalVariables.setteacherkey(newPost.get("grp_teacher_key").toString());
                                globalVariables.setgrpCode(dataSnapshot.getKey());
                                Intent intent=new Intent(getActivity(), SelectedCourseScreen.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
        return rootView;
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        fromdbsections.clear();
        grpname.clear();
        grpteacherkey.clear();
        grpsubject.clear();
        sections.clear();
        //globalVariables.clearClasses();
    }
    private void getgroup(){
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
                    getyourgroup(classes.get(k).toString());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


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
                            Log.e("Check", d.getKey());
                        }}}}
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void groupcheck(String yourgroups){
        curr_group = yourgroups;
        y = 0;
        do{
            if(Objects.equals(fromdbsections.get(y), curr_group)){
                dref = database.getReference().child("classes").child(curr_group);
                dref.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if(Objects.equals(dataSnapshot.getKey(), "grp_name")){
                           subject = dataSnapshot.getValue().toString();
                           list.add(subject);
                            adapter.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }
                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }
                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            y++;
        }while(y<=size);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
