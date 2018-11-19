package com.example.lab203_07.healthy.RestApi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.lab203_07.healthy.R;
import com.example.lab203_07.healthy.fragments.MenuFragment;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommentFragment extends Fragment {

    private JSONArray jsonArray;
    private int idPost;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        Log.d("COMMENT", "bundle "+bundle);
        idPost = bundle.getInt("post_id");
        callFromServer();
        initBack();
    }

    private void callFromServer() {

        AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                OkHttpClient _client = new OkHttpClient();
                try {
                    final Request _request = new Request.Builder()
                            .url("https://jsonplaceholder.typicode.com/posts/"+ idPost +"/comments").build();
                    Log.d("COMMENT", "Request success : "+_request);
                    jsonArray = new JSONArray(_client.newCall(_request).execute().body().string());
                    Log.d("COMMENT", "Response success : "+"\n"+jsonArray);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("COMMENT", "Request exception : "+e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                try{
                    final ArrayList<JSONObject> jsonObjects = new ArrayList<>();
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        jsonObjects.add(jsonObject);
                    }
                    ListView _listView = getView().findViewById(R.id.list_comment);
                    final CommentAdapter _commentAdapter = new CommentAdapter(getActivity(), R.layout.fragment_comment_item, jsonObjects);
                    _listView.setAdapter(_commentAdapter);
                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("COMMENT", "Request exception : "+e.getMessage());
                }
            }
        };
        asyncTask.execute();
    }

    private void initBack(){
        Button _backBtn = getView().findViewById(R.id.back_btn_comment);
        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_view, new MenuFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
