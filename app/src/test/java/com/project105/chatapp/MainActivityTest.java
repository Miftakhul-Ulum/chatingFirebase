package com.project105.chatapp;

import static org.junit.Assert.*;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.junit.Test;

public class MainActivityTest {
    DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference();

    @Test
    public  void getDatabaseReference() {

    }
}