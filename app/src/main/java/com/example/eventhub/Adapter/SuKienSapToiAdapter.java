package com.example.eventhub.Adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventhub.Model.SuKien;
import com.example.eventhub.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SuKienSapToiAdapter extends RecyclerView.Adapter<SuKienSapToiAdapter.SuKienViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(SuKien sk);
        void onCancel(SuKien sk);
    }

    private final List<SuKien> lSuKien;
    private final Set<Integer> registeredIds = new HashSet<>();
    private OnItemClickListener listener;

    public SuKienSapToiAdapter(List<SuKien> lSuKien) {
        this.lSuKien = lSuKien != null ? lSuKien : new ArrayList<>();
        setHasStableIds(true);
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setRegistered(Set<Integer> ids) {
        registeredIds.clear();
        if (ids != null) registeredIds.addAll(ids);
        notifyDataSetChanged();
    }

    public void update(List<SuKien> items) {
        lSuKien.clear();
        if (items != null) lSuKien.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position >= 0 && position < lSuKien.size() ? lSuKien.get(position).getMaSK() : RecyclerView.NO_ID;
    }

    @NonNull
    @Override
    public SuKienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sukien, parent, false);
        return new SuKienViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SuKienViewHolder holder, int position) {
        SuKien sk = lSuKien.get(position);
        holder.bind(sk);
    }

    @Override
    public int getItemCount() {
        return lSuKien.size();
    }

    private void applyRegisterState(Button btn, SuKien sk) {
        if (btn == null || sk == null) return;
        boolean isRegistered = registeredIds.contains(sk.getMaSK());
        if (isRegistered) {
            btn.setText("Đã đăng ký");
            btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#32B768")));
            btn.setTextColor(Color.WHITE);
        } else {
            btn.setText("Chi tiết");
            btn.setBackgroundTintList(null);
            btn.setBackgroundResource(R.drawable.custom_btn_danhmuc);
            btn.setTextColor(Color.WHITE);
        }
    }

    public class SuKienViewHolder extends RecyclerView.ViewHolder {
        ImageView img_poster;
        TextView txt_noidung, txt_thoigian, txt_coso;
        Button btn_thamgia;

        public SuKienViewHolder(@NonNull View itemView) {
            super(itemView);
            img_poster = itemView.findViewById(R.id.img_saptoi_poster);
            txt_noidung = itemView.findViewById(R.id.txt_saptoi_noidung);
            txt_thoigian = itemView.findViewById(R.id.txt_saptoi_thoigian);
            txt_coso = itemView.findViewById(R.id.txt_saptoi_coso);
            btn_thamgia = itemView.findViewById(R.id.btn_saptoi_thamgia);
        }

        public void bind(SuKien sk) {
            if (sk == null) return;
            txt_noidung.setText(sk.getTenSK());
            txt_thoigian.setText(sk.getThoiGianBatDau());
            txt_coso.setText(sk.getCoSo());
            if (sk.getPoster() != null && !sk.getPoster().isEmpty()) {
                Picasso.get().load(sk.getPoster()).into(img_poster);
            } else {
                img_poster.setImageResource(R.drawable.poster);
            }
            applyRegisterState(btn_thamgia, sk);

            itemView.setOnClickListener(v -> {
                if (listener != null) listener.onItemClick(sk);
            });
            btn_thamgia.setOnClickListener(v -> {
                if (listener != null) listener.onItemClick(sk);
            });
        }
    }
}
