package com.example.eventhub.View.Fragment.KhachHang;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.eventhub.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

// 1. Kế thừa từ BottomSheetDialogFragment
public class FilterBottomSheetFragment extends BottomSheetDialogFragment {

    // 2. Liên kết với file layout XML của bạn
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Sử dụng file layout bạn vừa sửa ở Bước 1
        return inflater.inflate(R.layout.fragment_filter_bottom_sheet, container, false);
    }

    // 3. Nơi viết code xử lý sự kiện (giống như onCreate trong Activity)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Tìm các nút
        Button btnConfirm = view.findViewById(R.id.oke);
        Button btnReset = view.findViewById(R.id.reset);


        // 1. Nhóm Thể thao
        ToggleButton sportToggle = view.findViewById(R.id.sport);
        ImageView sportIcon = view.findViewById(R.id.ic_sport);
        TextView sportText = view.findViewById(R.id.txt_sport);

        sportToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Khi được chọn
                    sportIcon.setColorFilter(Color.WHITE); // Đổi icon sang màu trắng
                    sportText.setTextColor(Color.parseColor("#00A2FF")); // Đổi chữ sang màu xanh
                } else {
                    // Khi không được chọn
                    sportIcon.setColorFilter(null); // Bỏ filter màu (trở về màu gốc)
                    sportText.setTextColor(Color.BLACK); // Đổi chữ về màu đen (hoặc xám)
                }
            }
        });

        // 2. Nhóm Âm nhạc
        ToggleButton musicToggle = view.findViewById(R.id.music);
        ImageView musicIcon = view.findViewById(R.id.ic_music);
        TextView musicText = view.findViewById(R.id.txt_music);

        musicToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    musicIcon.setColorFilter(Color.WHITE);
                    musicText.setTextColor(Color.parseColor("#00A2FF"));
                } else {
                    musicIcon.setColorFilter(null);
                    musicText.setTextColor(Color.BLACK);
                }
            }
        });

        // 3. Nhóm Chủ nhật xanh (cnx)
        ToggleButton cnxToggle = view.findViewById(R.id.cnx);
        ImageView cnxIcon = view.findViewById(R.id.ic_cnx);
        // ID "textView5" là ID của chữ "Chủ nhật xanh" trong XML của bạn
        TextView cnxText = view.findViewById(R.id.txt_cnx);

        cnxToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cnxIcon.setColorFilter(Color.WHITE);
                    cnxText.setTextColor(Color.parseColor("#00A2FF"));
                } else {
                    cnxIcon.setColorFilter(null);
                    cnxText.setTextColor(Color.BLACK);
                }
            }
        });

        btnConfirm.setOnClickListener(v -> {
            RadioGroup timeGroup = view.findViewById(R.id.radio_group_time);
            StringBuilder tags = new StringBuilder();
            if (sportToggle.isChecked()) {
                tags.append(sportText.getText().toString());
            }
            if (musicToggle.isChecked()) {
                if (tags.length() > 0) {
                    tags.append(",");
                }
                tags.append(musicText.getText().toString());
            }
            if (cnxToggle.isChecked()) {
                if (tags.length() > 0) {
                    tags.append(",");
                }
                tags.append(cnxText.getText().toString());
            }

            String timeValue = "";
            int checkedId = timeGroup.getCheckedRadioButtonId();
            if (checkedId == R.id.today) {
                timeValue = "today";
            } else if (checkedId == R.id.tmr) {
                timeValue = "tomorrow";
            } else if (checkedId == R.id.week) {
                timeValue = "week";
            }

            Bundle result = new Bundle();
            result.putString("tags", tags.toString());
            result.putString("time", timeValue);
            getParentFragmentManager().setFragmentResult("search_filter", result);
            dismiss();
        });

        btnReset.setOnClickListener(v -> {
            RadioGroup timeGroup = view.findViewById(R.id.radio_group_time);
            sportToggle.setChecked(false);
            musicToggle.setChecked(false);
            cnxToggle.setChecked(false);
            timeGroup.clearCheck();

            Bundle result = new Bundle();
            result.putString("tags", "");
            result.putString("time", "");
            getParentFragmentManager().setFragmentResult("search_filter", result);
            dismiss();
        });

    }
}
