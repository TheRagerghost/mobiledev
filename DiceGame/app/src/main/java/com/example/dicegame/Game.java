package com.example.dicegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class Game extends AppCompatActivity {

    int round = 0;
    int pl_score = 0;
    int ai_score = 0;

    int ai = 0;
    int pl = 0;

    TextView pl_score_tv;
    TextView ai_score_tv;
    TextView round_tv;
    TextView info_tv;

    ImageView dice_1;
    ImageView dice_2;
    ImageView dice_3;
    ImageView dice_4;

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamefirst);

        ai_score = 0;
        pl_score = 0;
        round = 0;

        btn = findViewById(R.id.button2);

        pl_score_tv = findViewById(R.id.textView5);
        ai_score_tv = findViewById(R.id.textView6);
        round_tv = findViewById(R.id.textView7);
        info_tv = findViewById(R.id.log_line);

        pl_score_tv.setText(String.valueOf(pl_score));
        ai_score_tv.setText(String.valueOf(ai_score));
        round_tv.setText("Раунд " + round);

        dice_1 = findViewById(R.id.imageView1);
        dice_2 = findViewById(R.id.imageView2);
        dice_3 = findViewById(R.id.imageView3);
        dice_4 = findViewById(R.id.imageView4);
    }

    public void NewRound(View view) throws InterruptedException {
        Random ran1 = new Random();
        Random ran2 = new Random();
        int[] dices = new int[4];
        String info = "";

        if(pl < 1 && ai < 1)
        {
            round++;
            round_tv.setText("Раунд " + round);

            pl = 1;
            ai = 1;
        }

        if(pl == 1)
        {
            dices[0] = 1 + ran1.nextInt(6);
            dices[1] = 1 + ran1.nextInt(6);

            dice_1.setImageResource(DieId(1, dices[0]));
            dice_2.setImageResource(DieId(1, dices[1]));

            pl_score += dices[0] + dices[1];
            pl_score_tv.setText(String.valueOf(pl_score));

            if (dices[0] == dices[1])
            {
                info += " Игрок выбросил дубль! ";
                btn.setText("Новый бросок");
                pl++;
            }
        }

        if(ai == 1)
        {
            dices[2] = 1 + ran2.nextInt(6);
            dices[3] = 1 + ran2.nextInt(6);

            dice_3.setImageResource(DieId(2, dices[2]));
            dice_4.setImageResource(DieId(2, dices[3]));

            ai_score += dices[2] + dices[3];
            ai_score_tv.setText(String.valueOf(ai_score));

            if (dices[2] == dices[3])
            {
                info += " Компьютер выбросил дубль! ";
                btn.setText("Новый бросок");
                ai++;
            }
        }

        info_tv.setText(info);

        pl--;
        ai--;

        if(pl < 1 && ai < 1)
        {
            btn.setText("Новый раунд");
        }

        if(pl_score >= 100 || ai_score >= 100)
        {
           EndGame();
        }
    }

    public void EndGame() {
        Intent intent = new Intent(this, Results.class);
        intent.putExtra("PLS", pl_score);
        intent.putExtra("AIS", ai_score);
        startActivity(intent);
        finish();
    }

    public int DieId(int color, int score) {

        if (color == 1) {
            switch (score) {
                case 1:
                    return R.drawable.dice_blue_1;

                case 2:
                    return R.drawable.dice_blue_2;

                case 3:
                    return R.drawable.dice_blue_3;

                case 4:
                    return R.drawable.dice_blue_4;

                case 5:
                    return R.drawable.dice_blue_5;

                case 6:
                    return R.drawable.dice_blue_6;
            }
        }
        else {
            switch (score) {
                case 1:
                    return R.drawable.dice_red_1;

                case 2:
                    return R.drawable.dice_red_2;

                case 3:
                    return R.drawable.dice_red_3;

                case 4:
                    return R.drawable.dice_red_4;

                case 5:
                    return R.drawable.dice_red_5;

                case 6:
                    return R.drawable.dice_red_6;
            }
        }


        return R.drawable.dice_red_1;
    }

}
