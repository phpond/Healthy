package com.example.lab203_07.healthy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lab203_07.healthy.fragments.LoginFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setContentView(R.layout.);
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new LoginFragment()).commit();
        }
    }
}
