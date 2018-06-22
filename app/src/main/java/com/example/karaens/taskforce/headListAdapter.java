package com.example.karaens.taskforce;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class headListAdapter extends ArrayAdapter<Task_element> {
    int mResource;
    Context mContext;
    TextView hl_text;

    public headListAdapter(@NonNull Context context, int resource, @NonNull List<Task_element> objects) {
        super(context, resource, objects);
        mResource = resource;
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String head=getItem(position).getHeading();
        int code=getItem(position).getCode();

        LayoutInflater inflater=LayoutInflater.from(mContext);
        if(convertView==null){
            convertView=inflater.inflate(mResource,parent,false);
        }
        hl_text=convertView.findViewById(R.id.hl_textView);
        hl_text.setText(head);
        switch (code){
            case 1:
                hl_text.setBackgroundColor(mContext.getResources().getColor(R.color.hBlue));
                break;
            case 2:
                hl_text.setBackgroundColor(mContext.getResources().getColor(R.color.hYellow));
                break;
            case 3:
                hl_text.setBackgroundColor(mContext.getResources().getColor(R.color.hRed));
                break;
                }
        return convertView;
    }
}
