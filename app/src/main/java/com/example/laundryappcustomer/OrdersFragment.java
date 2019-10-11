package com.example.laundryappcustomer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    }
}
