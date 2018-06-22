package com.example.karaens.taskforce;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateTask extends AppCompatActivity {

    EditText ut_heading,ut_dscrptn;
    Button red,yellow,blue,ut_update;
    int cCode,index;
    Task_element task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        ut_heading=findViewById(R.id.ut_heading);
        ut_dscrptn=findViewById(R.id.ut_dscrptn);
        red=findViewById(R.id.red);
        blue=findViewById(R.id.blue);
        yellow=findViewById(R.id.yellow);
        ut_update=findViewById(R.id.ut_update);

         index=getIntent().getIntExtra("index",0);
         task=TDList.TaskList.get(index);
         ut_heading.setText(task.getHeading());
         ut_dscrptn.setText(task.getDscrptn());
         cCode=task.getCode();

         switch (cCode) {
             case 1:
                 ut_heading.setBackgroundColor(getResources().getColor(R.color.hBlue));
                 ut_dscrptn.setBackgroundColor(getResources().getColor(R.color.dBlue));
                 break;
             case 2:
                 ut_heading.setBackgroundColor(getResources().getColor(R.color.hYellow));
                 ut_dscrptn.setBackgroundColor(getResources().getColor(R.color.dYellow));
                 break;
             case 3:
                 ut_heading.setBackgroundColor(getResources().getColor(R.color.hRed));
                 ut_dscrptn.setBackgroundColor(getResources().getColor(R.color.dRed));
                 break;
         }

        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cCode=1;
                ut_heading.setBackgroundColor(getResources().getColor(R.color.hBlue));
                ut_dscrptn.setBackgroundColor(getResources().getColor(R.color.dBlue));
            }
        });
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cCode=2;
                ut_heading.setBackgroundColor(getResources().getColor(R.color.hYellow));
                ut_dscrptn.setBackgroundColor(getResources().getColor(R.color.dYellow));
            }
        });
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cCode=3;
                ut_heading.setBackgroundColor(getResources().getColor(R.color.hRed));
                ut_dscrptn.setBackgroundColor(getResources().getColor(R.color.dRed));
            }
        });
        ut_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper myDb=new DatabaseHelper(getBaseContext());

                if(ut_heading.getText()!=null&&ut_dscrptn!=null)
                {
                    if(myDb.updateData(String.valueOf(index),ut_heading.getText().toString(),ut_dscrptn.getText().toString(),cCode))
                    {
                        task=new Task_element(cCode,ut_heading.getText().toString(),ut_dscrptn.getText().toString());
                        TDList.TaskList.set(index,task);
                        int WID=WidgetConfig.prefs.getInt("WID"+index,-1);
                        if(WID!=-1){
                            WidgetProvider.widgetUpdate(WID,index,2,getBaseContext());
                        }
                        Toast.makeText(getBaseContext(),"Task updated !",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(UpdateTask.this,TDList.class));
                    }
                }
            }
        });
    }
}
