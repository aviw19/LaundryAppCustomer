package com.example.laundryappcustomer;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class ProfileFragment extends Fragment {
    private FirebaseAuth mAuth;
    private Button mLogoutButton;
    private ImageView qrImage;
    private Bitmap bitmap;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference db=firebaseDatabase.getReference("Customer").child(Common.currentUser.getPhoneno()).child("firebaseToken");
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView= inflater.inflate(R.layout.fragment_profile, container, false);
        mAuth=FirebaseAuth.getInstance();
        mLogoutButton=rootView.findViewById(R.id.logout_button);

        qrImage = rootView.findViewById(R.id.qrimage);
        QRGEncoder qrgEncoder = new QRGEncoder(Common.currentUser.getPhoneno(), null, QRGContents.Type.TEXT,100);
        try {
            // Getting QR-Code as Bitmap
            bitmap = qrgEncoder.encodeAsBitmap();
            // Setting Bitmap to ImageView
            qrImage.setImageBitmap(bitmap);
        } catch (WriterException e) {
            Log.v("Hello", e.toString());
        }
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.setValue("null");
                mAuth.signOut();
                Intent intent=new Intent(getActivity(),MainActivity.class);
                Common.currentUser=null;
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();

            }
        });
        return rootView;

    }
}
