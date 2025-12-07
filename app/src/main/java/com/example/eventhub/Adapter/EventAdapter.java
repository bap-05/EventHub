package com.example.eventhub.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eventhub.Model.SuKienSapToi;
import com.example.eventhub.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    private List<SuKienSapToi> eventList;
    public EventAdapter(List<SuKienSapToi>eventList){
        this.eventList=eventList;
    }

    @NonNull
    @Override
    public EventAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event,parent,false);
        return  new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.EventViewHolder holder, int position) {
        SuKienSapToi event=eventList.get(position);
        holder.txtTitle.setText(event.getTenSK());
        holder.txtDateTime.setText(event.getThoiGian());
        holder.txtLocation.setText(event.getCoSo());
        Glide.with(holder.itemView.getContext())
                .load(event.getPoster())
                .placeholder(R.drawable.avatar)
                .error(R.drawable.avatar)
                .into(holder.imgEvent);
        if("Đã tích lũy".equalsIgnoreCase(event.getTrangThai())){
            holder.btnDetails.setVisibility(View.GONE);
            holder.layoutAttended.setVisibility(View.VISIBLE);
            holder.txtStatus.setText(event.getTrangThai());
//            holder.txtPointsValue.setText("+"+event.getDiemTichLuy());
        }
        else {
            holder.layoutAttended.setVisibility(View.GONE);
            holder.btnDetails.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return eventList!=null ? eventList.size():0;
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtDateTime, txtLocation, txtStatus, txtPointsValue;
        ImageView imgEvent;
        Button btnDetails;
        LinearLayout layoutAttended;


        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtEventTitle);
            txtDateTime = itemView.findViewById(R.id.txtEventDate);
            txtLocation=itemView.findViewById(R.id.txtEventLocation);
            imgEvent=itemView.findViewById(R.id.imgEvent);
            btnDetails=itemView.findViewById(R.id.btnDetails);
            layoutAttended=itemView.findViewById(R.id.layoutAttended);
            txtStatus=itemView.findViewById(R.id.txtStatus);
            txtPointsValue=itemView.findViewById(R.id.txtPointsValue);

        }
    }
}
