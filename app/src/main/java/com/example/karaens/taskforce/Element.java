package com.example.karaens.taskforce;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Element extends AppCompatActivity {

    TextView eHeading,eDscrptn;
    Button eUpdate,eDelete;
    int p;
    String KEY_ID="key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element);
        eHeading=findViewById(R.id.eTextHeading);
        eDscrptn=findViewById(R.id.eTextDscrptn);
        eUpdate=findViewById(R.id.eUpdate);
        eDelete=findViewById(R.id.eDelete);
        if(TDList.TaskList.isEmpty()){
            TDList.restore(this);
        }

        p=getIntent().getIntExtra("pos",-1);

        if(p==-1){
            int appWidgetId=Integer.parseInt(getIntent().getAction().substring(KEY_ID.length()));
            p=getIntent().getIntExtra("posW"+appWidgetId,-1);
        }

        if(p<=TDList.TaskList.size()&&p!=-1) {
            eHeading.setText(TDList.TaskList.get(p).getHeading());
            eDscrptn.setText(TDList.TaskList.get(p).getDscrptn());
            switch (TDList.TaskList.get(p).getCode()) {
                case 1:
                    eHeading.setBackgroundColor(getResources().getColor(R.color.hBlue));
                    eDscrptn.setBackgroundColor(getResources().getColor(R.color.dBlue));
                    break;
                case 2:
                    eHeading.setBackgroundColor(getResources().getColor(R.color.hYellow));
                    eDscrptn.setBackgroundColor(getResources().getColor(R.color.dYellow));
                    break;
                case 3:
                    eHeading.setBackgroundColor(getResources().getColor(R.color.hRed));
                    eDscrptn.setBackgroundColor(getResources().getColor(R.color.dRed));
                    break;

            }
            eUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Element.this, UpdateTask.class);
                    intent.putExtra("index", p);
                    startActivity(intent);
                }
            });
            eDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHelper myDb = new DatabaseHelper(getBaseContext());
                    if (myDb.deleteData(String.valueOf(p)) > 0) {
                        if(WidgetConfig.prefs.getInt("WID"+p,-1)!=-1){
                          WidgetProvider.widgetUpdate(WidgetConfig.prefs.getInt("WID"+p,-1),p,1,getBaseContext());
                        }
                        TDList.TaskList.remove(p);
                        Task_element task;
                        for (int i = p; i < TDList.TaskList.size(); i++) {
                            int WID=WidgetConfig.prefs.getInt("WID"+(p+1),-1);
                            if(WID!=-1){
                                WidgetConfig.editor.putInt("WID"+p,WID);
                                WidgetConfig.editor.putInt("WID"+(p+1),-1);
                                WidgetConfig.editor.apply();
                            }
                            task = TDList.TaskList.get(i);
                            myDb.addData(String.valueOf(i), task.getHeading(), task.getDscrptn(), task.getCode());
                            myDb.deleteData(String.valueOf(i + 1));
                        }
                        Toast.makeText(getBaseContext(), "Deletion successful", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Element.this, TDList.class));
                    }
                    else
                        Toast.makeText(getBaseContext(), "Deletion unsuccessful", Toast.LENGTH_LONG).show();
                }
            });
        }
        else if(p>TDList.TaskList.size())
            Toast.makeText(getBaseContext(), "The task does not exist any longer", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(TDList.TaskList.isEmpty()){
            TDList.restore(this);
        }
    }
}
