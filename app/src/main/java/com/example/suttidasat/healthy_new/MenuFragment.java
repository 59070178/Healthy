package com.example.suttidasat.healthy_new;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.suttidasat.healthy_new.weight.WeightFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MenuFragment extends Fragment {

    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);    }

    ArrayList<String> _menu = new ArrayList<>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();

        _menu.add("BMI");
        _menu.add("Weight");
        _menu.add("Sing Out");

        final ArrayAdapter<String> _menuAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                _menu
        );

        ListView _menuList = (ListView) getView().findViewById(R.id.menu_list);
        _menuList.setAdapter(_menuAdapter);

        _menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("MENU", "Click on menu = "+_menu.get(i));

                if (_menu.get(i).equals("BMI")){
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new BMIFragment())

                            .commit();

                    Log.d("USER", "GOTO BMI");
                    Toast.makeText
                            (getContext(),"GO TO BMI",Toast.LENGTH_SHORT)
                            .show();

                }else if (_menu.get(i).equals("Weight")){
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new WeightFragment())

                            .commit();

                    Log.d("USER", "GOTO Weight");
                    Toast.makeText
                            (getContext(),"GO TO MENU",Toast.LENGTH_SHORT)
                            .show();
                }else {
                    firebaseAuth.signOut();

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new LoginFragment())

                            .commit();

                    Log.d("USER", "GOTO Login Page");
                    Toast.makeText
                            (getContext(),"SING OUT SUCCESS, GO TO LOGIN",Toast.LENGTH_SHORT)
                            .show();

                }



            }
        });

    }


}
