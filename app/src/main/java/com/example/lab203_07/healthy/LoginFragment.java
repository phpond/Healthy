package com.example.lab203_07.healthy;

import android.os.Bundle;
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

public class LoginFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initLoginBtn();
        initRegisterBtn();
    }

    void initLoginBtn(){
        Button _loginBtn = getView().findViewById(R.id.login_login_btn);
        _loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText _userId = getView().findViewById(R.id.login_user_id);
                EditText _password = getView().findViewById(R.id.login_password);
                String _userIdStr = _userId.getText().toString();
                String _passwordStr = _password.getText().toString();
                if(_userIdStr.isEmpty() || _passwordStr.isEmpty()){
                    Toast.makeText(getActivity(), "กรุณาระบุuser or password", Toast.LENGTH_SHORT).show();
                    Log.d("USER", "USER OR PASSWORD IS EMPTY");
                }else if(_userIdStr.equals("admin") && _passwordStr.equals("admin")){
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view , new MenuFragment()).addToBackStack(null).commit();
                    Log.d("USER", "“GOTO MENU1");
                }else{
                    Toast.makeText(getActivity(), "“user or password ไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
                    Log.d("USER", "INVALID USER OR PASSWORD");
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
}
