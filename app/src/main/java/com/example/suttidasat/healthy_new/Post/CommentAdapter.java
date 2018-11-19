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

public class CommentAdapter extends ArrayAdapter {

    ArrayList<JSONObject> posts;
    Context context;

    public CommentAdapter(Context context, int resource, ArrayList<JSONObject> objects){
        super(context, resource, objects);
        this.posts = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View _commentItem = LayoutInflater.from(context)
                .inflate(R.layout.comment_item, parent, false);

        JSONObject postObj = posts.get(position);

        TextView _postId = _commentItem.findViewById(R.id.com_id);
        TextView _body = _commentItem.findViewById(R.id.com_body);
        TextView _name = _commentItem.findViewById(R.id.com_name);
        TextView _email = _commentItem.findViewById(R.id.com_mail);

        try {
            _postId.setText(postObj.getString("postId") + " : " + postObj.getString("id"));
            _body.setText(postObj.getString("body"));
            _name.setText(postObj.getString("name"));
            _email.setText("( "+postObj.getString("email")+" )");
        } catch (JSONException e) {
            Log.d("POSTADAPTER", "ERROR SET VALUE");
        }

        return _commentItem;
    }
}
