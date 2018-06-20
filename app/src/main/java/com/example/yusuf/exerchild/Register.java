package com.example.yusuf.exerchild;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import java.util.Calendar;

public class Register extends AppCompatActivity {
    EditText name;
    EditText password;
    EditText email;
    String city;
    RadioButton genderKız, genderErkek;
    RadioGroup radioGroup;
    String ageYear;
    Context context;
    String gender;
    Spinner dropdown1;
    Spinner dropdown2;
    Button register;
    FirebaseDatabase database;
    DatabaseReference myRef;

    private static final String TAG = "EmailPassword";
    ProgressDialog mProgress;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        context = this;
        mProgress= new ProgressDialog(context);
        name = findViewById(R.id.y_k_adi_giris);
        password = findViewById(R.id.y_parola_giris);
        email =  findViewById(R.id.y_email_giris);
        genderKız =  findViewById(R.id.cin_kız);
        genderErkek =  findViewById(R.id.cin_kız);
        radioGroup = findViewById(R.id.radiogrup);
        register=findViewById(R.id.regbutton);

        dropdown1 =  findViewById(R.id.spin_sehir);
         database= FirebaseDatabase.getInstance();
         myRef = database.getReference("Users");

        dropdown2 = findViewById(R.id.spin_yil);

        TextView loginScreen =  findViewById(R.id.textView5);


        loginScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
            }
        });

        dropdown1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                city = dropdown1.getSelectedItem().toString();

                Log.e("Selected item : ", city);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        dropdown2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                ageYear = dropdown2.getSelectedItem().toString();

                Log.e("Selected item : ", ageYear);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int radioButtonID = radioGroup.getCheckedRadioButtonId();
                View radioButton = radioGroup.findViewById(radioButtonID);
                int idx = radioGroup.indexOfChild(radioButton);
                new CountDownTimer(500, 50) {

                    @Override
                    public void onTick(long arg0) {
                        // TODO Auto-generated method stub
                        register.setBackgroundColor(Color.BLUE);
                    }

                    @Override
                    public void onFinish() {
                        register.setBackgroundColor(Color.TRANSPARENT);

                    }
                }.start();

                RadioButton r = (RadioButton) radioGroup.getChildAt(idx);
                String selectedtext = r.getText().toString();

                gender= selectedtext;
                if (name != null && password != null && city != null && ageYear != null && gender != null) {

                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int age = year - Integer.parseInt(ageYear);
                    User user = new User(name.getText().toString(), password.getText().toString(), age, city, gender, 0, 0);
                    register(name.getText().toString(), password.getText().toString(), age, city, gender, 0, 0,email.getText().toString());
                }
                else {

                    Toast.makeText(getApplicationContext(), "Boş alanları doldurmalısın", Toast.LENGTH_SHORT).show();
                }

            }

        });

    }

    private void showProgressDialog() {
        mProgress = new ProgressDialog(context);
        mProgress.setTitle("Page downloading...");
        mProgress.setMax(100);
        mProgress.setProgress(0);
        mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgress.show();

    }





    public void register(final String username, final String pass, final int age1, final String city,final  String gender1, final int mathsoc, final int ingsoc, final String email1) {

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(username)){

                    Toast.makeText(getApplicationContext(), "Bu isimle Kullanıcı Bulunmak" +
                            "tadır.", Toast.LENGTH_SHORT).show();

               }
                else{

                    myRef.child(username).child("username").setValue(username);
                    myRef.child(username).child("password").setValue(pass);
                    myRef.child(username).child("email").setValue(email1);
                    myRef.child(username).child("city").setValue(city);
                    myRef.child(username).child("age").setValue(age1);
                    myRef.child(username).child("gender").setValue(gender1);
                    myRef.child(username).child("scoreMat").setValue(0);
                    myRef.child(username).child("scoreing").setValue(0);


                    Toast.makeText(getApplicationContext(), "Kaydınız başarıyla alındı", Toast.LENGTH_SHORT).show();

                    name.setText("");
                    password.setText("");
                    email.setText("");
                    radioGroup.clearCheck();
                    dropdown1.setSelection(0);
                    dropdown2.setSelection(0);

               }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }


    public void onBackPressed(){
        Intent intocan = new Intent(Register.this, Login.class);
        startActivity(intocan);
    }


}
