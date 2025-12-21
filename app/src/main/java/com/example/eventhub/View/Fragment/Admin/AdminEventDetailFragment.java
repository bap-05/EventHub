package com.example.eventhub.View.Fragment.Admin;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.eventhub.Model.SuKien;
import com.example.eventhub.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AdminEventDetailFragment extends Fragment {

    public AdminEventDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_event_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView btnBack = view.findViewById(R.id.btn_back);
        AppCompatButton btnEdit = view.findViewById(R.id.btn_edit_event);
        ImageView posterView = view.findViewById(R.id.img_event_poster);
        TextView tvTitle = view.findViewById(R.id.tv_event_title);
        TextView tvTime = view.findViewById(R.id.tv_detail_time);
        TextView tvLocation = view.findViewById(R.id.tv_detail_location);
        TextView tvStatus = view.findViewById(R.id.tv_detail_status);
        TextView tvPoints = view.findViewById(R.id.tv_detail_points);
        TextView tvDescription = view.findViewById(R.id.tv_detail_description);

        btnBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        btnEdit.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.nav_admin_create_task));

        SuKien event = null;
        Bundle args = getArguments();
        if (args != null) {
            Object raw = args.getSerializable("event");
            if (raw instanceof SuKien) {
                event = (SuKien) raw;
            }
        }

        String title = event != null ? event.getTenSK() : "";
        String poster = event != null ? event.getPoster() : "";
        String rawStart = event != null ? event.getThoiGianBatDau() : "";
        String rawEnd = event != null ? event.getThoiGianKetThuc() : "";
        String location = event != null ? event.getDiaDiem() : "";
        String campus = event != null ? event.getCoSo() : "";
        String status = event != null ? event.getTrangThai() : "";
        int points = event != null ? event.getDiemCong() : 0;
        String description = event != null ? event.getMoTa() : "";

        if (TextUtils.isEmpty(title)) {
            title = "Chua cap nhat";
        }
        tvTitle.setText(title);

        tvTime.setText(buildTimeText(rawStart, rawEnd));
        tvLocation.setText(buildLocationText(location, campus));
        tvStatus.setText(buildStatusText(status));
        tvPoints.setText(points + " diem hoat dong");
        tvDescription.setText(TextUtils.isEmpty(description) ? "Chua cap nhat" : description);

        if (!TextUtils.isEmpty(poster)) {
            Glide.with(posterView.getContext())
                    .load(poster)
                    .placeholder(R.drawable.postersukien)
                    .error(R.drawable.postersukien)
                    .into(posterView);
        } else {
            posterView.setImageResource(R.drawable.postersukien);
        }
    }

    private String buildTimeText(String rawStart, String rawEnd) {
        String start = formatDateTime(rawStart);
        String end = formatDateTime(rawEnd);
        if (!TextUtils.isEmpty(start) && !TextUtils.isEmpty(end)) {
            return "Thoi gian: " + start + " - " + end;
        }
        if (!TextUtils.isEmpty(start)) {
            return "Thoi gian: " + start;
        }
        return "Thoi gian: Chua cap nhat";
    }

    private String buildLocationText(String location, String campus) {
        String loc = location == null ? "" : location.trim();
        String cs = campus == null ? "" : campus.trim();
        if (!TextUtils.isEmpty(loc) && !TextUtils.isEmpty(cs)) {
            return "Dia diem: " + loc + " - " + cs;
        }
        if (!TextUtils.isEmpty(loc)) {
            return "Dia diem: " + loc;
        }
        if (!TextUtils.isEmpty(cs)) {
            return "Dia diem: " + cs;
        }
        return "Dia diem: Chua cap nhat";
    }

    private String buildStatusText(String status) {
        if (TextUtils.isEmpty(status)) {
            return "Trang thai: Chua cap nhat";
        }
        return "Trang thai: " + status;
    }

    private String formatDateTime(String raw) {
        if (TextUtils.isEmpty(raw)) return "";
        try {
            SimpleDateFormat src = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date d = src.parse(raw);
            if (d == null) return raw;
            return new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault()).format(d);
        } catch (ParseException e) {
            return raw;
        }
    }
}
