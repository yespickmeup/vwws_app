package com.vwwsapp.adapters;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vwwsapp.activities.R;
import com.vwwsapp.model.MeterReaderAssignments;

import java.util.List;

public class ConsumersAdapter  extends RecyclerView.Adapter<ConsumersAdapter.ConsumerViewHolder>{

    private List<MeterReaderAssignments> listConsumers;

    public ConsumersAdapter(List<MeterReaderAssignments> listUsers) {
        this.listConsumers = listUsers;
    }

    @Override
    public ConsumerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_consumer, parent, false);

        return new ConsumerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ConsumersAdapter.ConsumerViewHolder holder, int position) {
        holder.textViewMeterNo.setText(listConsumers.get(position).getMeter_no());
        holder.textViewConsumerName.setText(listConsumers.get(position).getCustomer_name());

    }

    @Override
    public int getItemCount() {
        Log.v(UsersRecyclerAdapter.class.getSimpleName(),""+listConsumers.size());
        return listConsumers.size();
    }


    /**
     * ViewHolder class
     */
    public class ConsumerViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewMeterNo;
        public AppCompatTextView textViewConsumerName;


        public ConsumerViewHolder(View view) {
            super(view);
            textViewMeterNo = (AppCompatTextView) view.findViewById(R.id.meter_no);
            textViewConsumerName = (AppCompatTextView) view.findViewById(R.id.customer_name);

        }
    }


}
