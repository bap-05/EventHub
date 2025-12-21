package com.example.eventhub.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventhub.Model.AdminParticipant;
import com.example.eventhub.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminParticipantAdapter extends RecyclerView.Adapter<AdminParticipantAdapter.ParticipantVH> {

    private List<AdminParticipant> data = new ArrayList<>();
    private boolean allowApprove = false;

    public void setData(List<AdminParticipant> list, boolean allowApprove) {
        this.data = list != null ? list : new ArrayList<>();
        this.allowApprove = allowApprove;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ParticipantVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_participant, parent, false);
        return new ParticipantVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipantVH holder, int position) {
        AdminParticipant p = data.get(position);
        holder.tvName.setText(p.getHoTen());
        holder.tvMasv.setText(p.getMaSV());
        holder.tvKhoaLop.setText(p.getKhoa() + " - " + p.getLop());
        String diaChi = p.getDiaDiemMinhChung();
        holder.tvAddressStatus.setText(diaChi != null && !diaChi.isEmpty() ? diaChi : "Chưa có địa chỉ minh chứng");
        holder.tvStatus.setText(statusText(p.getTrangThaiMinhChung(), allowApprove));
        Glide.with(holder.itemView.getContext())
                .load(p.getAVT())
                .placeholder(R.drawable.avatar)
                .error(R.drawable.avatar)
                .into(holder.imgAvatar);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private String statusText(int st, boolean allowApprove) {
        if (!allowApprove) return "Xem thông tin";
        switch (st) {
            case 0: return "Chưa check-in";
            case 1: return "Chờ duyệt";
            case 2: return "Đã duyệt";
            case 3: return "Từ chối";
            default: return "Không xác định";
        }
    }

    static class ParticipantVH extends RecyclerView.ViewHolder {
        CircleImageView imgAvatar;
        TextView tvName, tvMasv, tvKhoaLop, tvAddressStatus, tvStatus;
        ParticipantVH(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img_avatar);
            tvName = itemView.findViewById(R.id.tv_name);
            tvMasv = itemView.findViewById(R.id.tv_masv);
            tvKhoaLop = itemView.findViewById(R.id.tv_khoa_lop);
            tvAddressStatus = itemView.findViewById(R.id.tv_address_status);
            tvStatus = itemView.findViewById(R.id.tv_status);
        }
    }
}
