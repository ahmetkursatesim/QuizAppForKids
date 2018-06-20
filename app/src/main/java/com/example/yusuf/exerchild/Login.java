package com.example.yusuf.exerchild;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.yusuf.exerchild.User;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.widget.Toast.LENGTH_LONG;

public class Login extends AppCompatActivity {
    public String userinfo;
    public String pwdinfo;
    EditText kullaniciAdi, password;
    Context context;
    Button   btnsignin;
    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
database=FirebaseDatabase.getInstance();
users=database.getReference("Users");
        final TextView registerScreen = findViewById(R.id.kayıt_ol);
        kullaniciAdi=findViewById(R.id.k_adi_giris);

        password= findViewById(R.id.k_parola_giris);
btnsignin=findViewById(R.id.giris);

      btnsignin.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              new CountDownTimer(500, 50) {

                  @Override
                  public void onTick(long arg0) {
                      // TODO Auto-generated method stub
                      btnsignin.setBackgroundColor(Color.BLACK);
                  }

                  @Override
                  public void onFinish() {
                     btnsignin.setBackgroundColor(Color.TRANSPARENT);

                  }
              }.start();

              signin(kullaniciAdi.getText().toString(),password.getText().toString());
              

          }
      });


            registerScreen.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                 registerScreen.setTextColor(Color.GREEN);
                  Intent i = new Intent(getApplicationContext(), Register.class);
                    startActivity(i);
               }
            });

    }





    public void onBackPressed(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
    private void signin(final String userr,final String pwd){

        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               if(!userr.isEmpty()){

                   if(!pwd.isEmpty()){


                       userinfo=dataSnapshot.child(userr).child("username").getValue().toString();
                       pwdinfo=dataSnapshot.child(userr).child("password").getValue().toString();
                           if(userr.equals(userinfo) ){

                               if(pwd.equals(pwdinfo)){
                                   String FILENAME = "username.txt";
                                   FileOutputStream fos;

                                   try {
                                       fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                                       try {

                                           fos.write(userr.toString().getBytes());


                                           fos.close();

                                       } catch (IOException e) {
                                           e.printStackTrace();
                                       }


                                   } catch (IOException e) {
                                       e.printStackTrace();
                                   }


                                   Intent intocan = new Intent(Login.this, MainActivity.class);
                                   startActivity(intocan);


                               }
                               else{

                                   Toast.makeText(getApplicationContext(), " şifreniz yanlış.", Toast.LENGTH_SHORT).show();

                               }

                           }
                           else{

                               Toast.makeText(getApplicationContext(), "Bu isimde bir kullanıcı bulunamadı.", Toast.LENGTH_SHORT).show();

                           }







                   }
                   else{
                       Toast.makeText(getApplicationContext(),"Lütfen Şifre Kısmını Doldurunuz" , Toast.LENGTH_LONG).show();
                   }
               }
               else{

                   Toast.makeText(getApplicationContext(),"Lütfen Kullanıcı Kısmını Doldurunuz " , Toast.LENGTH_LONG).show();
               }








            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }


    public void Forgot(View view) {

        Intent forgot = new Intent(getApplicationContext(), ForgottenPassword.class);
        startActivity(forgot);


    }
}
