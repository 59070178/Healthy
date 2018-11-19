package com.example.suttidasat.healthy_new.Post;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PostFragment extends Fragment {


    ListView postListView;
    PostAdapter postAdapter;
    String body;
    JSONArray jsonArray;


    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        String url = "https://jsonplaceholder.typicode.com/posts";


        getHttp();

        backBtn();


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


    void getHttp()
    {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                OkHttpClient client = new OkHttpClient();
                try {
                    Request request = new Request.Builder().url("https://jsonplaceholder.typicode.com/posts").build();
                    Response response = client.newCall(request).execute();
                    body = response.body().string();
                    jsonArray = new JSONArray(body);
                }
                catch (IOException e)
                {
                    Log.d("POST", "catch IOException : " + e.getMessage());
                }
                catch (JSONException e)
                {
                    Log.d("POST", "catch JSONException : " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                try
                {
                    final ArrayList<JSONObject> posts = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        posts.add(obj);
                    }

                    postListView = getView().findViewById(R.id.post_list);
                    postAdapter = new PostAdapter(getActivity(), R.layout.post_item, posts);
                    postListView.setAdapter(postAdapter);
                    postListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Bundle bundle = new Bundle();
                            try{
                                bundle.putInt("post id", posts.get(position).getInt("id"));
                            }
                            catch (JSONException e)
                            {
                                Log.d("POST", "catch JSONException : " + e.getMessage());
                            }
//                            getActivity().getSupportFragmentManager()
//                                    .beginTransaction()
//                                    .replace(R.id.main_view, new CommentFragment())
//                                    .addToBackStack(null)
//                                    .commit();

                                                        Fragment fragment = new CommentFragment();
                            fragment.setArguments(bundle);
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            ft.replace(R.id.main_view, fragment).addToBackStack(null).commit();

                            Toast.makeText(getActivity(),"Go To Comment"
                                    ,Toast.LENGTH_SHORT)
                                    .show();
                        }
                    });


                }
                catch (JSONException e)
                {
                    Log.d("POST", "Catch JSONException : " + e.getMessage());
                }
            }
        };
        task.execute();
    }


}
