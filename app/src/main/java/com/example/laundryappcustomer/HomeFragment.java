package com.example.laundryappcustomer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private CardView mRequestOrder;
    private CardView mCard2;
    private CardView mCard3;
    private CardView mCard4;
    private CardView mCard5;
    private CardView mCard6;
    private TextView mTextComment;
    private String OrderID;
    ArrayList<Order> OrderList = new ArrayList<>();
    private String mComment;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference table_user = firebaseDatabase.getReference("Requests");
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View rootView =inflater.inflate(R.layout.fragment_home, container, false);
       mRequestOrder=rootView.findViewById(R.id.request_Order);
        mCard2=rootView.findViewById(R.id.Card2);
        mCard3=rootView.findViewById(R.id.Card3);
        mCard4=rootView.findViewById(R.id.Card4);
        mCard5=rootView.findViewById(R.id.Card5);
        mCard6=rootView.findViewById(R.id.Card6);
        //getCurrentOrderList();
        actions();
       return rootView;
    }

    private void getCurrentOrderList() {
        DatabaseReference db = firebaseDatabase.getReference("Customer").child(Common.currentUser.getPhoneno());
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if(ds.child("Order").exists()){
                    Order ol = ds.getValue(Order.class);
                    OrderList.add(ol);}
                    else
                    {
                        Toast.makeText(getActivity(),"ABHI ORDER HAIHI NAHI",Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
            }
        };
        db.addValueEventListener(eventListener);
        Common.OrderList=OrderList;
    }


    private void actions() {
        //first card View
        mRequestOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                final View alertLayout = inflater.inflate(R.layout.request_comments, null);
                mTextComment=alertLayout.findViewById(R.id.request_Comment);
                alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent toPayment = new Intent(getActivity(),Payment.class);

                        startActivity(toPayment);

                        OrderID= Common.currentUser.getPhoneno()+Common.currentUser.getOrderCount();
                        mComment=mTextComment.getText().toString();
                        makingRequest();
                        updatingOrderNo();
                        updateCurrentUser();
                        updateOrderList();
                    }
                });
                alert.setView(alertLayout);
                alert.show();
            }
        });
    }

    private void updateOrderList() {
        //update Orders of User and also make a Order LIST IN USER. WITH CLASS USER
        Order n=new Order(mComment,"REQUESTED","0KG",OrderID);
        Common.OrderList.add(n);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference db=firebaseDatabase.getReference("Customer").child(Common.currentUser.getPhoneno());
        for(Order oL: Common.OrderList) {
                    db.child("Orders").child(OrderID).setValue(oL);
        }
    }

    private void updateCurrentUser() {
       DatabaseReference db=firebaseDatabase.getReference("Customer");
        try {
            db.child(Common.currentUser.getPhoneno()).setValue(Common.currentUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updatingOrderNo() {
        int count = Integer.parseInt(Common.currentUser.getOrderCount());
        count++;
        Common.currentUser.setOrderCount(String.valueOf(count));
    }

    private void makingRequest() {
        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Requests mReq = new Requests(Common.currentUser,mComment,"REQUESTED","0KG",OrderID);
                table_user.child(OrderID).setValue(mReq);
                Toast.makeText(getActivity(),"Request For Collection is Done",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

