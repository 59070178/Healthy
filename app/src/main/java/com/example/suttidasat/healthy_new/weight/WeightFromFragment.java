package com.example.suttidasat.healthy_new.weight;

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

import com.example.suttidasat.healthy_new.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class WeightFromFragment extends Fragment {

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight_form, container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        initRecordBtn();
        initBackBtn();
    }

    void initBackBtn(){
        Button _back = getView().findViewById(R.id.back_to_weight);
        _back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LOGIN", "go to Weight");
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new WeightFragment())
                        .commit();
            }
        });
    }

    void initRecordBtn(){
        Button _rec = getView().findViewById(R.id.record);


        _rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText _date = (EditText) getView().findViewById(R.id.form_date);
                EditText _weight = (EditText) getView().findViewById(R.id.form_weight);

                String _dateStr = _date.getText().toString();
                String _weightStr = _weight.getText().toString();

                WeightFragment weightFragment = new WeightFragment();

                if (_dateStr.isEmpty() || _weightStr.isEmpty()){

                    Toast.makeText(
                            getActivity(),
                            "Fill date and weight",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                else {

                    String uid = auth.getCurrentUser().getUid();

                    Weight _data = new Weight(
                            _dateStr,
                            Integer.valueOf(_weightStr),
                            "UP"
                    );

                    firestore.collection("myfitness")
                            .document(uid)
                            .collection("Weight")
                            .document(_dateStr)
                    .set(_data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Toast.makeText(
                                    getActivity(),
                                    "Success , Go To Weight",
                                    Toast.LENGTH_SHORT
                            ).show();

                            Log.d("LOGIN", "Success");
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.main_view, new WeightFragment())
                                    .commit();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

                }


            }
        });
    }
}
