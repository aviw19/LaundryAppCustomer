package com.example.laundryappcustomer;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {
    private ImageView mRequestOrder;
    private ImageView mRequestOrderAndIron;
    private TextView mTextComment;
    private String OrderID;
    private String mComment;
    private Requests mReq;
    private RadioButton sixKgButton;
    private RadioButton EightKgButton;
    private RadioButton pickUpAndDrop;
    private RadioButton onlyDrop;
    private RadioButton onlyPickup;
    private RadioButton noService;
    private String price;
    private String service;
    private DatabaseReference table_user ;
    private DatabaseReference table_user2;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        table_user = firebaseDatabase.getReference("Requests").child(Common.merchantphone);
        table_user2 = firebaseDatabase.getReference("Customer").child(Common.currentUser.getPhoneno());
       View rootView =inflater.inflate(R.layout.fragment_home, container, false);
       mRequestOrder=rootView.findViewById(R.id.request_Order);
       mRequestOrderAndIron = rootView.findViewById(R.id.request_Orderandiron);
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
                final AlertDialog.Builder alert = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                LayoutInflater inflater = getLayoutInflater();
                final View alertLayout = inflater.inflate(R.layout.request_comments1, null);
                mTextComment=alertLayout.findViewById(R.id.comments);
                sixKgButton = alertLayout.findViewById(R.id.sixkgradiobutton1);
                EightKgButton = alertLayout.findViewById(R.id.eightkgradiobutton1);
                pickUpAndDrop = alertLayout.findViewById(R.id.pickupdrop1);
                onlyPickup = alertLayout.findViewById(R.id.pickupdrop2);
                onlyDrop =alertLayout.findViewById(R.id.pickupdrop3);
                noService =alertLayout.findViewById(R.id.pickupdrop4);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                alert.setCancelable(false).setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String weight="0KG";
                        OrderID= Common.currentUser.getPhoneno()+Common.currentUser.getOrderCount();
                        mComment=mTextComment.getText().toString();

                        if(sixKgButton.isChecked())
                        {
                            weight="6KG";
                            price="199";
                            sixKgButton.setText("COST : 199RS");
                        }
                        if(EightKgButton.isChecked())
                        {
                            EightKgButton.setText("COST : 266RS");
                            weight="8KG";
                            price="266";
                        }
                        if(!(sixKgButton.isChecked())&&!(EightKgButton.isChecked()))
                        {
                            Toast.makeText(getActivity(),"Please select a weight slab",Toast.LENGTH_SHORT).show();
                        }

                        if(pickUpAndDrop.isChecked())
                        {
                            price=Integer.toString(20+Integer.parseInt(price));
                            service = "PICKUP AND DROP";
                            makingRequest1(weight,service);
                            addingOrder(weight,service);
                        }
                        else if(onlyPickup.isChecked())
                        {
                            price=Integer.toString(10+Integer.parseInt(price));
                            service = "ONLY PICKUP FROM HOME";
                            makingRequest1(weight,service);
                            addingOrder(weight,service);
                        }
                        else if(onlyDrop.isChecked())
                        {
                            price=Integer.toString(10+Integer.parseInt(price));
                            service = "ONLY DROP AT HOME";
                            makingRequest1(weight,service);
                            addingOrder(weight,service);
                        }
                        else if(noService.isChecked())
                        {
                            price=Integer.toString(10+Integer.parseInt(price));
                            service = "NO PICKUP AND DROP";
                            makingRequest1(weight,service);
                            addingOrder(weight,service);
                        }
                        else
                        {

                            Toast.makeText(getActivity(),"Please select a service",Toast.LENGTH_SHORT).show();

                        }

                    }
                });
                alert.setView(alertLayout);
                alert.show();
            }
        });

        mRequestOrderAndIron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                LayoutInflater inflater = getLayoutInflater();
                final View alertLayout = inflater.inflate(R.layout.request_comments2, null);
                mTextComment=alertLayout.findViewById(R.id.comments);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                alert.setCancelable(false).setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        String weight="0KG";
                        OrderID= Common.currentUser.getPhoneno()+Common.currentUser.getOrderCount();
                        mComment=mTextComment.getText().toString();
                        sixKgButton = alertLayout.findViewById(R.id.sixkgradiobutton1);
                        EightKgButton = alertLayout.findViewById(R.id.eightkgradiobutton1);
                        pickUpAndDrop = alertLayout.findViewById(R.id.pickupdrop1);
                        onlyPickup = alertLayout.findViewById(R.id.pickupdrop2);
                        onlyDrop =alertLayout.findViewById(R.id.pickupdrop3);
                        noService =alertLayout.findViewById(R.id.pickupdrop4);

                        if(!sixKgButton.isChecked() && !EightKgButton.isChecked())
                        {
                            Toast.makeText(getActivity(),"Please select a weight slab",Toast.LENGTH_SHORT).show();
                        }
                        if(sixKgButton.isChecked())
                        {
                            weight="6KG";
                            price="245";
                        }
                        if(EightKgButton.isChecked())
                        {
                            weight="8KG";
                            price="325";
                        }


                        if(pickUpAndDrop.isChecked())
                        {
                            price=Integer.toString(20+Integer.parseInt(price));
                            service = "PICKUP AND DROP";
                            makingRequest2(weight,service);
                            addingOrder(weight,service);
                        }
                        else if(onlyPickup.isChecked())
                        {
                            price=Integer.toString(10+Integer.parseInt(price));
                            service = "ONLY PICKUP FROM HOME";
                            makingRequest2(weight,service);
                            addingOrder(weight,service);
                        }
                        else if(onlyDrop.isChecked())
                        {
                            price=Integer.toString(10+Integer.parseInt(price));
                            service = "ONLY DROP AT HOME";
                            makingRequest2(weight,service);
                            addingOrder(weight,service);
                        }
                        else if(noService.isChecked())
                        {
                            price=Integer.toString(10+Integer.parseInt(price));
                            service = "NO PICKUP AND DROP";
                            makingRequest2(weight,service);
                            addingOrder(weight,service);
                        }
                        else
                        {

                            Toast.makeText(getActivity(),"Please select a service",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                alert.setView(alertLayout);
                alert.show();


            }
        });
    }

    private void makingRequest1(String weight,String service) {
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        mReq = new Requests(mComment,"REQUESTED",weight,OrderID,"Wash Only" + service,"NOT PAID",price,ts,"");
        table_user.child(OrderID).setValue(mReq);
        String token=FirebaseTokeGeneration.token;
        table_user2.child("firebaseToken").setValue(token);

    }

    private void makingRequest2(String weight,String service) {
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        mReq = new Requests(mComment,"REQUESTED",weight,OrderID,"Wash and Iron" + service,"NOT PAID",price,ts,"");
        table_user.child(OrderID).setValue(mReq);
        String token=FirebaseTokeGeneration.token;
        table_user2.child("firebaseToken").setValue(token);
    }

    private void addingOrder(String weight,String service) {
                updatingOrderNo();
                updateOrderList(weight,service);
                changeInUser();
                String token=FirebaseTokeGeneration.token;
                table_user2.child("firebaseToken").setValue(token);
            }

    private void updatingOrderNo() {
                int count = Integer.parseInt(Common.currentUser.getOrderCount());
                count++;
                Common.currentUser.setOrderCount(String.valueOf(count));
            }

    private void updateOrderList(String weight,String service) {
        Long tsLong = System.currentTimeMillis();
        String ts = tsLong.toString();
        Order n = new Order(mComment, "REQUESTED", weight, OrderID,price,mReq.getservices(),"NOT PAID",Common.merchantphone,ts,"");
        ArrayList<Order> orderList = Common.currentUser.getOrderList();
        if (orderList != null) {
            orderList.add(n);
            Common.currentUser.setOrderList(orderList);
        }
        else
        {
            orderList = new ArrayList<>();
            orderList.add(n);
            Common.currentUser.setOrderList(orderList);
        }
    }

    private void changeInUser() {
                table_user2.setValue(Common.currentUser);
                SharedPreferences sharedPreferences= Objects.requireNonNull(this.getActivity()).getSharedPreferences(Common.SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(Common.currentUser);
                editor.putString("CurrentUser", json);
                editor.putString(Common.PHONENO,Common.currentUser.getPhoneno());
                editor.putBoolean(Common.LOGIN,true);
                editor.apply();
            }
}

