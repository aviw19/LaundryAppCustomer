package com.example.laundryappcustomer;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.zxing.WriterException;

import java.io.IOException;
import java.text.BreakIterator;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class ProfileFragment extends Fragment {
    private FirebaseAuth mAuth;
    private Button mLogoutButton;
    private ImageView qrImage;
    private Bitmap bitmap;
    public TextView mtextName;
    public TextView mtextID;
    public TextView mtextphone;
    public TextView mtextemailid;
    public TextView mtextroomno;
    public TextView mtexthostel;
    public TextView mEditButton;
    public View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        mAuth=FirebaseAuth.getInstance();
        assignment();
        QRGEncoder qrgEncoder = new QRGEncoder(Common.currentUser.getPhoneno(), null, QRGContents.Type.TEXT,100);
        try {
            // Getting QR-Code as Bitmap
            bitmap = qrgEncoder.encodeAsBitmap();
            // Setting Bitmap to ImageView
            qrImage.setImageBitmap(bitmap);
        } catch (WriterException e) {
            Log.v("Hello", e.toString());
        }
        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditBottomSheet bottomsheet = new EditBottomSheet();
                bottomsheet.show(getFragmentManager(),"exampleBottomSheet");
            }
        });
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            FirebaseInstanceId.getInstance().deleteInstanceId();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Void result) {
                        Intent intent=new Intent(getActivity(),MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        Common.currentUser=null;
                        startActivity(intent);
                        getActivity().finish();
                    }
                }.execute();

            }
        });
        return rootView;

    }

    private void assignment() {
        mLogoutButton=rootView.findViewById(R.id.logout_button);
        qrImage = rootView.findViewById(R.id.qrimage);
        mtextName = rootView.findViewById(R.id.profile_name);
        mtextID = rootView.findViewById(R.id.profile_ID);
        mtextphone = rootView.findViewById(R.id.profile_phoneno);
        mtextemailid = rootView.findViewById(R.id.profile_emailid);
        mtextroomno = rootView.findViewById(R.id.profile_roomno);
        mtexthostel = rootView.findViewById(R.id.profile_hostelno);
        mEditButton =rootView.findViewById(R.id.profile_Edit);
        settexts();
    }

    private void settexts() {
        mtextName.setText(Common.currentUser.getName());
        mtextID.setText(Common.currentUser.getCollegeId());
        mtextemailid.setText(Common.currentUser.getEmailId());
        mtextroomno.setText(Common.currentUser.getRoomNo());
        mtexthostel.setText(Common.currentUser.getHostelNo());
        mtextphone.setText(Common.currentUser.getPhoneno());
    }

}
