package com.example.yusuf.exerchild;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartPage extends AppCompatActivity {
Button swapMath;
Button swapEng;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start_page);
        swapMath=findViewById(R.id.mat_sec);
        swapEng=findViewById(R.id.ing_sec);

        swapMath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CountDownTimer(500, 50) {

                    @Override
                    public void onTick(long arg0) {
                        // TODO Auto-generated method stub
                        swapMath.setBackgroundColor(Color.GREEN);
                    }

                    @Override
                    public void onFinish() {
                        swapMath.setBackgroundColor(Color.TRANSPARENT);

                    }
                }.start();
                Intent matematicintent= new Intent(StartPage.this, MathematicsQuiz.class);

                startActivity(matematicintent);
            }
        });
        swapEng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CountDownTimer(500, 50) {

                    @Override
                    public void onTick(long arg0) {
                        // TODO Auto-generated method stub
                        swapEng.setBackgroundColor(Color.GREEN);
                    }

                    @Override
                    public void onFinish() {
                        swapEng.setBackgroundColor(Color.TRANSPARENT);

                    }
                }.start();
                Intent englishintent = new Intent(StartPage.this, EnglishQuiz.class);

                startActivity(englishintent);
            }
        });


    }




    public void onBackPressed(){
        Intent returnMainPage = new Intent(StartPage.this, MainActivity.class);
        startActivity(returnMainPage);




    }
}
