package com.example.eventhub.View.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.eventhub.R;

public class SearchFragment extends Fragment {
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        Button filterButton = view.findViewById(R.id.button);
         ImageView img_back = view.findViewById(R.id.img_search_back);
         img_back.setOnClickListener(v -> {
             requireActivity().getOnBackPressedDispatcher().onBackPressed();
         });
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterBottomSheetFragment filterSheet = new FilterBottomSheetFragment();

                filterSheet.show(getParentFragmentManager(), "FilterBottomSheet");
            }
        });

        return view;
    }

}