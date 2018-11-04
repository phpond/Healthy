package com.example.lab203_07.healthy;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.lab203_07.healthy.Weights.Weight;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CompareStatus {
    String status;
    FirebaseFirestore fStore;
    FirebaseAuth _mAuth;

    ArrayList<Weight> weights = new ArrayList<>();

    public void CompareStatus(float _weight){
        fStore = FirebaseFirestore.getInstance();
        _mAuth = FirebaseAuth.getInstance();
        getDataFromFirebase(_weight);
        Log.d("COMPARE", "GET DATA OF "+_mAuth.getCurrentUser().getUid());

    }
    void getDataFromFirebase(float _weight){
        fStore.collection("“myfitness”")
                .document(_mAuth.getCurrentUser().getUid())
                .collection("weight")
                .orderBy("date")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("WEIGHT", "GET DATA ON FAIL");
            }
        });
    }
}
