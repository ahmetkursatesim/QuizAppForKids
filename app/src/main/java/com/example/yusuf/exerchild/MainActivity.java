package com.example.yusuf.exerchild;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    public String uinfo;
    public int score;
    Button oyunabasla;
    Button nasiloynanir;
    Button giveStar;
    Button scores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      oyunabasla=findViewById(R.id.oyuna_basla);
    nasiloynanir=findViewById(R.id.nasil_oynanir);
        giveStar=findViewById(R.id.puan_ver);

       scores=findViewById(R.id.top3);
        oyunabasla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new CountDownTimer(500, 50) {

                    @Override
                    public void onTick(long arg0) {
                        // TODO Auto-generated method stub
                        oyunabasla.setBackgroundColor(Color.GREEN);
                    }

                    @Override
                    public void onFinish() {
                        oyunabasla.setBackgroundColor(Color.TRANSPARENT);

                    }
                }.start();
                Intent intocan = new Intent(MainActivity.this, StartPage.class);

                startActivity(intocan);


            }
        });
        nasiloynanir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CountDownTimer(500, 50) {

                    @Override
                    public void onTick(long arg0) {
                        // TODO Auto-generated method stub
                        nasiloynanir.setBackgroundColor(Color.GREEN);
                    }

                    @Override
                    public void onFinish() {
                        nasiloynanir.setBackgroundColor(Color.TRANSPARENT);

                    }
                }.start();
                Intent rulesintent= new Intent(MainActivity.this,Rules.class);
                startActivity(rulesintent);

            }
        });
        giveStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CountDownTimer(500, 50) {

                    @Override
                    public void onTick(long arg0) {
                        // TODO Auto-generated method stub
                        giveStar.setBackgroundColor(Color.GREEN);
                    }

                    @Override
                    public void onFinish() {
                        giveStar.setBackgroundColor(Color.TRANSPARENT);

                    }
                }.start();
                Intent starintent = new Intent(MainActivity.this, Star.class);
                startActivity(starintent);

            }
        });
        scores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CountDownTimer(500, 50) {

                    @Override
                    public void onTick(long arg0) {
                        // TODO Auto-generated method stub
                        scores.setBackgroundColor(Color.GREEN);
                    }

                    @Override
                    public void onFinish() {
                        scores.setBackgroundColor(Color.TRANSPARENT);

                    }
                }.start();
                Intent scoreintent = new Intent(MainActivity.this, highScores.class);
                startActivity(scoreintent);

            }
        });


    }


    public void onBackPressed(){
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Uygulamayı Kapat")
                .setMessage("Uygulamayı kapatmak istiyor musun?")
                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                        startActivity(intent);
                        finish();
                        System.exit(0);
                    }
                }).setNegativeButton("Hayır", null).show();


    }


    public void logout(View view) throws IOException {

        String FILENAME = "username.txt";
        FileOutputStream fos;
        String empty="";

        try {
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            try {

                fos.write(empty.toString().getBytes());


                fos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }



        Intent intocan = new Intent(MainActivity.this, Login.class);
        startActivity(intocan);




        finish();
        System.exit(0);

    }


}
