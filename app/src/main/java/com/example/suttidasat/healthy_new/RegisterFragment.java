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
import android.widget.Toast;

public class RegisterFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRegisterBtn();
    }

    void initRegisterBtn(){
        Button _regBtn = getView().findViewById(R.id.reg_register);
        _regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText _regUserId = getView().findViewById(R.id.reg_userId);
                EditText _regName = getView().findViewById(R.id.reg_name);
                EditText _regAge = getView().findViewById(R.id.reg_age);
                EditText _regPsw = getView().findViewById(R.id.reg_password);

                String _userIdStr = _regUserId.getText().toString();
                String _passwordStr = _regPsw.getText().toString();
                String _regNameStr = _regName.getText().toString();
                String __regAgeStr = _regAge.getText().toString();

                if (_passwordStr.isEmpty()){

                    Toast.makeText(
                            getActivity(),
                            "กรุณาระบุข้อมูลให้ครบถ้วน",
                            Toast.LENGTH_SHORT
                    ).show();
                    Log.d("USER","FIELD Password IS EMPTY" );

                }else if (__regAgeStr.isEmpty()){
                    Toast.makeText(
                            getActivity(),
                            "กรุณาระบุข้อมูลให้ครบถ้วน",
                            Toast.LENGTH_SHORT
                    ).show();
                    Log.d("USER","FIELD Age IS EMPTY" );
                }else if ( _regNameStr.isEmpty()){
                    Toast.makeText(
                            getActivity(),
                            "กรุณาระบุข้อมูลให้ครบถ้วน",
                            Toast.LENGTH_SHORT
                    ).show();
                    Log.d("USER","FIELD Name IS EMPTY" );
                }else if ( _userIdStr.isEmpty()){
                    Toast.makeText(
                            getActivity(),
                            "กรุณาระบุข้อมูลให้ครบถ้วน",
                            Toast.LENGTH_SHORT
                    ).show();
                    Log.d("USER","FIELD User Id IS EMPTY" );
                }
                else if (_userIdStr.equals("admin")){
                    Toast.makeText(
                            getActivity(),
                            "user นี้มีอยู่ในระบบแล้ว",
                            Toast.LENGTH_SHORT
                    ).show();
                    Log.d("USER","USER ALREADY EXIST" );
                }else {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new BMIFragment())
                            .addToBackStack(null)
                            .commit();
                    Log.d("USER", "GOTO BMI");
                }

            }
        });


    }
}

