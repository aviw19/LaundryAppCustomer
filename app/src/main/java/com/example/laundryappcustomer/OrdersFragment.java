package com.example.laundryappcustomer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrdersFragment extends Fragment {
    private ArrayList<Order> mOrderList=new ArrayList<>();
    private RecyclerView recyclerMenu;
    private RecyclerView.LayoutManager layoutManager;
    private OrdersAdapter mAdapter;
    private View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView =inflater.inflate(R.layout.fragment_orders, container, false);
        createRecyclerView();
        return rootView;
    }

    private void createRecyclerView() {
        recyclerMenu = rootView.findViewById(R.id.recycler_orders);
        recyclerMenu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        mOrderList=Common.currentUser.getOrderList();
        mAdapter = new OrdersAdapter(mOrderList);
        recyclerMenu.setLayoutManager(layoutManager);
        recyclerMenu.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OrdersAdapter.OnItemClickListener()
        {
            @Override
            public void onPayClick(int position, String status,String payamt,String paymentstatus) {
                if (!(paymentstatus.equalsIgnoreCase("Paid"))) {
                    if ((status.equalsIgnoreCase("REQUESTED"))||(status.equalsIgnoreCase("Accepted"))){

                        Intent intent = new Intent(getActivity(), UpiPayment.class);
                        intent.putExtra("stats",mOrderList.get(position).getStatus());
                        intent.putExtra("orderno",String.valueOf(position+1));
                        intent.putExtra("price", mOrderList.get(position).getPrice());
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "Cannot Pay Yet, Please Wait", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(getActivity(), "Already Paid", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
