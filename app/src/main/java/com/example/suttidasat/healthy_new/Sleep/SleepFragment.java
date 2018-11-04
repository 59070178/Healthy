package com.example.suttidasat.healthy_new.Sleep;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.suttidasat.healthy_new.MenuFragment;
import com.example.suttidasat.healthy_new.R;
import com.example.suttidasat.healthy_new.weight.Weight;
import com.example.suttidasat.healthy_new.weight.WeightFromFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import static android.content.Context.MODE_PRIVATE;

public class SleepFragment extends Fragment {

    ArrayList<Sleep> sleepList = new ArrayList<>();
    String cDate,cSleep,cWake;
    private SQLiteDatabase database;
//    private Cursor db_query;

    private ListView listView;
    private SleepAdapter sleepAdapter;
    int position;


    @Nullable
    @Override

    public View onCreateView
            (@NonNull LayoutInflater inflater,
             @Nullable ViewGroup container,
             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sleep, container, false);
    }

    @Override
    public void onActivityCreated
            (@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView = getView().findViewById(R.id.sleep_list);
         sleepAdapter = new SleepAdapter(getActivity(), R.layout.sleep_item, sleepList);

        //open to use db
        database = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);


        //create table if not exist
        database.execSQL(
                "CREATE TABLE IF NOT EXISTS sleeps (_id INTEGER PRIMARY KEY AUTOINCREMENT, sleep_time VARCHAR(5), wake_time VARCHAR(5), date VARCHAR(11) , diff VARCHAR(5))"
        );

        String date,time_sleep,time_wake,time_diff;
        //query data
        Cursor db_query = database.rawQuery("SELECT * FROM sleeps", null);

        sleepAdapter.clear();

        while(db_query.moveToNext()) {
            int id = db_query.getInt(0);
            date = db_query.getString(3);
            time_sleep = db_query.getString(1);
            time_wake = db_query.getString(2);
            time_diff = db_query.getString(4);

            sleepList.add(new Sleep(id,date, time_sleep, time_wake,time_diff));
        }

        /// sort by date

        Collections.sort(sleepList,new DateSorter());

        listView.setAdapter(sleepAdapter);
        sleepAdapter.notifyDataSetChanged();

        //get data when clicks....

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//               id = listView.getItemIdAtPosition(position);

               Sleep sleep = new Sleep();

               sleep = (Sleep) parent.getItemAtPosition(position);
               cDate = sleep.getDate_sleep();
               cSleep = sleep.getTime_sleep();
               cWake = sleep.getTime_wake();
               int _id = sleep.getId();


               SharedPreferences.Editor sp = getContext().getSharedPreferences("selected_sleep",MODE_PRIVATE).edit();
               sp.putString("Date",cDate).apply();
               sp.putString("sleep_time",cSleep).apply();
               sp.putString("wake_time",cWake).apply();
               sp.putInt("id",_id);
               sp.commit();

               getActivity().getSupportFragmentManager()
                       .beginTransaction()
                       .addToBackStack(null)
                       .replace(R.id.main_view, new SleepFormFragment())
                       .commit();

           }
       });
        db_query.close();
        database.close();

        addBtn();
        backBtn();
    }

    void addBtn(){
        Button addSleep = getView().findViewById(R.id.add_sleep);
        addSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("User", "Go To Sleep From");
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new SleepFormFragment())
                        .addToBackStack(null)
                        .commit();

                Toast.makeText(getActivity(),"Go To Sleep From"
                        ,Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    void backBtn(){

        Button addSleep = getView().findViewById(R.id.back_to_menu);
        addSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("User", "Back To Menu");
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new MenuFragment())
                        .addToBackStack(null)
                        .commit();

                Toast.makeText(getActivity(),"Back To Menu"
                        ,Toast.LENGTH_SHORT)
                        .show();
            }
        });

    }
}
