package com.example.suttidasat.healthy_new.Sleep;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
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

import static android.content.Context.MODE_PRIVATE;

import com.example.suttidasat.healthy_new.R;


public class SleepFormFragment extends Fragment {

    public SleepFormFragment() {
        cv = new ContentValues();
    }

    private SQLiteDatabase database;
    private ContentValues cv;
    SharedPreferences sp;

    public View onCreateView
            (@NonNull LayoutInflater inflater,
             @Nullable ViewGroup container,
             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_sleep_form, container, false);
    }

    @Override
    public void onActivityCreated
            (@Nullable Bundle savedInstanceState) {


        super.onActivityCreated(savedInstanceState);


        chkSelected();
        backBtn();
        saveBtn();
    }

    void backBtn() {
        Button back = getView().findViewById(R.id.back_to_sleepItem);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LOGIN", "Back to sleep item");

                sp = getContext().getSharedPreferences("selected_sleep", MODE_PRIVATE);
                sp.edit().clear().commit();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new SleepFragment())
                        .commit();
                Toast.makeText(getActivity(), "Back to sleep item"
                        , Toast.LENGTH_SHORT)
                        .show();
            }
        });


    }

    void saveBtn() {

        Button save = getView().findViewById(R.id.record_sleep);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText sleepTimeEdit = getView().findViewById(R.id.form_sleep_time);
                EditText wakeupTimeEdit = getView().findViewById(R.id.form_wake_time);
                EditText dateEdit = getView().findViewById(R.id.form_sleep_date);


                String date = dateEdit.getText().toString();

                String time_sleep = sleepTimeEdit.getText().toString();
                String time_wake = wakeupTimeEdit.getText().toString();

                if (date.isEmpty() || time_sleep.isEmpty() || time_wake.isEmpty()) {
                    Log.d("User", "ระบุข้อมูลไม่ครบถ้วน");

                    Toast.makeText(
                            getActivity(),
                            "ระบุข้อมูลไม่ครบถ้วน",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    /// cal diff time

                    String[] _sleepArray = time_sleep.split(":");
                    String[] _wakeArray = time_wake.split(":");


                    String hour, minute;

                    int intSleep = (Integer.parseInt(_sleepArray[0])) * 3600 + (Integer.parseInt(_sleepArray[1])) * 60;
                    int intWake = (Integer.parseInt(_wakeArray[0]) * 3600) + (Integer.parseInt(_wakeArray[1]) * 60);

                    if (intSleep > intWake) {
                        hour = String.valueOf(Math.round(86400 - (intSleep - intWake)) / 3600);
                        minute = String.valueOf(Math.round((86400 - (intSleep - intWake)) % 3600) / 60);

                    } else {
                        hour = String.valueOf(Math.round(intWake - intSleep) / 3600);
                        minute = String.valueOf(Math.round((intWake - intSleep) % 3600) / 60);
                    }
                    if (hour.length() == 1){
                        hour = "0"+hour;
                    }
                    if (minute.length() == 1){
                        minute = "0"+minute;
                    }

                    String amountDiff = hour + ":" + minute;

                    database = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);

                    database.execSQL(
                            "CREATE TABLE IF NOT EXISTS sleeps (_id INTEGER PRIMARY KEY AUTOINCREMENT, sleep_time VARCHAR(5), wake_time VARCHAR(5), date VARCHAR(11) , diff VARCHAR(5))"
                    );

                    Sleep item = new Sleep();

                    item.setContent(date, time_sleep, time_wake, amountDiff);

                    cv = item.getContent();


                    /// check if from selected

                    sp = getContext().getSharedPreferences("selected_sleep", MODE_PRIVATE);
                    int id = sp.getInt("id", -1);

                    if (id != -1) { //update from selected

                        database.update("sleeps", cv, "_id=" + id, null);
                        Log.d("SLEEP_FORM", "UPDATE ALREADY");

                        Toast.makeText(getActivity(), "Update Success"
                                , Toast.LENGTH_SHORT)
                                .show();

                        sp.edit().clear().commit();

                    } else { // add new sleep

                        database.insert("sleeps", null, cv);
                        Log.d("SLEEP_FORM", "INSERT ALREADY");

                        Toast.makeText(getActivity(), "Save Success"
                                , Toast.LENGTH_SHORT)
                                .show();

                    }
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new SleepFragment())
                            .commit();

                }


            }

        });
    }

    void chkSelected() {


        sp = getContext().getSharedPreferences("selected_sleep", MODE_PRIVATE);
        int id = sp.getInt("id", -1);
        String date = sp.getString("Date", null);


        if (id != -1) {
            EditText sleepTimeEdit = getView().findViewById(R.id.form_sleep_time);
            EditText wakeupTimeEdit = getView().findViewById(R.id.form_wake_time);
            EditText dateEdit = getView().findViewById(R.id.form_sleep_date);

            sleepTimeEdit.setText(sp.getString("sleep_time", null));
            wakeupTimeEdit.setText(sp.getString("wake_time", null));
            dateEdit.setText(date);

            System.out.println(id + "  aew");
        } else {
            return;
        }
    }
}
