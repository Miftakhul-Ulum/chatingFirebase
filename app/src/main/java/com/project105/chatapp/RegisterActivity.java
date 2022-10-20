package com.project105.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText eName = findViewById(R.id.tex_name);
        final  EditText eNumber = findViewById(R.id.tex_number);
        final EditText eEmail = findViewById(R.id.text_email);
        final AppCompatButton btnDaftar = findViewById(R.id.btn_daftar);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        if (!DataMemory.getData(this).isEmpty()){
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            intent.putExtra("Nomor", DataMemory.getData(this));
            intent.putExtra("Nama", DataMemory.getNama(this));
            intent.putExtra("Email", "");

            startActivity(intent);
            finish();
        }

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.show();

                final String nama = eName.getText().toString();
                final String number = eNumber.getText().toString();
                final String email = eEmail.getText().toString();

                if (nama.isEmpty()||number.isEmpty()||email.isEmpty()) {
                    Toast.makeText(RegisterActivity.this,"Kolom tidak boleh kosong", Toast.LENGTH_SHORT ).show();
                    progressDialog.dismiss();
                }else {
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            progressDialog.dismiss();
                            if (snapshot.child("Users").hasChild(number)){
                                Toast.makeText(RegisterActivity.this,"Number Already Exist", Toast.LENGTH_SHORT).show();
                            }else {
                                databaseReference.child("Users").child(number).child("Email").setValue(email);
                                databaseReference.child("Users").child(number).child("Nama").setValue(nama);
                                databaseReference.child("Users").child(number).child("profile_pict").setValue("");

//                                save nomor ke memory
                                DataMemory.saveData(number, RegisterActivity.this);
//                                save nama ke memory
                                DataMemory.saveNama(nama, RegisterActivity.this);

                                Toast.makeText(RegisterActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                intent.putExtra("Nomor", number);
                                intent.putExtra("Nama", nama);
                                intent.putExtra("Email", email);

                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });
    }
}