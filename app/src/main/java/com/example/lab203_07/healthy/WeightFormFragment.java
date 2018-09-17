package com.example.lab203_07.healthy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class WeightFormFragment extends Fragment {

    FirebaseFirestore _firestore;
    FirebaseAuth _mAuth;

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
        initSaveButton();
        initBack();
    }

    void initSaveButton(){
        Button _btn = getView().findViewById(R.id.weight_form_save_btn);
        _btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _date = ((EditText)(getView().findViewById(R.id.weight_form_date))).getText().toString();
                String _weight = ((EditText)(getView().findViewById(R.id.weight_form_weight))).getText().toString();
                String _uid = _mAuth.getCurrentUser().getUid();

                if(_date.isEmpty() || _weight.isEmpty()){
                    Toast.makeText(getActivity(),"กรุณาใส่ข้อมูลให้ครบ",Toast.LENGTH_SHORT).show();
                }else {
                    Weight _weights = new Weight(_date, Integer.parseInt(_weight), "UP");
                    _firestore.collection("“myfitness”").document(_uid)
                            .collection("weight").document(_date)
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
}
