package com.example.laundryappcustomer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder> {

    private ArrayList<Order> mOrderList;

    public class OrdersViewHolder extends RecyclerView.ViewHolder {

        public TextView txtOrderID;
        public TextView txtComment;
        public TextView txtStatus;
        public TextView txtWeight;
        public TextView txtPrice;

        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrderID = itemView.findViewById(R.id.order_id);
            txtComment = itemView.findViewById(R.id.order_Comment);
            txtWeight = itemView.findViewById(R.id.order_weight);
            txtStatus = itemView.findViewById(R.id.order_status);
            txtPrice = itemView.findViewById(R.id.order_total);
        }
    }

    public OrdersAdapter(ArrayList<Order> OrderArrayList) {
        mOrderList = OrderArrayList;
    }


    @NonNull
    @Override
    public OrdersAdapter.OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_item,parent,false);
        OrdersAdapter.OrdersViewHolder mvh = new OrdersAdapter.OrdersViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.OrdersViewHolder holder, int position){
        Order currentItem = mOrderList.get(position);
        holder.txtOrderID.setText(currentItem.getOrderID());
        holder.txtComment.setText(currentItem.getComments());
        holder.txtWeight.setText(currentItem.getWeight());
        holder.txtStatus.setText(currentItem.getStatus());
        holder.txtPrice.setText(currentItem.getPrice());
    }

    @Override
    public int getItemCount(){
        return mOrderList.size();
    }
}
