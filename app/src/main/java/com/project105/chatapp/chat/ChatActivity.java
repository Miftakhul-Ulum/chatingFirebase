package com.project105.chatapp.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project105.chatapp.DataMemory;
import com.project105.chatapp.R;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    private final List<ChatList> chatLists = new ArrayList<>();
    String getUserNomor = "";
    private String chatKey;
    private RecyclerView recyclerViewChat;
    private ChatAdapter chatAdapter;
    private boolean loadingFirstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        final ImageView btnBack = findViewById(R.id.btn_back);
        final ImageView sendBtn = findViewById(R.id.send_chat);
        final CircleImageView userProfil = findViewById(R.id.profileChat);
        final EditText editChat = findViewById(R.id.edit_chat);
        final TextView namaUserChat = findViewById(R.id.tex_namechat);
        recyclerViewChat = findViewById(R.id.receicler_list_chat);

//        get data from meseges adapter class

        final String getnama = getIntent().getStringExtra("Nama");
        final String getProfil = getIntent().getStringExtra("profile_pict");
        final String getNomor = getIntent().getStringExtra("Nomor");
        chatKey = getIntent().getStringExtra("chat_key");


        namaUserChat.setText(getnama);
        Picasso.get().load(getProfil).into(userProfil);

        recyclerViewChat.setHasFixedSize(true);
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(ChatActivity.this));

        chatAdapter = new ChatAdapter(chatLists,ChatActivity.this);
        recyclerViewChat.setAdapter(chatAdapter);

//        get user nomor dari memori
        getUserNomor = DataMemory.getData(ChatActivity.this);


        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (chatKey.isEmpty()) {
                    chatKey = "1";
                    if (snapshot.hasChild("chat")) {
                        chatKey = String.valueOf(snapshot.child("chat").getChildrenCount() + 1);
                    }
                }
                if (snapshot.hasChild("chat")){

                    if (snapshot.child("chat").child(chatKey).hasChild("messeges")){
                        chatLists.clear();

                        for (DataSnapshot mesegeSnapshot : snapshot.child("chat").child(chatKey).child("messeges").getChildren()){

                            if (mesegeSnapshot.hasChild("msg")&& mesegeSnapshot.hasChild("Nomor")){

                                final String msgTimesTamps = mesegeSnapshot.getKey();
                                final String getMobile = mesegeSnapshot.child("Nomor").getValue(String.class);
                                final String getMsg = mesegeSnapshot.child("msg").getValue(String.class);

                                Timestamp timestamp = new Timestamp(Long.parseLong(msgTimesTamps));
                                Date date = new Date(timestamp.getTime());
                                SimpleDateFormat dateFormat =  new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());

                                ChatList chatList = new ChatList(getMobile, getnama, getMsg, dateFormat.format(date), timeFormat.format(date));
                                chatLists.add(chatList);

                                if (loadingFirstTime ||  Long.parseLong(msgTimesTamps)>Long.parseLong(DataMemory.getPesanTerkirim(ChatActivity.this, chatKey))){

                                    loadingFirstTime = false;

                                    DataMemory.savePesanTerkirim(msgTimesTamps, chatKey, ChatActivity.this);
                                    chatAdapter.updateChatList(chatLists);

                                    recyclerViewChat.scrollToPosition(chatLists.size()-1);
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String getTxtchat = editChat.getText().toString();

//                GET CURRENT TIMESTAMP
                final String currentTime = String.valueOf(System.currentTimeMillis()).substring(0, 10);


                databaseReference.child("chat").child(chatKey).child("user_1").setValue(getUserNomor);
                databaseReference.child("chat").child(chatKey).child("user_2").setValue(getNomor);
                databaseReference.child("chat").child(chatKey).child("messeges").child(currentTime).child("msg").setValue(getTxtchat);
                databaseReference.child("chat").child(chatKey).child("messeges").child(currentTime).child("Nomor").setValue(getUserNomor);

//                chat clear
                editChat.setText("");
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}