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

import com.example.suttidasat.healthy_new.weight.WeightFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterFragment extends Fragment {

    private FirebaseAuth fbAuth;

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

        fbAuth = FirebaseAuth.getInstance();
        initRegisterBtn();

    }

    void initRegisterBtn(){
        Button _regBtn = getView().findViewById(R.id.reg_register);
        _regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText _regEmail = getView().findViewById(R.id.reg_email);
                EditText _regPsw = getView().findViewById(R.id.reg_password);
                EditText _regRePsw = getView().findViewById(R.id.reg_rePassword);

                String _regEmailStr = _regEmail.getText().toString();
                String _passwordStr = _regPsw.getText().toString();
                String _regRePswStr = _regRePsw.getText().toString();

                if (_passwordStr.isEmpty() || _regEmailStr.isEmpty() ||_regRePswStr.isEmpty()) {

                    Toast.makeText(
                            getActivity(),
                            "กรุณาระบุข้อมูลให้ครบถ้วน",
                            Toast.LENGTH_SHORT
                    ).show();
                    Log.d("USER","Some FIELD IS EMPTY" );

                } else if (!_passwordStr.equals(_regRePswStr)){
                    Toast.makeText(
                            getActivity(),
                            "Password not equals Re-Password",
                            Toast.LENGTH_SHORT
                    ).show();
                    Log.d("USER","Password not equals Re-Password" );
                } else if (_passwordStr.length() < 6){
                    Toast.makeText(
                            getActivity(),
                            "Password should more than 5",
                            Toast.LENGTH_SHORT
                    ).show();
                    Log.d("USER","Password should more than 5" );
                }else {


                    fbAuth.createUserWithEmailAndPassword(_regEmailStr,_passwordStr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {


                            sendVerifiedEmail(authResult.getUser());
                            Toast.makeText(
                                    getActivity(),
                                    "Register is success",
                                    Toast.LENGTH_SHORT
                            ).show();
                            Log.d("Register","Success" );


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(getActivity(),"ERROR = " + e.getMessage()
                                    ,Toast.LENGTH_SHORT)
                                    .show();

                            Log.d("Register","Fail" );

                        }
                    });
                }
            }
        });
    }

    /// return user that finish login

    void sendVerifiedEmail(FirebaseUser _user){
        _user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                fbAuth.getInstance().signOut();
                Log.d("LOGIN", "Send verify e-mail successful");
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.main_view, new LoginFragment())
                        .commit();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getActivity(),"ERROR = " + e.getMessage()
                        ,Toast.LENGTH_SHORT)
                        .show();

                Log.d("LOGIN", "Send vefiry e-mail failure");

            }
        });
    }


}

