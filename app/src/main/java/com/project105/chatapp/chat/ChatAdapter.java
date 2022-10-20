package com.project105.chatapp.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project105.chatapp.DataMemory;
import com.project105.chatapp.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private List<ChatList> chatLists;
    private final Context context;
    private String userMobile;

    public ChatAdapter(List<ChatList> chatLists, Context context) {
        this.chatLists = chatLists;
        this.context = context;
        this.userMobile = DataMemory.getData(context);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, null));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChatList listChat = chatLists.get(position);

        if (listChat.getMobile().equals(userMobile)){
            holder.myMsgLayout.setVisibility(View.VISIBLE);
            holder.opoLayout.setVisibility(View.GONE);

            holder.myMesege.setText(listChat.getMessege());
            holder.myTime.setText(listChat.getDate()+""+listChat.getTime());
        }else {
            holder.myMsgLayout.setVisibility(View.GONE);
            holder.opoLayout.setVisibility(View.VISIBLE);

            holder.opoMesege.setText(listChat.getMessege());
            holder.opoTime.setText(listChat.getDate()+""+listChat.getTime());
        }

    }

    @Override
    public int getItemCount() {
        return chatLists.size();
    }

    public void updateChatList(List<ChatList> chatLists){
        this.chatLists = chatLists;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        LinearLayout opoLayout, myMsgLayout;
        EditText opoMesege, opoTime;
        EditText myMesege, myTime;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            opoLayout = itemView.findViewById(R.id.opoLayout);
            myMsgLayout = itemView.findViewById(R.id.myMsgLayout);
            opoMesege = itemView.findViewById(R.id.textOpoMsg);
            opoTime = itemView.findViewById(R.id.textOpoTime);
            myMesege = itemView.findViewById(R.id.textMyMsg);
            myTime = itemView.findViewById(R.id.textMyMsgTime);
        }
    }
}
