package com.example.eventhub.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eventhub.Model.AdminUpdateEventRequest;
import com.example.eventhub.Repository.AdminEventRepository;

public class AdminEditTaskViewModel extends ViewModel {
    private final AdminEventRepository repo = new AdminEventRepository();
    private final MutableLiveData<Boolean> updated = new MutableLiveData<>();
    private final MutableLiveData<String> err = new MutableLiveData<>();

    public MutableLiveData<Boolean> getUpdated() { return updated; }
    public MutableLiveData<String> getErr() { return err; }

    public void updateEvent(AdminUpdateEventRequest request) {
        updated.setValue(false);
        repo.updateEvent(request, updated, err);
    }

    public void clearUpdated() {
        updated.setValue(false);
    }
}
