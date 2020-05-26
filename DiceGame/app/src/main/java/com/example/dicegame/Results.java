package com.example.dicegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Results extends AppCompatActivity {

    TextView result_tv;
    TextView emote_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        int pl_score = intent.getIntExtra("PLS", 0);
        int ai_score = intent.getIntExtra("AIS", 0);

        result_tv = findViewById(R.id.textView8);
        emote_tv = findViewById(R.id.textView9);

        if( pl_score >= 100 && ai_score >= 100)
        {
            result_tv.setText("Ничья");
            emote_tv.setText("┐(-_-)┌");
        }
        else if (pl_score >= 100)
        {
            result_tv.setText("Победа");
            emote_tv.setText("o(>ω<)o");
        }
        else
        {
            result_tv.setText("Поражение");
            emote_tv.setText("٩(ఠ益ఠ)۶");
        }
    }

    public void ToMain (View view)
    {
        finish();
    }

    public void ToGame (View view)
    {
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
        finish();
    }
}
