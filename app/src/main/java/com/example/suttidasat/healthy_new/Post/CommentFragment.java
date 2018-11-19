package com.example.suttidasat.healthy_new.Post;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.example.suttidasat.healthy_new.R;

public class CommentFragment extends Fragment {

    ListView commentListView;
    int id;
    String body;
    JSONArray jsonArray;
    CommentAdapter commentAdapter;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comment, container, false);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        Bundle bundle = getArguments();
        id = bundle.getInt("post id");

        initRestAPI();
        backBtn();


    }
    void backBtn(){

        Button back = getView().findViewById(R.id.back_to_post);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("User", "Back To Menu");
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new PostFragment())
                        .addToBackStack(null)
                        .commit();

                Toast.makeText(getActivity(),"Back To Posts"
                        ,Toast.LENGTH_SHORT)
                        .show();
            }
        });

    }

    void initRestAPI()
    {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                OkHttpClient client = new OkHttpClient();
                try {
                    String url = "https://jsonplaceholder.typicode.com/posts/" + id + "/comments";
                    Request request = new Request.Builder().url(url).build();
                    Response response = client.newCall(request).execute();
                    body = response.body().string();
                    jsonArray = new JSONArray(body);
                }
                catch (IOException e)
                {
                    Log.d("COMMENT", "catch IOException : " + e.getMessage());
                }
                catch (JSONException e)
                {
                    Log.d("COMMENT", "catch JSONException : " + e.getMessage());
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                try
                {
                    final ArrayList<JSONObject> commentList = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        commentList.add(obj);
                    }

                     commentListView = getView().findViewById(R.id.comment_list);
                    commentAdapter = new CommentAdapter(getContext(), R.layout.comment_item, commentList);
                    commentListView.setAdapter(commentAdapter);

//                    JSONObject postObj = commentList.get(0);
//
//                    TextView _postId = getView().findViewById(R.id.com_id);
//                    TextView _body = getView().findViewById(R.id.com_body);
//                    TextView _name = getView().findViewById(R.id.com_name);
//                    TextView _email = getView().findViewById(R.id.com_mail);
//
//                    _postId.setText(postObj.getString("postId") + " : " + postObj.getString("id"));
//                    _postId.setText(postObj.getString("postId") + " : " + postObj.getString("id"));
//                    _body.setText(postObj.getString("body"));
//                    _name.setText(postObj.getString("name"));
//                    _email.setText("( "+postObj.getString("email")+" )");


                }
                catch (JSONException e)
                {
                    Log.d("COMMENT", "catch JSONException : " + e.getMessage());
                }
            }
        };
        task.execute();
    }
}
