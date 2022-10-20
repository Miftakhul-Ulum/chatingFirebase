package com.project105.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project105.chatapp.pesan.AdapterPesan;
import com.project105.chatapp.pesan.ListPesan;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private final List<ListPesan> pesanList = new ArrayList<>();

    private String nomor;
    private String nama;
    private String email;
    private String chatKey;
    private RecyclerView recyclerViewPesan;
    private AdapterPesan adapterPesan;

    private boolean dataSet = false;

    private String pessanTerkirim = "";
    private int pessanGagal = 0;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CircleImageView userProfile = findViewById(R.id.profileUser);
        recyclerViewPesan = findViewById(R.id.receicler);

//        get intent data from register clas
        nomor = getIntent().getStringExtra("Nomor");
        nama = getIntent().getStringExtra("Nama");
        email = getIntent().getStringExtra("Email");

        recyclerViewPesan.setHasFixedSize(true);
        recyclerViewPesan.setLayoutManager(new LinearLayoutManager(this));

        adapterPesan = new AdapterPesan(pesanList, MainActivity.this);
        recyclerViewPesan.setAdapter(adapterPesan);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

//        get profile pict from firebase database
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String profilPict = snapshot.child("Users").child(nomor).child("profile_pict").getValue(String.class);

                if (!profilPict.isEmpty()){
//                    set profile pic to circle image view
                    Picasso.get().load(profilPict).into(userProfile);
                }

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               pesanList.clear();
               pessanGagal = 0;
               pessanTerkirim = "";
               chatKey = "";

               for (DataSnapshot dataSnapshot : snapshot.child("Users").getChildren()){

                   final String getNomor = dataSnapshot.getKey();

                   dataSet = false;

                   if (!getNomor.equals(nomor)){
                       final String getnama = dataSnapshot.child("Nama").getValue(String.class);
                       final String getProfilPic = dataSnapshot.child("profile_pict").getValue(String.class);

                       databaseReference.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot snapshot) {
                               int getChatCount = (int)snapshot.getChildrenCount();
                               if (getChatCount > 0){
                                   for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                                       final String getKey = dataSnapshot1.getKey();
                                       chatKey = getKey;

                                       if (dataSnapshot1.hasChild("user_1")&& dataSnapshot1.hasChild("user_2")&& dataSnapshot1.hasChild("messeges")){
                                           final String getUserOne = dataSnapshot1.child("user_1").getValue(String.class);
                                           final String getUserTwo = dataSnapshot1.child("user_2").getValue(String.class);

                                           if ((getUserOne.equals(getNomor)&&getUserTwo.equals(nomor))|| (getUserOne.equals(nomor)&&getUserTwo.equals(getNomor))){

                                               for (DataSnapshot chatSnapshot : dataSnapshot1.child("messeges").getChildren()){

                                                   final long getMessegeKey = Long.parseLong(chatSnapshot.getKey());
                                                   final long getPesanTerkirim = Long.parseLong(DataMemory.getPesanTerkirim(MainActivity.this, getKey));

                                                   pessanTerkirim = chatSnapshot.child("msg").getValue(String.class);
                                                   if (getMessegeKey>getPesanTerkirim){
                                                       pessanGagal++;
                                                   }
                                               }
                                           }
                                       }
                                   }
                               }
                               if (!dataSet){
                                   dataSet = true;
                                   ListPesan listPesan = new ListPesan(getnama, getNomor,pessanTerkirim,getProfilPic, pessanGagal, chatKey);
                                   pesanList.add(listPesan);
                                   adapterPesan.updateData(pesanList);
                               }

                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError error) {

                           }
                       });
                   }
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
    }
}