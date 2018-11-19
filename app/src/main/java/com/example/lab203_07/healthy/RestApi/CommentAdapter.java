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

public class CommentAdapter extends ArrayAdapter {

    private ArrayList<JSONObject> _jsonComments = new ArrayList<>();
    private Context context;

    public CommentAdapter(@NonNull Context context, int resource, @NonNull ArrayList<JSONObject> _jsonComments) {
        super(context, resource, _jsonComments);
        Log.d("COMMENT_ADAP", "call adapter list" +_jsonComments);
        this.context = context;
        this._jsonComments = _jsonComments;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.d("COMMENT_ADAP", "jsonComment position : "+position);
        View postView = LayoutInflater.from(context).inflate(R.layout.fragment_comment_item, parent, false);

        TextView _idPost = postView.findViewById(R.id.id_post_comment);
        TextView _id = postView.findViewById(R.id.id_comment);
        TextView _body = postView.findViewById(R.id.body_comment);
        TextView _name = postView.findViewById(R.id.name_comment);
        TextView _email = postView.findViewById(R.id.email_comment);

        try {
            JSONObject _jsonPost = _jsonComments.get(position);
            Log.d("POST_ADAP", "jsonList : "+_jsonPost);
            _idPost.setText(_jsonPost.getString("postId")+" ");
            _id.setText(" "+_jsonPost.getString("id"));
            _body.setText(_jsonPost.getString("body"));
            _name.setText(_jsonPost.getString("name"));
            _email.setText(_jsonPost.getString("email"));
        }catch (Exception e){
            e.printStackTrace();
        }

        return postView;
    }
}
