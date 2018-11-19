package com.example.suttidasat.healthy_new.Post;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.suttidasat.healthy_new.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PostAdapter extends ArrayAdapter {

    private ArrayList<JSONObject> postList;
    private Context context;

    public PostAdapter(Context context, int resource, ArrayList<JSONObject> objects)
    {
        super(context, resource, objects);
        this.postList = objects;
        this.context = context;
    }

    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {


        View postItem  = LayoutInflater.from(context)
                .inflate(R.layout.post_item,parent,false);

        JSONObject postObj = postList.get(position);

        TextView id_title = (TextView) postItem.findViewById(R.id.post_id_title);
        TextView body = (TextView) postItem.findViewById(R.id.post_body);


        try
        {
            id_title.setText(postObj.getString("id") + " : " + postObj.getString("title"));
            body.setText(postObj.getString("body"));
        }
        catch (JSONException e)
        {
            Log.d("Posts", "catch JSONException : " + e.getMessage());
        }
        return postItem;

    }
}

