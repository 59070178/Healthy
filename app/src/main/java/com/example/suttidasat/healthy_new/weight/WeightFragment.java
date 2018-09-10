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

import com.example.suttidasat.healthy_new.R;

import java.util.ArrayList;

public class WeightFragment extends Fragment {

    ArrayList<Weight> weights = new ArrayList<>();
    WeightAdapter _weightAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight, container,false);
    }

//    private ListView listView;
//    private WeightAdapter wAdapter;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        weights.add(new Weight("01 Jan 2018", 63, "UP"));
        weights.add(new Weight("02 Jan 2018", 64, "DOWN"));
        weights.add(new Weight("03 Jan 2018", 63, "UP"));



        ListView _weightList =  (ListView) getView().findViewById(R.id.weight_list);

//        _weightAdapter = new WeightAdapter(this,weights);

         _weightAdapter = new WeightAdapter(
                getActivity(),
                R.layout.weight_item,
                weights
        );
        _weightList.setAdapter(_weightAdapter);


        initAddWeightBtn();

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
     void addWeight(Weight weight){
        weights.add(weight);
    }
}
