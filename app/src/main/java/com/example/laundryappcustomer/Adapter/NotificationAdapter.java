package com.example.laundryappcustomer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.example.laundryappcustomer.NotificationPOJO;
import com.example.laundryappcustomer.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends ArrayAdapter<NotificationPOJO> {
    private Context mContext;
    private List<NotificationPOJO> notificationPOJOArrayList = new ArrayList<>();

    public NotificationAdapter(@NonNull Context context, @NonNull ArrayList<NotificationPOJO> list) {
        super(context, 0,list);
        mContext=context;
        notificationPOJOArrayList=list;

    }
    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent){
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.recyclerviewitem,parent,false);


        NotificationPOJO userPOJOIncomeobject  = notificationPOJOArrayList.get(position);
        TextView textView=listItem.findViewById(R.id.ntitle);
        TextView textView1=listItem.findViewById(R.id.nbody);

        textView.setText(userPOJOIncomeobject.getNotification_POJO_Title());
        textView1.setText(userPOJOIncomeobject.getNotification_POJO_Body());

        return listItem;

    }
}
