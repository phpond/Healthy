package com.example.lab203_07.healthy;


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

public class RegisterFragment extends Fragment {
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
                String _userId = ((EditText)(getView().findViewById(R.id.register_user_id))).getText().toString();
                String _name = ((EditText)(getView().findViewById(R.id.register_name))).getText().toString();
                String _age = ((EditText)(getView().findViewById(R.id.register_age))).getText().toString();
                String _password = ((EditText)(getView().findViewById(R.id.register_password))).getText().toString();

                if(_userId.isEmpty() || _name.isEmpty() || _age.isEmpty() || _password.isEmpty()){
                    Toast.makeText(getActivity(), "กรุณาระบุข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show();
                    Log.d("REGISTER", "FIELD NAME IS EMPTY");
                }else if(_userId.equals("admin")){
                    Toast.makeText(getActivity(), "user นี้มีอยู่ในระบบแล้ว", Toast.LENGTH_SHORT).show();
                    Log.d("REGISTER", "USER ALREADY EXIST");
                }else{
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new BMIFragment()).addToBackStack(null).commit();
                    Log.d("REGISTER", "GOTO BMI");
                }
            }
        });
    }
}
