package com.example.eventhub.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eventhub.Model.AdminParticipant;
import com.example.eventhub.Repository.AdminParticipantRepository;

import java.util.List;

public class AdminParticipantViewModel extends ViewModel {
    private final AdminParticipantRepository repo = new AdminParticipantRepository();
    private final MutableLiveData<List<AdminParticipant>> participants = new MutableLiveData<>();
    private final MutableLiveData<String> err = new MutableLiveData<>();

    public MutableLiveData<List<AdminParticipant>> getParticipants() { return participants; }
    public MutableLiveData<String> getErr() { return err; }

    public void load(int maSK) {
        repo.loadParticipants(maSK, participants, err);
    }

    public void updateStatus(int maSK, int maTK, int trangThai, String lyDo) {
        repo.updateStatus(maSK, maTK, trangThai, lyDo, () -> repo.loadParticipants(maSK, participants, err), err);
    }
}
