package com.example.eventhub.View.Fragment.KhachHang;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventhub.Adapter.SuKienSapToiAdapter;
import com.example.eventhub.Model.SuKien;
import com.example.eventhub.R;
import com.example.eventhub.ViewModel.SuKienViewModel;

public class SearchFragment extends Fragment {
    private SuKienViewModel suKienViewModel;
    private String currentTags = "";
    private String currentTime = "";
    private String currentKeyword = "";

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        Button filterButton = view.findViewById(R.id.button);
        ImageView img_back = view.findViewById(R.id.img_search_back);
        EditText searchInput = view.findViewById(R.id.editTextText);
        RecyclerView rcvSearch = view.findViewById(R.id.rcv_search);

        rcvSearch.setLayoutManager(new LinearLayoutManager(requireContext()));
        suKienViewModel = new ViewModelProvider(this).get(SuKienViewModel.class);
        suKienViewModel.getListSKSearch().observe(getViewLifecycleOwner(), suKiens -> {
            SuKienSapToiAdapter adapter = new SuKienSapToiAdapter(suKiens);
            rcvSearch.setAdapter(adapter);
            adapter.setListener(new SuKienSapToiAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(SuKien sk) {
                    suKienViewModel.setSk(sk);
                    Navigation.findNavController(view).navigate(R.id.chiTietSuKienFragment);
                }

                @Override
                public void onCancel(SuKien sk) {

                }
            });

        });

        getParentFragmentManager().setFragmentResultListener("search_filter", this, (requestKey, bundle) -> {
            currentTags = bundle.getString("tags", "");
            currentTime = bundle.getString("time", "");
            runSearch();
        });

        img_back.setOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                currentKeyword = s.toString().trim();
                runSearch();
            }
        });

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterBottomSheetFragment filterSheet = new FilterBottomSheetFragment();

                filterSheet.show(getParentFragmentManager(), "FilterBottomSheet");
            }
        });

        runSearch();
        return view;
    }

    private void runSearch() {
        if (suKienViewModel != null) {
            suKienViewModel.searchSuKien(currentKeyword, currentTags, currentTime);
        }
    }
}
