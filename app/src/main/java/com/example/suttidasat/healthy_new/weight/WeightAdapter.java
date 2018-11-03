package com.example.suttidasat.healthy_new.weight;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.suttidasat.healthy_new.R;

import java.util.ArrayList;
import java.util.List;

public class WeightAdapter extends ArrayAdapter {

    List<Weight> WeightList = new ArrayList<Weight>();
    Context context;


    public WeightAdapter(Context context, int resource, List<Weight> objects) {
        super(context, resource, objects);
        this.WeightList = objects;
        this.context = context;
    }



    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {

        View listItem = LayoutInflater.from(context).inflate(R.layout.weight_item,parent,false);

        TextView _shDate = (TextView) listItem.findViewById(R.id.show_date);
        TextView _shWeight = (TextView) listItem.findViewById(R.id.show_weight);
        TextView _shStatus = (TextView) listItem.findViewById(R.id.show_status);

        Weight _w = WeightList.get(position);

        _shDate.setText(_w.getDate());
        _shWeight.setText(_w.getWeight()+" kg.");
//        _shStatus.setText(_w.getStatus());

        if (position > 0) {
            Weight _prevRow = WeightList.get(position - 1);
            if (_prevRow.weight > _w.weight) {
                _shStatus.setText("DOWN");
            } else if (_w.weight > _prevRow.weight) {
                _shStatus.setText("UP");
            }else{
                _shStatus.setText("");
            }
        }else {
            _shStatus.setText(" ");
        }
        return listItem;
    }
}
