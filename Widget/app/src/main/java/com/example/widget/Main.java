package com.example.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

public class Main extends AppWidgetProvider {

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager manager, int[] ids) {
        super.onUpdate(context, manager, ids);

        SharedPreferences pref = context.getSharedPreferences(Conf.WIDGET_PREF, Context.MODE_PRIVATE);

        for (int id: ids) {
            updateWidget(context, manager, pref, id);
        }
    }

    @Override
    public void onDeleted(Context context, int[] ids) {
        super.onDeleted(context, ids);

        SharedPreferences.Editor edit = context.getSharedPreferences(Conf.WIDGET_PREF, Context.MODE_PRIVATE).edit();

        for (int id: ids) {
            edit.remove(Conf.NOTE_TEXT + id);
            edit.remove(Conf.NOTE_COLOR + id);
        }

        edit.apply();
    }

    static void updateWidget(Context context, AppWidgetManager manager, SharedPreferences pref, int widgetID) {

        String text = pref.getString(Conf.NOTE_TEXT + widgetID, null);

        if (text == null) return;

        int color = pref.getInt(Conf.NOTE_COLOR + widgetID, 0);

        RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.main_layout);
        view.setTextViewText(R.id.textView, text);
        view.setInt(R.id.textView, "setBackgroundColor", color);
        manager.updateAppWidget(widgetID, view);
    }
}
