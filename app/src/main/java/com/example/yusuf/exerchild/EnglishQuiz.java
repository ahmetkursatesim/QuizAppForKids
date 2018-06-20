package com.example.yusuf.exerchild;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class EnglishQuiz extends AppCompatActivity {
    Context context;
    ImageView smile,sad;
    Toast toast;
    ArrayList<Question> questionList;
    ImageView image;
    TextView text, txt_score, txt_soruSayisi;
    Button btn1, btn2, btn3;
    ImageButton ileriButton, geriButton;
    int counter=0;
    public static int score=0;
    boolean click_button=false;
    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference scoreref;
    private ProgressDialog progressDialog;
    static Animation anim1;
    String username="";
    MediaPlayer mp = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        score=0;
        context=this;
        toast=new Toast(this);
        setContentView(R.layout.activity_english_quiz);
        questionList= new ArrayList<Question>();
        text= findViewById(R.id.ing_soru);
        btn1=findViewById(R.id.A1);
        btn2= findViewById(R.id.B1);
        btn3=findViewById(R.id.C1);
        ileriButton=findViewById(R.id.ileri1);
        geriButton=findViewById(R.id.geri1);
        txt_score=findViewById(R.id.Puan1);
        txt_soruSayisi=findViewById(R.id.soru_sayisi1);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("ingilizce");
        scoreref=database.getReference("Users");

        anim1= AnimationUtils.loadAnimation(EnglishQuiz.this,R.anim.shakng);
        try {
            username=readAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new getQuestion().execute();

    }

    private void updatescore(final int score) {
        scoreref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if(Integer.parseInt(dataSnapshot.child(username).child("scoreing").getValue().toString())<=score){
                    scoreref.child(username).child("scoreing").setValue(score);

                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


    public void ileriye1Git(View view) throws IOException {

        counter++;

        if(counter<=29) {
            text.setText(questionList.get(counter).question);

            ArrayList<String> cevaplarShuffle = new ArrayList<String>();
            cevaplarShuffle.add(questionList.get(counter).answer);
            cevaplarShuffle.add(questionList.get(counter).wrongAnswer_1);
            cevaplarShuffle.add(questionList.get(counter).wrongAnswer_2);
            Collections.shuffle(cevaplarShuffle);
            btn1.setText(cevaplarShuffle.get(0));
            btn2.setText(cevaplarShuffle.get(1));
            btn3.setText(cevaplarShuffle.get(2));
            GradientDrawable gradientDrawable1 = (GradientDrawable)btn1.getBackground().mutate();
            gradientDrawable1.setColor(Color.WHITE);
            GradientDrawable gradientDrawable2 = (GradientDrawable)btn2.getBackground().mutate();
            gradientDrawable2.setColor(Color.WHITE);
            GradientDrawable gradientDrawable3 = (GradientDrawable)btn3.getBackground().mutate();
            gradientDrawable3.setColor(Color.WHITE);
            txt_soruSayisi.setText("Soru:"+(counter+1)+"/30");
            if (questionList.get(counter).clikable) {
                btn1.setEnabled(false);
                btn2.setEnabled(false);
                btn3.setEnabled(false);
            } else {
                btn1.setEnabled(true);
                btn2.setEnabled(true);
                btn3.setEnabled(true);
            }


        }
        else if(counter==30){
            Toast.makeText(getApplicationContext(), "Sorular bitti", Toast.LENGTH_SHORT).show();
            counter--;
        }

    }

    public void geriye1Git(View view) {

        counter--;
        if(counter>=0) {
            ArrayList<String> cevaplarShuffle1 = new ArrayList<String>();
            cevaplarShuffle1.add(questionList.get(counter).answer);
            cevaplarShuffle1.add(questionList.get(counter).wrongAnswer_1);
            cevaplarShuffle1.add(questionList.get(counter).wrongAnswer_2);
            Collections.shuffle(cevaplarShuffle1);
            btn1.setText(cevaplarShuffle1.get(0));
            btn2.setText(cevaplarShuffle1.get(1));
            btn3.setText(cevaplarShuffle1.get(2));
            text.setText(questionList.get(counter).question);
            GradientDrawable gradientDrawable1 = (GradientDrawable)btn1.getBackground().mutate();
            gradientDrawable1.setColor(Color.WHITE);
            GradientDrawable gradientDrawable2 = (GradientDrawable)btn2.getBackground().mutate();
            gradientDrawable2.setColor(Color.WHITE);
            GradientDrawable gradientDrawable3 = (GradientDrawable)btn3.getBackground().mutate();
            gradientDrawable3.setColor(Color.WHITE);
            txt_soruSayisi.setText("Soru:"+(counter+1)+"/30");
            if (questionList.get(counter).clikable) {
                btn1.setEnabled(false);
                btn2.setEnabled(false);
                btn3.setEnabled(false);
            } else {
                btn1.setEnabled(true);
                btn2.setEnabled(true);
                btn3.setEnabled(true);
            }

        }
        else {
            Toast.makeText(getApplicationContext(), "İlk sorudasın geriye gidemezsin", Toast.LENGTH_SHORT).show();
            counter++;
        }

    }

    public void dogruCevapMi_A(View view){
        click_button=true;
        btn1.setEnabled(false);
        btn2.setEnabled(false);
        btn3.setEnabled(false);
        questionList.get(counter).clikable=true;
        if(btn1.getText().toString().equals(questionList.get(counter).answer)){

            if(mp!=null)
            {
                mp.stop();
            }
            mp = MediaPlayer.create(this,R.raw.animatclap);
            mp.start();
            Toast.makeText(getApplicationContext(), "Tebrikler :)", Toast.LENGTH_SHORT).show();
            score++;
            updatescore(score);

            txt_score.setText("Puan : "+score);
            GradientDrawable gradientDrawable = (GradientDrawable)btn1.getBackground().mutate();
            gradientDrawable.setColor(Color.GREEN);

        }
        else{
            if(mp!=null)
            {
                mp.stop();
            }
            mp = MediaPlayer.create(this,R.raw.animate);
            mp.start();
            Toast.makeText(getApplicationContext(), "Daha çok çalışmalısın..", Toast.LENGTH_SHORT).show();
            GradientDrawable gradientDrawable1 = (GradientDrawable)btn1.getBackground().mutate();
            gradientDrawable1.setColor(Color.RED);
            btn1.startAnimation(anim1);

        }

    }
    public void dogruCevapMi_B(View view){
        click_button=true;
        btn1.setEnabled(false);
        btn2.setEnabled(false);
        btn3.setEnabled(false);
        questionList.get(counter).clikable=true;
        if(btn2.getText().toString().equals(questionList.get(counter).answer)){

            if(mp!=null)
            {
                mp.stop();
            }
            mp = MediaPlayer.create(this,R.raw.animatclap);
            mp.start();
            Toast.makeText(getApplicationContext(), "Tebrikler :)", Toast.LENGTH_SHORT).show();
            score++;
            updatescore(score);

            txt_score.setText("Puan : "+score);
            GradientDrawable gradientDrawable = (GradientDrawable)btn2.getBackground().mutate();
            gradientDrawable.setColor(Color.GREEN);

        }
        else{
            if(mp!=null)
            {
                mp.stop();
            }
            mp = MediaPlayer.create(this,R.raw.animate);
            mp.start();
            Toast.makeText(getApplicationContext(), "Daha çok çalışmalısın..", Toast.LENGTH_SHORT).show();
            GradientDrawable gradientDrawable1 = (GradientDrawable)btn2.getBackground().mutate();
            gradientDrawable1.setColor(Color.RED);
            btn2.startAnimation(anim1);
        }
    }

    public void dogruCevapMi_C(View view){
        click_button=true;
        btn1.setEnabled(false);
        btn2.setEnabled(false);
        btn3.setEnabled(false);
        questionList.get(counter).clikable=true;
        if(btn3.getText().toString().equals(questionList.get(counter).answer)){

            if(mp!=null)
            {
                mp.stop();
            }
            mp = MediaPlayer.create(this,R.raw.animatclap);
            mp.start();
            Toast.makeText(getApplicationContext(), "Tebrikler :)", Toast.LENGTH_SHORT).show();
            score++;
            updatescore(score);

            txt_score.setText("Puan : "+score);
            GradientDrawable gradientDrawable = (GradientDrawable)btn3.getBackground().mutate();
            gradientDrawable.setColor(Color.GREEN);

        }
        else{
            if(mp!=null)
            {
                mp.stop();
            }
            mp = MediaPlayer.create(this,R.raw.animate);
            mp.start();
            Toast.makeText(getApplicationContext(), "Daha çok çalışmalısın..", Toast.LENGTH_SHORT).show();
            GradientDrawable gradientDrawable1 = (GradientDrawable)btn3.getBackground().mutate();
            gradientDrawable1.setColor(Color.RED);
            btn3.startAnimation(anim1);
        }
    }



    public void onBackPressed(){
        Intent intocan = new Intent(EnglishQuiz.this, StartPage.class);
        startActivity(intocan);


    }







    public class getQuestion extends AsyncTask<String, String, String>
    {

        @Override
        protected String doInBackground(String... params) {

            myRef.addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        String qname = dsp.getKey();


                        String q = String.valueOf(dsp.child("soru").getValue());
                        String a = String.valueOf(dsp.child("cevap").getValue());
                        String wAnswer1 = String.valueOf(dsp.child("yanlis1").getValue());
                        String wAnswer2 = String.valueOf(dsp.child("yanlis2").getValue());


                        questionList.add(new Question(qname, q, a, wAnswer1, wAnswer2));



                    }

                    progressDialog.hide();
                    text.setText(questionList.get(counter).question);
                    System.out.println(questionList.get(counter).question);
                    ArrayList<String> cevaplarShuffle= new ArrayList<String>();
                    cevaplarShuffle.add(questionList.get(counter).answer);
                    cevaplarShuffle.add(questionList.get(counter).wrongAnswer_1);
                    cevaplarShuffle.add(questionList.get(counter).wrongAnswer_2);
                    Collections.shuffle(cevaplarShuffle);
                    btn1.setText(cevaplarShuffle.get(0));
                    System.out.println(cevaplarShuffle.get(0));
                    btn2.setText(cevaplarShuffle.get(1));
                    System.out.println(cevaplarShuffle.get(1));
                    btn3.setText(cevaplarShuffle.get(2));
                    System.out.println(cevaplarShuffle.get(2));
                    txt_soruSayisi.setText("Soru:"+"1/30");


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }


            });

            return null;
        }

        @Override
        protected void onPreExecute() {
            //main thread
            //super.onPreExecute();
            Log.d("AsyncTask","onPreExecute is working");

            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Sayfa yükleniyor...");
            progressDialog.setMax(100);
            progressDialog.setProgress(0);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            Log.d("AsyncTask","onPreExecute end...");
        }



    }
    public String readAll() throws FileNotFoundException, IOException {

        String FILENAME = "username.txt";

        FileOutputStream fos;

        try {
            fos = openFileOutput(FILENAME, Context.MODE_APPEND);


        } catch (IOException e) {
            e.printStackTrace();
        }


        int ch;
        StringBuffer fileContent = new StringBuffer("");
        FileInputStream fis;
            fis = openFileInput(FILENAME);


            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line=br.readLine();
            username=line;


        return username;

    }

}
