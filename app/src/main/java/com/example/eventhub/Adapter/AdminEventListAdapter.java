package com.example.eventhub.Adapter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventhub.Model.AdminEventItem;
import com.example.eventhub.R;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.List;

public class AdminEventListAdapter extends RecyclerView.Adapter<AdminEventListAdapter.EventViewHolder> {

    public interface OnEventClick {
        void onItemClick(AdminEventItem item);
        void onAvatarClick(AdminEventItem item);
        void onEditClick(AdminEventItem item);
        void onMarkDone(AdminEventItem item);
    }

    private List<AdminEventItem> data;
    private final OnEventClick listener;

    public AdminEventListAdapter(List<AdminEventItem> data, OnEventClick listener) {
        this.data = data;
        this.listener = listener;
    }

    public void updateData(List<AdminEventItem> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_event, parent, false);
        return new EventViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        AdminEventItem item = data.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvStatus.setText(item.getStatusText());
        holder.tvPriority.setText(item.getPriorityText());
        holder.tvStart.setText(item.getStartDate());
        holder.tvEnd.setText(item.getEndDate());
        holder.progressBar.setLayoutParams(
                new LinearLayout.LayoutParams(0, (int) (4 * holder.itemView.getResources().getDisplayMetrics().density), 1f));
        holder.statusIcon.setImageResource(item.getStatusIconRes());
        holder.priorityIcon.setImageResource(item.getPriorityIconRes());
        holder.startIcon.setImageResource(item.getCalendarIconRes());
        holder.endIcon.setImageResource(item.getCalendarIconRes());

        holder.tvCount.setText(item.getSoLuongDaDangKy() + "/" + item.getSoLuongGioiHan());
        loadAvatar(holder.avatar1, item.getAvt1());
        loadAvatar(holder.avatar2, item.getAvt2());
        loadAvatar(holder.avatar3, item.getAvt3());

        holder.tvEventId.setVisibility(View.GONE);
        boolean isDone = item.isShowDone();
        boolean isOngoing = item.getStatus() == AdminEventItem.Status.ONGOING;

        holder.qrIcon.setVisibility(isOngoing ? View.VISIBLE : View.GONE);
        holder.editIcon.setVisibility(isDone ? View.GONE : View.VISIBLE);

        if (isOngoing) {
            holder.qrIcon.setImageResource(R.drawable.qr);
            holder.qrIcon.setOnClickListener(v -> showQrDialog(v, item.getEventId()));
        } else {
            holder.qrIcon.setOnClickListener(null);
        }

        if (isDone) {
            holder.editIcon.setOnClickListener(null);
        } else {
            holder.editIcon.setImageResource(R.drawable.ic_edit);
            holder.editIcon.setOnClickListener(v -> {
                if (listener == null) return;
                if (isOngoing) {
                    listener.onMarkDone(item);
                } else {
                    listener.onEditClick(item);
                }
            });
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(item);
        });
        holder.avatarContainer.setOnClickListener(v -> {
            if (listener != null) listener.onAvatarClick(item);
        });
        holder.tvCount.setOnClickListener(v -> {
            if (listener != null) listener.onAvatarClick(item);
        });
    }

    private void loadAvatar(ImageView view, String url) {
        if (url == null || url.isEmpty()) {
            view.setImageDrawable(new ColorDrawable(Color.TRANSPARENT));
            return;
        }
        Glide.with(view.getContext())
                .load(url)
                .placeholder(new ColorDrawable(Color.TRANSPARENT))
                .error(new ColorDrawable(Color.TRANSPARENT))
                .into(view);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvStatus, tvPriority, tvStart, tvEnd, tvEventId, tvCount;
        ImageView statusIcon, priorityIcon, startIcon, endIcon, avatar1, avatar2, avatar3, qrIcon, editIcon;
        View progressBar;
        View avatarContainer;

        EventViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_event_title);
            tvStatus = itemView.findViewById(R.id.tv_event_status);
            tvPriority = itemView.findViewById(R.id.tv_event_priority);
            tvStart = itemView.findViewById(R.id.tv_event_start);
            tvEnd = itemView.findViewById(R.id.tv_event_end);
            tvEventId = itemView.findViewById(R.id.tv_event_id);
            tvCount = itemView.findViewById(R.id.tv_event_count);
            statusIcon = itemView.findViewById(R.id.icon_status);
            priorityIcon = itemView.findViewById(R.id.icon_priority);
            startIcon = itemView.findViewById(R.id.icon_start);
            endIcon = itemView.findViewById(R.id.icon_end);
            qrIcon = itemView.findViewById(R.id.icon_qr);
            editIcon = itemView.findViewById(R.id.icon_edit);
            avatar1 = itemView.findViewById(R.id.avatar1);
            avatar2 = itemView.findViewById(R.id.avatar2);
            avatar3 = itemView.findViewById(R.id.avatar3);
            progressBar = itemView.findViewById(R.id.progress_line);
            avatarContainer = itemView.findViewById(R.id.avatar_container);
        }
    }

    private void showQrDialog(View view, int eventId) {
        try {
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.encodeBitmap(String.valueOf(eventId),
                    com.google.zxing.BarcodeFormat.QR_CODE, 500, 500);
            ImageView qrView = new ImageView(view.getContext());
            qrView.setImageBitmap(bitmap);
            int padding = (int) (16 * view.getResources().getDisplayMetrics().density);
            qrView.setPadding(padding, padding, padding, padding);
            new AlertDialog.Builder(view.getContext())
                    .setTitle("Ma QR su kien")
                    .setView(qrView)
                    .setPositiveButton("Dong", null)
                    .show();
        } catch (Exception ignored) {
            // ignore
        }
    }
}

