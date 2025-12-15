package com.example.eventhub.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.eventhub.R;

public class SearchFragment extends Fragment {
    public SearchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        Button filterButton = view.findViewById(R.id.button);

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