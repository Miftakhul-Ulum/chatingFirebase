package com.project105.chatapp.pesan;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project105.chatapp.R;
import com.project105.chatapp.chat.ChatActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterPesan extends RecyclerView.Adapter<AdapterPesan.MyViewHolder > {
    private List<ListPesan> listPesans;
    private Context context;

    public AdapterPesan(List<ListPesan> listPesans, Context context) {
        this.listPesans = listPesans;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pesan,parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ListPesan listP = listPesans.get(position);
        if (!listP.getProfilImage().isEmpty()){
            Picasso.get().load(listP.getProfilImage()).into(holder.imageProfil);
        }
        holder.namaLengkap.setText(listP.getNama());
        holder.pesanTerkirim.setText(listP.getPesanTerkirim());
        if (listP.getPesanGagal()==0){
            holder.pesanGagal.setVisibility(View.GONE);
            holder.pesanTerkirim.setTextColor(Color.parseColor("#959595"));
        }else {
            holder.pesanGagal.setVisibility(View.VISIBLE);
            holder.pesanGagal.setText(listP.getPesanGagal()+"");
            holder.pesanTerkirim.setTextColor(context.getResources().getColor(R.color.blue));
        }
        holder.linearItemPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("Nomor", listP.getNomor());
                intent.putExtra("Nama",listP.getNama());
                intent.putExtra("profile_pict", listP.getProfilImage());
                intent.putExtra("chat_key", listP.getChatKey());
                context.startActivity(intent);

            }
        });
    }
    public void updateData(List<ListPesan> listPesans){
        this.listPesans = listPesans;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listPesans.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView imageProfil;
        private TextView namaLengkap;
        private TextView pesanTerkirim;
        private TextView pesanGagal;
        private LinearLayout linearItemPesan;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProfil = itemView.findViewById(R.id.image_profile);
            namaLengkap = itemView.findViewById(R.id.text_nama);
            pesanTerkirim = itemView.findViewById(R.id.pesan_terkirim);
            pesanGagal = itemView.findViewById(R.id.pesan_gagal);
            linearItemPesan = itemView.findViewById(R.id.linear_item_pesan);

        }
    }
}
