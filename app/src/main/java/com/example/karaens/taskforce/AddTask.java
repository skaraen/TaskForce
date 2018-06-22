package com.example.karaens.taskforce;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddTask extends AppCompatActivity {

   EditText at_heading,at_dscrptn;
   Button red,yellow,blue,at_add;
   int colorCode=0;
   DatabaseHelper myDb=new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        at_heading=findViewById(R.id.at_heading);
        at_dscrptn=findViewById(R.id.at_dscrptn);
        red=findViewById(R.id.red);
        blue=findViewById(R.id.blue);
        yellow=findViewById(R.id.yellow);
        at_add=findViewById(R.id.at_add);

        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorCode=1;
                at_heading.setBackgroundColor(getResources().getColor(R.color.hBlue));
                at_dscrptn.setBackgroundColor(getResources().getColor(R.color.dBlue));
            }
        });
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorCode=2;
                at_heading.setBackgroundColor(getResources().getColor(R.color.hYellow));
                at_dscrptn.setBackgroundColor(getResources().getColor(R.color.dYellow));
            }
        });
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorCode=3;
                at_heading.setBackgroundColor(getResources().getColor(R.color.hRed));
                at_dscrptn.setBackgroundColor(getResources().getColor(R.color.dRed));
            }
        });
        at_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(colorCode!=0){
                    if(at_heading.getText()!=null&&at_dscrptn.getText()!=null) {
                        Task_element task = new Task_element(colorCode,at_heading.getText().toString(),at_dscrptn.getText().toString());
                        if(myDb.addData(String.valueOf(TDList.TaskList.size()),at_heading.getText().toString(),
                                at_dscrptn.getText().toString(),colorCode)){
                            TDList.TaskList.add(task);
                            Toast.makeText(getBaseContext(),"Task added to list !",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(AddTask.this,TDList.class));
                        }
                        else
                            Toast.makeText(getBaseContext(),"Problem adding task !",Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(getBaseContext(),"Fill all the fields !",Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getBaseContext(),"Assign a theme to proceed !",Toast.LENGTH_LONG).show();
            }
        });

    }
}
