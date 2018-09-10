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

public class WeightFromFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight_form, container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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

                    int _weightInt = Integer.parseInt(_weightStr);
                    weightFragment.addWeight(new Weight(_dateStr, _weightInt, ""));
                    Toast.makeText(
                            getActivity(),
                            "Record Success",
                            Toast.LENGTH_SHORT
                    ).show();

                }


            }
        });
    }
}
