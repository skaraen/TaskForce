package com.example.karaens.taskforce;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.Toast;

public class WidgetConfig extends AppCompatActivity {

    ListView hl_listView;
    int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    String KEY_ID = "key";
    static SharedPreferences prefs;
    static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_config);

        prefs = getSharedPreferences("SharedPref", MODE_PRIVATE);
        editor = prefs.edit();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        Intent res = new Intent();
        res.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_CANCELED, res);

        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        hl_listView = findViewById(R.id.hl_listView);
        TDList.restore(this);
        headListAdapter hlAdapter = new headListAdapter(this, R.layout.headings_layout, TDList.TaskList);
        hl_listView.setAdapter(hlAdapter);

        hl_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getBaseContext());
                Intent intent = new Intent(WidgetConfig.this, Element.class);
                intent.putExtra("posW" + String.valueOf(appWidgetId), position);
                intent.setAction(KEY_ID + appWidgetId);
                PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_layout);
                views.setOnClickPendingIntent(R.id.widget_rl, pendingIntent);
                views.setCharSequence(R.id.widget_text, "setText", TDList.TaskList.get(position).getHeading());
                switch (TDList.TaskList.get(position).getCode()) {
                    case 1:
                        views.setInt(R.id.widget_rl, "setBackgroundColor", getResources().getColor(R.color.hBlue));
                        break;
                    case 2:
                        views.setInt(R.id.widget_rl, "setBackgroundColor", getResources().getColor(R.color.hYellow));
                        break;
                    case 3:
                        views.setInt(R.id.widget_rl, "setBackgroundColor", getResources().getColor(R.color.hRed));
                        break;
                }

                Log.d("wid", String.valueOf(prefs.getInt("WID" + position, -1)));
                if (prefs.getInt("WID" + position, -1) == -1) {
                    editor.putInt("WID" + position, appWidgetId);
                    editor.putInt("SF" + appWidgetId, position);
                    editor.apply();
                    appWidgetManager.updateAppWidget(appWidgetId, views);
                    Intent res = new Intent();
                    res.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                    setResult(RESULT_OK, res);

                    finish();
                } else
                    Toast.makeText(getBaseContext(), "Widget already present", Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        for(int i=0;i<TDList.TaskList.size();i++){
            editor.putInt("WID"+i,-1);
            editor.apply();
        }
    }

    static void sharedPrefsInit(Context context){
        prefs = context.getSharedPreferences("SharedPref", MODE_PRIVATE);
        editor = prefs.edit();
    }
}
