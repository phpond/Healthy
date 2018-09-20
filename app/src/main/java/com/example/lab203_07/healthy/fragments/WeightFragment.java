package com.example.lab203_07.healthy.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.lab203_07.healthy.R;
import com.example.lab203_07.healthy.Weight;
import com.example.lab203_07.healthy.WeightAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class WeightFragment extends Fragment {

    FirebaseFirestore fStore;
    FirebaseAuth _mAuth;
    ArrayList<Weight> weights = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fStore = FirebaseFirestore.getInstance();
        _mAuth = FirebaseAuth.getInstance();

        ListView _weightList = getView().findViewById(R.id.weight_list);
        final WeightAdapter _weightAdapter = new WeightAdapter(getActivity(), R.layout.fragment_weight_item, weights);
        _weightList.setAdapter(_weightAdapter);
        _weightAdapter.clear();

        getDataFromFirebase(_weightAdapter);
        Log.d("WEIGHT", "GET DATA OF "+_mAuth.getCurrentUser().getUid());

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

    void getDataFromFirebase(final WeightAdapter _weightAdapter){
        fStore.collection("“myfitness”")
                .document(_mAuth.getCurrentUser().getUid())
                .collection("weight")
                .orderBy("date")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                _weightAdapter.clear();
                for (QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                    weights.add(doc.toObject(Weight.class));
                    Log.d("WEIGHT", "SUCCESS DATE : "+doc.getId());
                }
                _weightAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("WEIGHT", "GET DATA ON FAIL");
            }
        });
    }
}
