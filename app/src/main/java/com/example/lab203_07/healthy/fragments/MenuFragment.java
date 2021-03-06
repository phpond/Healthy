package com.example.lab203_07.healthy.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lab203_07.healthy.R;
import com.example.lab203_07.healthy.RestApi.PostFragment;
import com.example.lab203_07.healthy.Sleep.SleepFragment;
import com.example.lab203_07.healthy.Weights.WeightFormFragment;
import com.example.lab203_07.healthy.Weights.WeightFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MenuFragment extends Fragment{

    ArrayList<String> _menu = new ArrayList<>();
    FirebaseAuth _mAuth = FirebaseAuth.getInstance();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        _menu.add("BMI");
        _menu.add("Weight");
        _menu.add("Post");
        _menu.add("Sleep");
        _menu.add("Setup");
        _menu.add("Sign out");

        final ArrayAdapter<String> _menuAdapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_list_item_1, _menu
        );
        ListView _menuList = getView().findViewById(R.id.menu_list);
        _menuList.setAdapter(_menuAdapter);
        _menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("MENU", "Click on menu = "+_menu.get(i));
                _menuAdapter.notifyDataSetChanged();
                if(_menu.get(i).equals("BMI")){
                    _menuAdapter.clear();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new BMIFragment()).addToBackStack(null).commit();
                }
                else if(_menu.get(i).equals("Weight")){
                    _menuAdapter.clear();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new WeightFragment()).addToBackStack(null).commit();
                }else if(_menu.get(i).equals("Setup")){
                    _menuAdapter.clear();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new WeightFormFragment()).addToBackStack(null).commit();
                }else if(_menu.get(i).equals("Sleep")){
                    _menuAdapter.clear();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new SleepFragment()).addToBackStack(null).commit();
                }else if(_menu.get(i).equals("Post")){
                    _menuAdapter.clear();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new PostFragment()).addToBackStack(null).commit();
                }else{
                    _mAuth.signOut();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new LoginFragment()).addToBackStack(null).commit();
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }
}
