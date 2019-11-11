package com.example.laundryappcustomer;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class EditBottomSheet extends BottomSheetDialogFragment {
    private BottomSheetListener mListener;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.profile_edit,container,false);
        final EditText memailid = v.findViewById(R.id.edit_emailid);
        final EditText mhostel = v.findViewById(R.id.edit_hostel);
        final EditText mroomno = v.findViewById(R.id.edit_roomno);
        final EditText mID = v.findViewById(R.id.edit_id);
        mID.setText(Common.currentUser.getCollegeId());
        mroomno.setText(Common.currentUser.getRoomNo());
        mhostel.setText(Common.currentUser.getHostelNo());
        memailid.setText(Common.currentUser.getEmailId());
        Button mUpdate = v.findViewById(R.id.edit_update);
        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked(mroomno.getText().toString(),memailid.getText().toString(),mhostel.getText().toString(),mID.getText().toString());
                dismiss();
            }
        });
        return v;
    }
    public interface BottomSheetListener{
        void onButtonClicked(String newroomno,String newemailid,String newhostel,String newID);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (BottomSheetListener) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString()+ "must implement BottomSheetListener");
        }
    }
}
