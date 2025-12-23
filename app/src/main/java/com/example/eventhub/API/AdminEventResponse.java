package com.example.eventhub.API;

import com.example.eventhub.Model.SuKien;
import java.util.List;

public class AdminEventResponse {
    private List<SuKien> upcoming;
    private List<SuKien> ongoing;
    private List<SuKien> done;

    public List<SuKien> getUpcoming() { return upcoming; }
    public List<SuKien> getOngoing() { return ongoing; }
    public List<SuKien> getDone() { return done; }
}
