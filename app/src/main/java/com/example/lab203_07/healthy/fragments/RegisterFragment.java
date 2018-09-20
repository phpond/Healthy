package com.example.lab203_07.healthy.fragments;


import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab203_07.healthy.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterFragment extends Fragment {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initRegisterBtn();
    }

    void initRegisterBtn(){
        Button _registerBtn = getView().findViewById(R.id.register_register_btn);
        _registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _email = ((EditText)(getView().findViewById(R.id.register_email))).getText().toString();
                String _password = ((EditText)(getView().findViewById(R.id.register_password))).getText().toString();
                String _rePassword = ((EditText)(getView().findViewById(R.id.register_re_password))).getText().toString();

                if(_password.length() < 6){
                    Toast.makeText(getActivity(), "รหัสผ่านต้องมากกว่า 6 ตัวอักษร", Toast.LENGTH_SHORT).show();
                    Log.d("REGISTER", "PASSWORD LESS 6 CHAR");
                }else if(_password.equals(_rePassword) == false){
                    Toast.makeText(getActivity(), "Password และ Re-Password ไม่ตรงกัน", Toast.LENGTH_SHORT).show();
                    Log.d("REGISTER", "PASS NOT EQUAL RE-PASS");
                }else if(_email.isEmpty() || _password.isEmpty() || _rePassword.isEmpty()){
                    Toast.makeText(getActivity(), "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
                    Log.d("REGISTER", "EMPTY");
                }else{
                    //register
                    regisFirebase(_email,_password);

                }
            }
        });
    }


    void regisFirebase(String _email, String _password){
        mAuth.createUserWithEmailAndPassword(_email,_password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {@Override public void onSuccess(AuthResult authResult) {
                    sendVerifiedEmail(authResult.getUser());
                    Log.d("REGISTER", "REGISTER SUCCESS"); }})
                .addOnFailureListener(new OnFailureListener() {@Override public void onFailure(@NonNull Exception e) {
                    Log.d("REGISTER", "REGISTER FAIL");
                    Toast.makeText(getActivity(),"Emailนี้เคยลงทะเบียนแล้ว", Toast.LENGTH_SHORT).show();
                }});
    }

    void sendVerifiedEmail(FirebaseUser _user) {
        _user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("REGISTER", "SEND VERIFY SUCCESS");
                gotoLoginFrag();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"ERROR", Toast.LENGTH_SHORT).show();
                Log.d("REGISTER", "SEND VERIFY FAIL");
            }
        });
    }

    void gotoLoginFrag(){
        //go to loginFrag
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new LoginFragment()).addToBackStack(null).commit();
        FirebaseAuth _math = FirebaseAuth.getInstance();
        _math.signOut();
        Log.d("REGISTER", "GOTO Login");
    }
}
