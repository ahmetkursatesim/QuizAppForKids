package com.example.yusuf.exerchild;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Collections;

public class highScores extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<String> usernamelist=new ArrayList<>();
    ArrayList<Integer> totalscorelist=new ArrayList<>();
    ArrayList<Integer> totalscorelist2=new ArrayList<>();
    ArrayList<String> totalscorelist3=new ArrayList<>();
    ArrayList<String> totalscorelist4=new ArrayList<>();
    TextView s1,s2,s3,s4,s5,s11,s12,s13,s14,s15 ;


    @Override
   public  void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscores);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");
s1 =findViewById(R.id.s1);
        s2 =findViewById(R.id.s2);
        s3 =findViewById(R.id.s3);
        s4 =findViewById(R.id.s4);
        s5 =findViewById(R.id.s5);
        s11 =findViewById(R.id.s11);
        s12=findViewById(R.id.s12);
        s13=findViewById(R.id.s13);
        s14=findViewById(R.id.s14);
        s15=findViewById(R.id.s15);





new scorey().execute();


    }

public class scorey extends  AsyncTask<String, String, String>{


    @Override
    protected String doInBackground(String... strings) {


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dsp : dataSnapshot.getChildren()){
                    usernamelist.add(String.valueOf(dsp.getKey()));


                }
                for (int x=0; x<usernamelist.size();x++){
                   totalscorelist.add((Integer.parseInt( dataSnapshot.child(usernamelist.get(x)).child("scoreMat").getValue().toString())+ Integer.parseInt(dataSnapshot.child(usernamelist.get(x)).child("scoreing").getValue().toString())));
                    Collections.sort(totalscorelist);
                    Collections.reverse(totalscorelist);
                    totalscorelist2.add((Integer.parseInt( dataSnapshot.child(usernamelist.get(x)).child("scoreMat").getValue().toString())+ Integer.parseInt(dataSnapshot.child(usernamelist.get(x)).child("scoreing").getValue().toString())));
                }
                for (int x=0; x<5;x++) {
                    for (int y=0; y<usernamelist.size();y++) {

                        if(totalscorelist.get(x)==totalscorelist2.get(y)){

                            totalscorelist3.add(usernamelist.get(y));
                            totalscorelist4.add(""+totalscorelist.get(x));
                        }


                    }

                }
                 s1.setText(totalscorelist3.get(0));
                s11.setText(totalscorelist4.get(0));

                s2.setText(totalscorelist3.get(1));
                s12.setText(totalscorelist4.get(1));

                s3.setText(totalscorelist3.get(2));
                s13.setText(totalscorelist4.get(2));
                s4.setText(totalscorelist3.get(3));
                s14.setText(totalscorelist4.get(3));
                s5.setText(totalscorelist3.get(4));
                s15.setText(totalscorelist4.get(4));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });












        return null;
    }
}



    }
