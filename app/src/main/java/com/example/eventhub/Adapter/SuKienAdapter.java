package com.example.eventhub.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventhub.Model.SuKien;
import com.example.eventhub.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SuKienAdapter extends RecyclerView.Adapter<SuKienAdapter.SuKienViewHolder> {
    private List<SuKien> lSuKien;
    private OnclickItemListener listener;
    private final Set<Integer> registeredIds = new HashSet<>();

    public interface OnclickItemListener{
        void onClickItem(SuKien sk);
    }

    public void setListener(OnclickItemListener listener) {
        this.listener = listener;
    }

    public SuKienAdapter(List<SuKien> lSuKien) {
        this.lSuKien = lSuKien;
    }

    @NonNull
    @Override
    public SuKienAdapter.SuKienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sapdienra,parent,false);
        return new SuKienViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SuKienAdapter.SuKienViewHolder holder, int position) {
        SuKien sk = lSuKien.get(position);
        if(sk==null)
            return;
        holder.txt_noidung.setText(sk.getTenSK());
        holder.txt_thoigian.setText(sk.getThoiGianBatDau());
        Glide.with(holder.itemView).load(sk.getAVT1()).into(holder.img_avt1);
        Glide.with(holder.itemView).load(sk.getAVT2()).into(holder.img_avt2);
        Glide.with(holder.itemView).load(sk.getAVT3()).into(holder.img_avt3);
        Glide.with(holder.itemView).load(sk.getAVT4()).into(holder.img_avt4);
        Glide.with(holder.itemView).load(sk.getPoster()).into(holder.img_poster);
        applyRegisterState(holder.btn_thamgia, sk.getMaSK());
    }

    @Override
    public int getItemCount() {
        if(lSuKien !=null)
            return lSuKien.size();
        return 0;
    }

    public void setRegisteredIds(Set<Integer> ids) {
        registeredIds.clear();
        if (ids != null) registeredIds.addAll(ids);
        notifyDataSetChanged();
    }

    private void applyRegisterState(Button btn, int maSK) {
        if (btn == null) return;
        if (registeredIds.contains(maSK)) {
            btn.setText("Đã đăng ký");
            btn.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#32B768")));
            btn.setTextColor(android.graphics.Color.WHITE);
        } else {
            btn.setText("Tham gia");
            btn.setBackgroundTintList(null);
            btn.setBackgroundResource(R.drawable.custom_btn_danhmuc);
            btn.setTextColor(android.graphics.Color.WHITE);
        }
    }

    public class SuKienViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_poster;
        private TextView txt_noidung, txt_thoigian;
        private Button btn_thamgia;
        private ShapeableImageView img_avt1, img_avt2, img_avt3, img_avt4;
        public SuKienViewHolder(@NonNull View itemView) {
            super(itemView);
            img_poster = itemView.findViewById(R.id.img_sapdienra_poster);
            txt_noidung = itemView.findViewById(R.id.txt_sapdienra_noidung);
            txt_thoigian = itemView.findViewById(R.id.txt_sapdienra_thoigian);
            btn_thamgia = itemView.findViewById(R.id.btn_sapdienra_thamgia);
            img_avt1 = itemView.findViewById(R.id.img_home_avt1);
            img_avt2 = itemView.findViewById(R.id.img_home_avt2);
            img_avt3 = itemView.findViewById(R.id.img_home_avt3);
            img_avt4 = itemView.findViewById(R.id.img_home_avt4);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null)
                    {
                        int vitri = getBindingAdapterPosition();
                        if(vitri != RecyclerView.NO_POSITION)
                            listener.onClickItem(lSuKien.get(vitri));
                    }
                }
            });
        }
    }
}
