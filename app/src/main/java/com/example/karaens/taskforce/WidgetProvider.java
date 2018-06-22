package com.example.karaens.taskforce;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class WidgetProvider extends AppWidgetProvider {

    String KEY_ID="key";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for(int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context, Element.class);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

            WidgetConfig.sharedPrefsInit(context);

            if (appWidgetId != -1) {
                int pos = WidgetConfig.prefs.getInt("SF" + appWidgetId, -1);
                if(pos!=-1) {
                    views.setCharSequence(R.id.widget_text, "setText", TDList.TaskList.get(pos).getHeading());
                    intent.putExtra("posW" + appWidgetId, pos);
                    intent.setAction(KEY_ID + String.valueOf(appWidgetId));
                    switch (TDList.TaskList.get(pos).getCode()) {
                        case 1:
                            views.setInt(R.id.widget_rl, "setBackgroundColor", context.getResources().getColor(R.color.hBlue));
                            break;
                        case 2:
                            views.setInt(R.id.widget_rl, "setBackgroundColor", context.getResources().getColor(R.color.hYellow));
                            break;
                        case 3:
                            views.setInt(R.id.widget_rl, "setBackgroundColor", context.getResources().getColor(R.color.hRed));
                            break;
                    }
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    views.setOnClickPendingIntent(R.id.widget_rl, pendingIntent);
                }
                appWidgetManager.updateAppWidget(appWidgetId, views);
            }
        }
    }

    static void widgetUpdate(int appWidgetId,int pos,int flag,Context context){
        AppWidgetManager widgetManager=AppWidgetManager.getInstance(context);
        RemoteViews views=new RemoteViews(context.getPackageName(),R.layout.widget_layout);
        if (flag==1) {
            views.setCharSequence(R.id.widget_text, "setText", "Deleted");
            views.setInt(R.id.widget_rl, "setBackgroundColor", context.getResources().getColor(R.color.dGrey));
            Intent intent=new Intent();
            PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,0);
            views.setOnClickPendingIntent(R.id.widget_rl,pendingIntent);
            widgetManager.updateAppWidget(appWidgetId,views);
            WidgetConfig.editor.putInt("WID"+pos,-1);
            WidgetConfig.editor.apply();
        }
        else{
            views.setCharSequence(R.id.widget_text, "setText", TDList.TaskList.get(pos).getHeading());
            switch (TDList.TaskList.get(pos).getCode()) {
                case 1:
                    views.setInt(R.id.widget_rl, "setBackgroundColor", context.getResources().getColor(R.color.hBlue));
                    break;
                case 2:
                    views.setInt(R.id.widget_rl, "setBackgroundColor", context.getResources().getColor(R.color.hYellow));
                    break;
                case 3:
                    views.setInt(R.id.widget_rl, "setBackgroundColor", context.getResources().getColor(R.color.hRed));
                    break;
            }
            widgetManager.updateAppWidget(appWidgetId,views);
        }
    }


}
