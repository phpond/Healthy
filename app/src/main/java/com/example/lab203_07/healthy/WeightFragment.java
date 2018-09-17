package com.example.lab203_07.healthy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class WeightFragment extends Fragment {

    ArrayList<Weight> weights = new ArrayList<>();
    FirebaseFirestore fstore = FirebaseFirestore.getInstance();
    FirebaseAuth _mAuth = FirebaseAuth.getInstance();
    ListView _weightList;
    WeightAdapter _weightAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _weightList = getView().findViewById(R.id.weight_list);
        Log.d("WEIGHT", "skjs");
        getDataFromFirebase();
        Log.d("WEIGHT", "success");
        _weightAdapter = new WeightAdapter(getActivity(),R.layout.fragment_weight_item,weights);
        _weightList.setAdapter(_weightAdapter);
        _weightAdapter.clear();
        initAddWeightBtn();
    }

    public void initAddWeightBtn(){
        Button _addWeight = getView().findViewById(R.id.weight_add_weight_btn);
        _addWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new WeightFormFragment()).addToBackStack(null).commit();
                Log.d("WEIGHT", "GO TO WEIGHT FORM");
            }
        });
    }

    void getDataFromFirebase(){
        fstore.collection("myfitness")
                .document(_mAuth.getCurrentUser().getUid())
                .collection("weight").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        Log.d("WEIGHT", "object : ");
                        for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                            weights.add(doc.toObject(Weight.class));
                            _weightAdapter.notifyDataSetChanged();
                            Log.d("WEIGHT", "object : "+doc);
                        }
                    }
                });
    }
}
