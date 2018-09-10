package com.example.suttidasat.healthy_new;

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
import android.widget.TextView;
import android.widget.Toast;

public class BMIFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bmi, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initCalBtn();
        initBackBtn();
    }

    void initCalBtn (){

        Button _calBtn = getView().findViewById(R.id.btn_cal);
        _calBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText _heigth = getView().findViewById(R.id.bmi_height);
                EditText _weight = getView().findViewById(R.id.bmi_weight);

                String _heightStr = _heigth.getText().toString();
                String _weightStr = _weight.getText().toString();

                int _heightInt = Integer.parseInt(_heightStr);
                int _weightInt = Integer.parseInt(_weightStr);
                float _heightFloat = Float.parseFloat(_heightStr);
                float _result = _weightInt/((_heightFloat/10)*(_heightFloat/10));

                if (_heightStr.isEmpty() || _weightStr.isEmpty()){
                    Toast.makeText(

                            getActivity(),
                            "กรุณาระบุข้อมูลให้ครบถ้วน",
                            Toast.LENGTH_SHORT
                    ).show();
                    Log.d("USER", "FIELD NAME IS EMPTY");
                }else {

                    TextView ans = getView().findViewById(R.id.ans_bmi);
                    ans.setText(_result + "");
                    Log.d("user","BMI IS VALUE");
                }

            }
        });
    }

    void initBackBtn(){
        Button _backBtn = getView().findViewById(R.id.bmi_backBnt);
        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new MenuFragment())
                        .commit();
                Log.d("USER", "GOTO MENU");
            }
        });

    }
}
