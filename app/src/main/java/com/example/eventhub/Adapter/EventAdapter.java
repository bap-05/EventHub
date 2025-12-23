package com.example.eventhub.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventhub.Model.SuKien;
import com.example.eventhub.R;
import com.example.eventhub.ViewModel.SuKienViewModel;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    private List<SuKien> eventList;
    private OnClickItemListener listener;
    public interface OnClickItemListener{
        void onClickItem(SuKien suKien);
    }

    public void setListener(OnClickItemListener listener) {
        this.listener = listener;
    }

    public EventAdapter(List<SuKien>eventList){
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
        SuKien event=eventList.get(position);
        holder.txtTitle.setText(event.getTenSK());
        holder.txtDateTime.setText(event.getThoiGianBatDau());
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
            holder.txtPointsValue.setText("+" + event.getDiemCong());
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        int vitri = getBindingAdapterPosition();
                        if(vitri!=RecyclerView.NO_POSITION)
                            listener.onClickItem(eventList.get(vitri));
                    }
                }
            });
        }
    }
}
