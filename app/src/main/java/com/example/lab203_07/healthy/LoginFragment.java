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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment{
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(mAuth.getCurrentUser() != null) {
            Log.d("LOGIN", "CURRENT USER");
            if(mAuth.getCurrentUser().isEmailVerified()){
                gotoMenuFrag();
                Log.d("LOGIN", "GO TO MENU BY : "+ mAuth.getCurrentUser().getEmail());
            }else{
                Toast.makeText(getActivity(), "Please verified your email", Toast.LENGTH_SHORT).show();
                Log.d("LOGIN", "DON'T VERIFY EMAIL");
            }
        }

        initLoginBtn();
        initRegisterBtn();
    }

    void initLoginBtn(){
        Button _loginBtn = getView().findViewById(R.id.login_login_btn);
        _loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _emailStr = ((EditText)(getView().findViewById(R.id.login_E_Mail))).getText().toString();
                String _passwordStr = ((EditText)(getView().findViewById(R.id.login_password))).getText().toString();

                if(_emailStr.isEmpty() || _passwordStr.isEmpty()){
                        Toast.makeText(getActivity(), "Please fill email or password", Toast.LENGTH_SHORT).show();
                        Log.d("LOGIN", "EMAIL OR PASS EMTHY");
                }else{
                    login(_emailStr,_passwordStr);
                }
            }
        });
    }

    void initRegisterBtn(){
        TextView _registerBtn = getView().findViewById(R.id.login_register_btn);
        _registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new RegisterFragment()).addToBackStack(null).commit();
                Log.d("USER", "GOTO REGISTER");
            }
        });
    }

    void login(String _emailStr, String _passwordStr){

        mAuth.signInWithEmailAndPassword(_emailStr,_passwordStr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                if(authResult.getUser().isEmailVerified()){
                    gotoMenuFrag();
                    Log.d("LOGIN", "GO TO MENU BY : "+ mAuth.getCurrentUser().getEmail());
                }else{
                    Toast.makeText(getActivity(), "Please verified your email", Toast.LENGTH_SHORT).show();
                    Log.d("LOGIN", "DON'T VERIFY EMAIL");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Please Register new account", Toast.LENGTH_SHORT).show();
                Log.d("LOGIN", "LOGIN ERROR");
            }
        });
    }

    void gotoMenuFrag(){
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view , new MenuFragment()).addToBackStack(null).commit();
        Log.d("USER", "GOTO MENU");
    }
}
