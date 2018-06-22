package com.example.karaens.taskforce;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class TDList extends AppCompatActivity {

    Button buttonAdd,codeAll,codeRed,codeBlue,codeYellow;
    ListView listViewTasks;
    static ArrayList<Task_element> TaskList=new ArrayList<>();
    ArrayList<Task_element> splTaskList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tdlist);
        buttonAdd=findViewById(R.id.buttonAdd);
        codeAll=findViewById(R.id.codeAll);
        codeRed=findViewById(R.id.codeRed);
        codeBlue=findViewById(R.id.codeBlue);
        codeYellow=findViewById(R.id.codeYellow);
        listViewTasks=findViewById(R.id.listViewTasks);

        WidgetConfig.sharedPrefsInit(this);

        final ListAdapter adapter=new ListAdapter(this,R.layout.task_layout,TaskList);
        final ListAdapter splAdapter=new ListAdapter(this,R.layout.task_layout,splTaskList);
        listViewTasks.setAdapter(adapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TDList.this,AddTask.class));
            }
        });

        listViewTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(TDList.this,Element.class);
                intent.putExtra("pos",position);
                startActivity(intent);
            }
        });

        codeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               listViewTasks.setAdapter(adapter);
            }
        });
        codeRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               setSplList(3);
               listViewTasks.setAdapter(splAdapter);
            }
        });
        codeBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSplList(1);
                listViewTasks.setAdapter(splAdapter);
            }
        });
        codeYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSplList(2);
                listViewTasks.setAdapter(splAdapter);
            }
        });
        }

    @Override
    protected void onStart() {
        super.onStart();
        restore(this);
    }

    void setSplList(int c){
        splTaskList.clear();
        for(int i=0;i<TaskList.size();i++)
        {
          if(TaskList.get(i).getCode()==c)
          {
              splTaskList.add(TaskList.get(i));
          }
        }
    }

    static void restore(Context context){
        if(TaskList.isEmpty()){
            DatabaseHelper myDb=new DatabaseHelper(context);
            Cursor res=myDb.getData();
            while (res.moveToNext()){
                Task_element task=new Task_element(res.getInt(3),res.getString(1),res.getString(2));
                TaskList.add(task);
            }
            Toast.makeText(context,"Data restored !",Toast.LENGTH_LONG).show();
        }
    }

}
