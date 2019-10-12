package com.example.laundryappcustomer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private TextView mTextComment;
    private String OrderID;
    private ArrayList<Order> OrderList = new ArrayList<>();
    private String mComment;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference table_user = firebaseDatabase.getReference("Requests");
    private DatabaseReference table_user2 = firebaseDatabase.getReference("Customer").child(Common.currentUser.getPhoneno());
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View rootView =inflater.inflate(R.layout.fragment_home, container, false);
       mRequestOrder=rootView.findViewById(R.id.request_Order);
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
                mTextComment=alertLayout.findViewById(R.id.request_Comment);
                alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        OrderID= Common.currentUser.getPhoneno()+Common.currentUser.getOrderCount();
                        mComment=mTextComment.getText().toString();
                        makingRequest();
                        addingOrder();
                    }
                });
                alert.setView(alertLayout);
                alert.show();
                Intent toPayment = new Intent(getActivity(),Payment.class);
                startActivity(toPayment);
            }
        });
    }

    private void makingRequest() {
        Requests mReq = new Requests(Common.currentUser,mComment,"REQUESTED","0KG",OrderID);
        table_user.child(OrderID).setValue(mReq);
    }

    private void addingOrder() {
        updatingOrderNo();
        updateOrderList();
        changeInUser();
    }

    private void updatingOrderNo() {
        int count = Integer.parseInt(Common.currentUser.getOrderCount());
        count++;
        Common.currentUser.setOrderCount(String.valueOf(count));
    }

    private void updateOrderList() {
        Order n = new Order(mComment, "REQUESTED", "0KG", OrderID,"NOT YET");
        OrderList = Common.currentUser.getOrderList();
        if (OrderList != null) {
            OrderList.add(n);
            Common.currentUser.setOrderList(OrderList);
        }
        else
        {
            OrderList = new ArrayList<Order>();
            OrderList.add(n);
            Common.currentUser.setOrderList(OrderList);
        }
    }

    private void changeInUser() {
        table_user2.setValue(Common.currentUser);
    }
}

