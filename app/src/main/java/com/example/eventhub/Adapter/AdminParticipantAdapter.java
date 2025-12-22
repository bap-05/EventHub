package com.example.eventhub.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventhub.Model.AdminParticipant;
import com.example.eventhub.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminParticipantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnParticipantAction {
        void onSelect(AdminParticipant p);
        void onApprove(AdminParticipant p);
        void onReject(AdminParticipant p);
    }

    private static final int TYPE_VIEW = 0;
    private static final int TYPE_REVIEW = 1;

    private List<AdminParticipant> data = new ArrayList<>();
    private boolean reviewMode = false;
    private final OnParticipantAction listener;

    public AdminParticipantAdapter(OnParticipantAction listener) {
        this.listener = listener;
    }

    public void setData(List<AdminParticipant> list, boolean allowApprove) {
        this.data = list != null ? list : new ArrayList<>();
        this.reviewMode = allowApprove;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return reviewMode ? TYPE_REVIEW : TYPE_VIEW;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_REVIEW) {
            View v = inflater.inflate(R.layout.item_student_list, parent, false);
            return new ReviewVH(v);
        } else {
            View v = inflater.inflate(R.layout.item_admin_participant, parent, false);
            return new ViewVH(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AdminParticipant p = data.get(position);
        if (holder instanceof ViewVH) {
            ((ViewVH) holder).bind(p);
        } else if (holder instanceof ReviewVH) {
            ((ReviewVH) holder).bind(p);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class ViewVH extends RecyclerView.ViewHolder {
        CircleImageView imgAvatar;
        TextView tvName, tvMasv, tvKhoaLop, tvAddressStatus, tvStatus;

        ViewVH(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img_avatar);
            tvName = itemView.findViewById(R.id.tv_name);
            tvMasv = itemView.findViewById(R.id.tv_masv);
            tvKhoaLop = itemView.findViewById(R.id.tv_khoa_lop);
            tvAddressStatus = itemView.findViewById(R.id.tv_address_status);
            tvStatus = itemView.findViewById(R.id.tv_status);
        }

        void bind(AdminParticipant p) {
            tvName.setText(p.getHoTen());
            tvMasv.setText(p.getMaSV());
            tvKhoaLop.setText(p.getKhoa() + " - " + p.getLop());
            String diaChi = p.getDiaDiemMinhChung();
            tvAddressStatus.setText(diaChi != null && !diaChi.isEmpty() ? diaChi : "Chua co dia chi minh chung");
            tvStatus.setText(statusText(p.getTrangThaiMinhChung(), false));
            Glide.with(itemView.getContext())
                    .load(p.getAVT())
                    .placeholder(R.drawable.avatar)
                    .error(R.drawable.avatar)
                    .into(imgAvatar);
            itemView.setOnClickListener(v -> {
                if (listener != null) listener.onSelect(p);
            });
        }
    }

    private class ReviewVH extends RecyclerView.ViewHolder {
        CircleImageView imgAvatar;
        TextView tvName, tvMasv, tvStatus;
        ImageView btnApprove, btnReject;

        ReviewVH(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img_avatar);
            tvName = itemView.findViewById(R.id.txt_student_name);
            tvMasv = itemView.findViewById(R.id.txt_student_id);
            tvStatus = itemView.findViewById(R.id.txt_status);
            btnApprove = itemView.findViewById(R.id.btn_approve);
            btnReject = itemView.findViewById(R.id.btn_reject);
        }

        void bind(AdminParticipant p) {
            tvName.setText(p.getHoTen());
            tvMasv.setText("MSSV: " + p.getMaSV());
            Glide.with(itemView.getContext())
                    .load(p.getAVT())
                    .placeholder(R.drawable.avatar)
                    .error(R.drawable.avatar)
                    .into(imgAvatar);

            boolean hasMatchedAddress = matchAddress(p.getDiaDiemMinhChung(), p.getDiaDiemSK());
            int st = p.getTrangThaiMinhChung();

            if (st == 2 || st == 3) {
                btnApprove.setVisibility(View.GONE);
                btnReject.setVisibility(View.GONE);
                tvStatus.setVisibility(View.VISIBLE);
                tvStatus.setBackgroundResource(st == 2 ? R.drawable.bg_status_green : R.drawable.bg_status_red);
                tvStatus.setText(st == 2 ? "Da duyet" : "Tu choi");
                tvStatus.setOnClickListener(v -> {
                    btnApprove.setVisibility(View.VISIBLE);
                    btnReject.setVisibility(View.VISIBLE);
                    tvStatus.setVisibility(View.GONE);
                });
            } else {
                tvStatus.setVisibility(View.VISIBLE);
                btnApprove.setVisibility(View.VISIBLE);
                btnReject.setVisibility(View.VISIBLE);
                tvStatus.setBackgroundResource(hasMatchedAddress ? R.drawable.bg_status_green : R.drawable.bg_status_red);
                tvStatus.setText(hasMatchedAddress ? "Dia chi phu hop" : "Dia chi chua phu hop");
                tvStatus.setOnClickListener(null);
            }

            btnApprove.setOnClickListener(v -> {
                if (listener != null) listener.onApprove(p);
            });
            btnReject.setOnClickListener(v -> {
                if (listener != null) listener.onReject(p);
            });

            itemView.setOnClickListener(v -> {
                if (listener != null) listener.onSelect(p);
            });

        }

        private boolean matchAddress(String proof, String expected) {
            if (proof == null || proof.trim().isEmpty()) return false;
            if (expected == null || expected.trim().isEmpty()) return false;
            return proof.trim().equalsIgnoreCase(expected.trim());
        }
    }

    private String statusText(int st, boolean allowApprove) {
        if (!allowApprove) return "Xem thong tin";
        switch (st) {
            case 0: return "Chua check-in";
            case 1: return "Cho duyet";
            case 2: return "Da duyet";
            case 3: return "Tu choi";
            default: return "Khong xac dinh";
        }
    }
}
