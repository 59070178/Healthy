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
import android.widget.ListView;

import com.example.suttidasat.healthy_new.MenuFragment;
import com.example.suttidasat.healthy_new.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class WeightFragment extends Fragment {

    ArrayList<Weight> weights = new ArrayList<>();
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight, container,false);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        ListView _weightList =  (ListView) getView().findViewById(R.id.weight_list);


        final WeightAdapter _weightAdapter = new WeightAdapter(
                getActivity(),
                R.layout.weight_item,
                weights
        );

        _weightList.setAdapter(_weightAdapter);


        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        //GET VALUDE FROM FIREBASE
        String user = auth.getCurrentUser().getUid();
        firestore.collection("myfitness").document(user)
                .collection("Weight")
                .addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        weights.clear();
                        for (DocumentSnapshot d: queryDocumentSnapshots.getDocuments()){
                            weights.add(d.toObject(Weight.class));
                        }
                           _weightAdapter.notifyDataSetChanged();

                    }

                });
        initAddWeightBtn();
        initBackMenuBtn();

    }


    void initAddWeightBtn (){
        Button _addWeight = getView().findViewById(R.id.add_weight);
        _addWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("User", "Go To Weight From");
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new WeightFromFragment())

                        .commit();


            }
        });
    }
    void initBackMenuBtn() {
        Button _backBtn = (Button) getView().findViewById(R.id.back_to_menu);
        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view,new MenuFragment())
                        .addToBackStack(null)
                        .commit();
                Log.d("USER", "GOTO MENU");
            }
        });
    }

}
