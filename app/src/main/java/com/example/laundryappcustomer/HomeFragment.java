package com.example.laundryappcustomer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;

import java.security.PrivateKey;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static java.lang.Integer.parseInt;

public class HomeFragment extends Fragment {
    private ImageView mRequestOrder;
    private ImageView mRequestOrderandIron;
    private TextView mTextComment;
    private String OrderID;
    private ArrayList<Order> OrderList = new ArrayList<>();
    private String mComment;
    private Requests mReq;
    private RadioButton sixKgbutton;
    private RadioButton EightKgButton;
    private FirebaseDatabase firebaseDatabase ;
    private DatabaseReference table_user ;
    private DatabaseReference table_user2;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        table_user = firebaseDatabase.getReference("Requests");
        table_user2 = firebaseDatabase.getReference("Customer").child(Common.currentUser.getPhoneno());
       View rootView =inflater.inflate(R.layout.fragment_home, container, false);
       mRequestOrder=rootView.findViewById(R.id.request_Order);
       mRequestOrderandIron = rootView.findViewById(R.id.request_Orderandiron);
        FirebaseTokeGeneration.main();

        String token=FirebaseTokeGeneration.token;
        table_user2.child("firebaseToken").setValue(token);
        actions();
       return rootView;
    }

    private void actions() {
        //first card View
        mRequestOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                final View alertLayout = inflater.inflate(R.layout.request_comments, null);
                mTextComment=alertLayout.findViewById(R.id.comments);
                sixKgbutton = alertLayout.findViewById(R.id.sixkgbutton);
                EightKgButton = alertLayout.findViewById(R.id.eightkgbutton);
                alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String weight="0KG";
                        OrderID= Common.currentUser.getPhoneno()+Common.currentUser.getOrderCount();
                        mComment=mTextComment.getText().toString();
                        if(sixKgbutton.isChecked() && EightKgButton.isChecked())
                        {
                            Toast.makeText(getActivity(),"Please select only a single category for weight",Toast.LENGTH_SHORT).show();
                        }
                        else if(!sixKgbutton.isChecked() && !EightKgButton.isChecked())
                        {
                            Toast.makeText(getActivity(),"Please select a category for weight",Toast.LENGTH_SHORT).show();
                        }
                        else if(sixKgbutton.isChecked())
                        {
                            weight="6KG";
                        }
                        else
                        {
                            weight="8KG";
                        }
                        makingRequest1(weight);
                        addingOrder(weight);
                    }
                });
                alert.setView(alertLayout);
                alert.show();
            }
        });
        mRequestOrderandIron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                final View alertLayout = inflater.inflate(R.layout.request_comments, null);
                mTextComment=alertLayout.findViewById(R.id.comments);
                alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Intent toPayment = new Intent(getActivity(),Payment.class);
                        //startActivity(toPayment);
                        String weight="0KG";
                        OrderID= Common.currentUser.getPhoneno()+Common.currentUser.getOrderCount();
                        mComment=mTextComment.getText().toString();
                        sixKgbutton = alertLayout.findViewById(R.id.sixkgbutton);
                        EightKgButton = alertLayout.findViewById(R.id.eightkgbutton);
                        if(sixKgbutton.isChecked() && EightKgButton.isChecked())
                        {
                            Toast.makeText(getActivity(),"Please select only a single category for weight",Toast.LENGTH_SHORT).show();
                        }
                        else if(!sixKgbutton.isChecked() && !EightKgButton.isChecked())
                        {
                            Toast.makeText(getActivity(),"Please select a category for weight",Toast.LENGTH_SHORT).show();
                        }
                        else if(sixKgbutton.isChecked())
                        {
                            weight="6KG";
                        }
                        else
                        {
                            weight="8KG";
                        }
                        makingRequest2(weight);
                        addingOrder(weight);
                    }
                });
                alert.setView(alertLayout);
                alert.show();


            }
        });
    }

    private void makingRequest1(String weight) {
        mReq = new Requests(Common.currentUser,mComment,"REQUESTED",weight,OrderID,"Wash Only","NOT PAID");
        table_user.child(OrderID).setValue(mReq);
    }
    private void makingRequest2(String weight) {
        mReq = new Requests(Common.currentUser,mComment,"REQUESTED",weight,OrderID,"Wash and Iron","NOT PAID");
        table_user.child(OrderID).setValue(mReq);
    }

    private void addingOrder(String weight) {
        updatingOrderNo();
        updateOrderList(weight);
        changeInUser();
    }

    private void updatingOrderNo() {
        int count = Integer.parseInt(Common.currentUser.getOrderCount());
        count++;
        Common.currentUser.setOrderCount(String.valueOf(count));
    }

    private void updateOrderList(String weight) {
        Order n = new Order(mComment, "REQUESTED", weight, OrderID,"NOT YET",mReq.getService(),"NOT PAID");
        OrderList = Common.currentUser.getOrderList();
        if (OrderList != null) {
            OrderList.add(n);
            Common.currentUser.setOrderList(OrderList);
        }
        else
        {
            OrderList = new ArrayList<>();
            OrderList.add(n);
            Common.currentUser.setOrderList(OrderList);
        }
    }

    private void changeInUser(){
        table_user2.setValue(Common.currentUser);
    }
}

