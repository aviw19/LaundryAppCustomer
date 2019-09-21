package com.example.laundryappcustomer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laundryappcustomer.Adapter.MyDbHandler;
import com.example.laundryappcustomer.Adapter.NotificationAdapter;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_notification, container, false);
        /*MyDbHandler myDbHandler=new MyDbHandler(getActivity());
        List<NotificationPOJO> notificationPOJOList= myDbHandler.getAllNotifications();
        ListView lv=rootView.findViewById(R.id.listview);

        //final List<String> IncomeList= new ArrayList<>();
        ArrayList<NotificationPOJO> notificationPOJOArrayList= new ArrayList<>();


        //final ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(),R.layout.listviewitemlayout,R.id.textView3,IncomeList);



        for (NotificationPOJO cn : notificationPOJOList)
            notificationPOJOArrayList.add(new NotificationPOJO(cn.getId(),cn.getNotification_POJO_Body(),cn.Notification_POJO_Body));




        NotificationAdapter NotificationAdapter =new NotificationAdapter(getActivity(),notificationPOJOArrayList);
        lv.setAdapter(NotificationAdapter);*/
        return  rootView;
    }
}
