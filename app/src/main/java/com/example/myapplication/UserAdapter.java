package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends BaseAdapter {

    private Context context; //context
    private ArrayList<User> users;
    private LayoutInflater inflater;
    private int layout;
    public UserAdapter(Context context, int resource, ArrayList<User> users) {

        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final UserAdapter.ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new UserAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (UserAdapter.ViewHolder) convertView.getTag();
        }
        final User user = users.get(position);

        viewHolder.username.setText(user.getUsername() + "(" + user.getUserId() + ")");
        return convertView;
    }
    private class ViewHolder {


        final TextView username;
        ViewHolder(View view){
            username = view.findViewById(R.id.username);
        }
    }


}
