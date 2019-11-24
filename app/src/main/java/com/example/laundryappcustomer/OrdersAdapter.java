package com.example.laundryappcustomer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder> {
    private OnItemClickListener mListener;
    private ArrayList<Order> mOrderList;
    public interface OnItemClickListener {
        void onPayClick(int position,String status,String payamt,String paymentstatus);
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

        public TextView txtPaymentStatus;

        public OrdersViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);
            txtOrderID = itemView.findViewById(R.id.order_OrderId);
            txtComment = itemView.findViewById(R.id.order_Comments);
            txtWeight = itemView.findViewById(R.id.order_weight);
            txtStatus = itemView.findViewById(R.id.order_status);
            txtPrice = itemView.findViewById(R.id.order_Price);
            mPayButton = itemView.findViewById(R.id.pay_button);
            txtService = itemView.findViewById(R.id.order_service);
            txtPaymentStatus = itemView.findViewById(R.id.order_paymentstatus);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                        {
                                listener.onPayClick(position,mOrderList.get(position).getStatus(),mOrderList.get(position).getPrice(),mOrderList.get(position).getPaymentStatus());
                        }


                    }
                    else
                    {
                        Log.d("hello", "onClick: ");
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
    public void onBindViewHolder(@NonNull OrdersAdapter.OrdersViewHolder holder, int position) {
            Order currentItem = mOrderList.get(position);
            holder.txtOrderID.setText(currentItem.getOrderID());
            holder.txtComment.setText(currentItem.getComments());
            holder.txtWeight.setText(currentItem.getWeight());
            holder.txtStatus.setText(currentItem.getStatus());
            holder.txtPrice.setText(currentItem.getPrice());
            holder.txtService.setText(currentItem.getService());
            holder.txtPaymentStatus.setText(currentItem.getPaymentStatus());
            holder.mPayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            }

    @Override
    public int getItemCount(){
        if(mOrderList==null)
        {
            return 0;
        }
        return mOrderList.size();
    }
}

