package com.example.lab203_07.healthy.fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab203_07.healthy.R;
import com.example.lab203_07.healthy.Weight;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class WeightFormFragment extends Fragment {

    FirebaseFirestore _firestore;
    FirebaseAuth _mAuth;
    String[] _month = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "July", "Aug", "Sep", "Oct", "Nov", "Dec"};

    private DatePickerDialog.OnDateSetListener mDateDataListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.weight_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        _firestore = FirebaseFirestore.getInstance();
        _mAuth = FirebaseAuth.getInstance();
        initDate();
        initSaveButton();
        initBack();

    }

    void initDate(){
        final TextView _dateEdit = getView().findViewById(R.id.weight_form_date);
        _dateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateDataListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateDataListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                _dateEdit.setText(day+" "+_month[month]+" "+year);
                Log.d("FORM", "On date : "+ day +" / "+month + " / "+year);
            }
        };
    }

    void initSaveButton(){
        Button _btn = getView().findViewById(R.id.weight_form_save_btn);
        _btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _date = ((TextView)(getView().findViewById(R.id.weight_form_date))).getText().toString();
                String _weight = ((EditText)(getView().findViewById(R.id.weight_form_weight))).getText().toString();

                if(_date.isEmpty() || _weight.isEmpty()){
                    Toast.makeText(getActivity(),"กรุณาใส่ข้อมูลให้ครบ",Toast.LENGTH_SHORT).show();
                }else {
                    addInfo(new Weight(_date,Integer.parseInt(_weight)));
                }

            }
        });
    }

    void initBack(){
        Button _btn = getView().findViewById(R.id.weight_form_back_btn);
        _btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new MenuFragment()).addToBackStack(null).commit();
                Log.d("FORM", "COME BACK TO MENU");
            }
        });
    }

    void addInfo(Weight _weights){

        _firestore.collection("“myfitness”").document(_mAuth.getCurrentUser().getUid())
                .collection("weight").document(_weights.getDate())
                .set(_weights)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(),"บันทึกสำเร็จ",Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new WeightFragment()).addToBackStack(null).commit();
                        Log.d("FORM", "ADD ON SUCCESS");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"บันทึกไม่สำเร็จ",Toast.LENGTH_SHORT).show();
                Log.d("FORM", "ADD ON FAIL");
            }
        });
    }
}
