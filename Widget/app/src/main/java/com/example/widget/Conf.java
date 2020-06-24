package com.example.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

public class Conf extends Activity {

    int w_id = AppWidgetManager.INVALID_APPWIDGET_ID;
    Intent resultValue;

    public final static String WIDGET_PREF = "WIDGET_PREF";
    public final static String NOTE_TEXT = "NOTE_TEXT_";
    public final static String NOTE_COLOR = "NOTE_COLOR_";

    @Override
    public void onCreate(Bundle params) {
        super.onCreate(params);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            w_id = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if (w_id == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, w_id);
        setResult(RESULT_CANCELED, resultValue);
        setContentView(R.layout.conf_layout);
    }

    public void onClick(View view) {

        EditText text_edit = findViewById(R.id.note_edit);
        RadioGroup color_edit = findViewById(R.id.colors);

        int color = 0;

        switch (color_edit.getCheckedRadioButtonId()) {

            case R.id.note_red:
                color = R.color.red;
                break;

            case R.id.note_green:
                color = R.color.green;
                break;

            case R.id.note_blue:
                color = R.color.blue;
                break;

            case R.id.note_cyan:
                color = R.color.cyan;
                break;

            case R.id.note_magenta:
                color = R.color.magenta;
                break;

            case R.id.note_yellow:
                color = R.color.yellow;
                break;

            case R.id.note_white:
                color = R.color.lightgray;
                break;

            case R.id.note_black:
                color = R.color.darkgray;
                break;
        }

        SharedPreferences pref = getSharedPreferences(WIDGET_PREF, MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(NOTE_TEXT + w_id, text_edit.getText().toString());
        edit.putInt(NOTE_COLOR + w_id, getResources().getColor(color));
        edit.apply();

        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        Main.updateWidget(this, manager, pref, w_id);

        setResult(RESULT_OK, resultValue);
        finish();
    }
}
