package com.example.lab203_07.healthy.RestApi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lab203_07.healthy.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class PostAdapter extends ArrayAdapter {

    private ArrayList<JSONObject> _jsonPosts = new ArrayList<>();
    private Context context;

    public PostAdapter(@NonNull Context context, int resource, @NonNull ArrayList<JSONObject> _jsonPosts) {
        super(context, resource, _jsonPosts);
        Log.d("POST_ADAP", "call adapter list" +_jsonPosts);
        this.context = context;
        this._jsonPosts = _jsonPosts;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.d("POST_ADAP", "jsonList position : "+position);
        View postView = LayoutInflater.from(context).inflate(R.layout.fragment_post_item, parent, false);

        TextView _id = postView.findViewById(R.id.id_post);
        TextView _title = postView.findViewById(R.id.title_post);
        TextView _body = postView.findViewById(R.id.body_post);

        try {
            JSONObject _jsonPost = _jsonPosts.get(position);
            Log.d("POST_ADAP", "jsonList : "+_jsonPost);
            _id.setText(_jsonPost.getString("id")+" ");
            _title.setText(" "+_jsonPost.getString("title"));
            _body.setText(_jsonPost.getString("body"));
        }catch (Exception e){
            e.printStackTrace();
        }

        return postView;
    }
}
