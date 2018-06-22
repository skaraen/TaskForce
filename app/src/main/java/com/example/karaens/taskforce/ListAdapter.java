package com.example.karaens.taskforce;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends ArrayAdapter<Task_element>{

    int mResource;
    Context mContext;
    String mHeading,mDscrptn;
    int mCode;
    TextView textHeading,textDscrptn;

    public ListAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        mResource = resource;
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       mHeading=getItem(position).getHeading();
       mDscrptn=getItem(position).getDscrptn();
       mCode=getItem(position).getCode();

       LayoutInflater inflater=LayoutInflater.from(mContext);
        if(convertView==null){
            convertView=inflater.inflate(mResource,parent,false);
        }

        textHeading=convertView.findViewById(R.id.textHeading);
        textDscrptn=convertView.findViewById(R.id.textDscrptn);

        textHeading.setText(mHeading);
        textDscrptn.setText(mDscrptn);

        switch (mCode){
            case 1:
                 textHeading.setBackgroundColor(mContext.getResources().getColor(R.color.hBlue));
                 textDscrptn.setBackgroundColor(mContext.getResources().getColor(R.color.dBlue));
                 break;
            case 2:
                textHeading.setBackgroundColor(mContext.getResources().getColor(R.color.hYellow));
                textDscrptn.setBackgroundColor(mContext.getResources().getColor(R.color.dYellow));
                break;
            case 3:
                textHeading.setBackgroundColor(mContext.getResources().getColor(R.color.hRed));
                textDscrptn.setBackgroundColor(mContext.getResources().getColor(R.color.dRed));
                break;
        }

        return convertView;
    }
}
