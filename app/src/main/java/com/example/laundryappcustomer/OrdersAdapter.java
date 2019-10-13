package com.example.laundryappcustomer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder> {
    private OnItemClickListener mListener;
    int position;
    private ArrayList<Order> mOrderList;
    public interface OnItemClickListener {
        void onPayClick(int position,String status);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener;
    }

    public class OrdersViewHolder extends RecyclerView.ViewHolder {
        private TextView txtOrderID;
        private TextView txtComment;
        private TextView txtStatus;
        private TextView txtWeight;
        private TextView txtPrice;
        private Button mPayButton;
        private TextView txtService;

        public OrdersViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);
            txtOrderID = itemView.findViewById(R.id.order_OrderId);
            txtComment = itemView.findViewById(R.id.order_Comments);
            txtWeight = itemView.findViewById(R.id.order_weight);
            txtStatus = itemView.findViewById(R.id.order_status);
            txtPrice = itemView.findViewById(R.id.order_Price);
            mPayButton = itemView.findViewById(R.id.pay_button);
            txtService = itemView.findViewById(R.id.order_service);
            position = getAdapterPosition();
            mPayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        if (position != RecyclerView.NO_POSITION)
                        {
                            listener.onPayClick(position,mOrderList.get(position).getStatus());
                        }
                    }
                }
            });
        }
    }

    public OrdersAdapter(ArrayList<Order> categoryArrayList) {
        mOrderList = categoryArrayList;
    }


    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_item,parent,false);
        OrdersViewHolder mvh = new OrdersViewHolder(v,mListener);
        return mvh;
    }
    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder,int position){
        final Order currentItem = mOrderList.get(position);
        holder.txtOrderID.setText(currentItem.getOrderID());
        holder.txtComment.setText(currentItem.getComments());
        holder.txtWeight.setText(currentItem.getWeight());
        holder.txtStatus.setText(currentItem.getStatus());
        holder.txtPrice.setText(currentItem.getPrice());
        holder.txtService.setText(currentItem.getService());

    }

    @Override
    public int getItemCount(){
        return mOrderList.size();
    }
}

