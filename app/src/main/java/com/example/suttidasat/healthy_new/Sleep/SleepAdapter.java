package com.example.suttidasat.healthy_new.Sleep;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.suttidasat.healthy_new.R;
import com.example.suttidasat.healthy_new.weight.Weight;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SleepAdapter extends ArrayAdapter<Sleep> {

    private List<Sleep> sleepList;
    private Context context;

    public SleepAdapter(@NonNull Context context, int resource, @NonNull List<Sleep> objects){
        super(context, resource, objects);
        this.sleepList = objects;
        this.context = context;
    }

    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {


        View sleepItem = LayoutInflater.from(context).inflate(R.layout.sleep_item,parent,false);

        TextView sleep_date = (TextView) sleepItem.findViewById(R.id.sleep_date);
        TextView sleep_and_wake = (TextView) sleepItem.findViewById(R.id.sleep_wake);
        TextView amount_time = (TextView) sleepItem.findViewById(R.id.amount_time);

        Sleep sl = sleepList.get(position);


        sleep_date.setText(sl.getDate_sleep());
        sleep_and_wake.setText(sl.getTime_sleep() + "-" + sl.getTime_wake());
        amount_time.setText(sl.getDiff());

        return sleepItem;

    }

}
