package com.example.eventhub.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eventhub.Model.AdminCreateEventRequest;
import com.example.eventhub.Repository.AdminEventRepository;

public class AdminCreateTaskViewModel extends ViewModel {
    private final AdminEventRepository repo = new AdminEventRepository();
    private final MutableLiveData<Boolean> created = new MutableLiveData<>();
    private final MutableLiveData<String> err = new MutableLiveData<>();

    public MutableLiveData<Boolean> getCreated() { return created; }
    public MutableLiveData<String> getErr() { return err; }

    public void createEvent(AdminCreateEventRequest request) {
        created.setValue(false);
        repo.createEvent(request, created, err);
    }

    public void clearCreated() {
        created.setValue(false);
    }
}
